package com.pharmanuman.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pharmanuman.dao.MedicineForCompanyRepository;
import com.pharmanuman.dao.MedicineForPredictionRepo;
import com.pharmanuman.dao.MedicineRepository;
import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.Medicine;
import com.pharmanuman.entities.MedicineForCompany;
import com.pharmanuman.entities.MedicineForPrediction;
import com.pharmanuman.entities.User;
import com.pharmanuman.helper.MyMessage;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/pc")
public class CompanyController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	MedicineForCompanyRepository medicineForCompanyRepository;

	@Autowired
	MedicineForPredictionRepo medicineForPredictionRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String name = principal.getName();
		System.out.println("User name " + name);
		User user = this.userRepository.getUserByUserName(name);
		System.out.println("USER " + user);
		model.addAttribute("user", user);
	}

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("title", "Pharmaceuticalcompany Dashboard");
		return "pharmaceuticalcompany/pc_dashboard";
	}

	@RequestMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("title", "Profile");
		return "pharmaceuticalcompany/profile";
	}

	@RequestMapping("/setting")
	public String setting(Model model) {
		model.addAttribute("title", "Setting");
		return "pharmaceuticalcompany/setting";
	}

	@RequestMapping("/prediction")
	public String prediciton(Model model) {
		model.addAttribute("title", "Prediction");
		return "pharmaceuticalcompany/prediction";
	}

	@GetMapping("/add-stock")
	public String openAddContactForm(Model model, Principal p) {
		model.addAttribute("title", "Order medicine");

		model.addAttribute("medicineForCompany", new MedicineForCompany());

		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<MedicineForCompany> medicines = this.medicineForCompanyRepository.findMedicinesById(tempUser.getId());
		model.addAttribute("medicines", medicines);
		return "pharmaceuticalcompany/add_stock";
	}

	@RequestMapping("/process-order")
	public String processOrder(@Valid @ModelAttribute MedicineForCompany medicineForCompany, BindingResult result,
			Principal p, Model m, HttpSession session) {
		m.addAttribute("medicine", new MedicineForCompany());

		try {

			if (result.hasErrors()) {
				System.out.println("Error:: " + result.toString());
//				m.addAttribute("medicineCompany", medicineForCompany);
				return "pharmaceuticalcompany/add_stock";

			}

			String tempName = p.getName();
			User user = this.userRepository.getUserByUserName(tempName);
			medicineForCompany.setUser(user);
			user.getMedicinesForCompany().add(medicineForCompany);

			this.userRepository.save(user);

			System.out.println("data: " + medicineForCompany);
			System.out.println("medicines added to database");
			String name = p.getName();
			User tempUser = this.userRepository.getUserByUserName(name);
			List<MedicineForCompany> medicines = this.medicineForCompanyRepository.findMedicinesById(tempUser.getId());
//			Collections.sort(medicines, Comparator.comparingInt(MedicineForCompany::getMid).reversed());
			m.addAttribute("medicines", medicines);

			session.setAttribute("message", new MyMessage("Successfully Added!! ", "alert-success"));

			return "pharmaceuticalcompany/view_stock";
		} catch (Exception e) {

			session.setAttribute("message", new MyMessage("Something went wrong!! ", "alert-danger"));
			return "pharmaceuticalcompany/view_stock";
		}

	}

	@RequestMapping("/view-stock")
	public String viewMedicine(Model m, Principal p) {
		m.addAttribute("title", "View Medicine");
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<MedicineForCompany> medicines = this.medicineForCompanyRepository.findMedicinesById(tempUser.getId());

		// Sort the list alphabetically by name
//		Collections.sort(medicines, Comparator.comparing(Medicine::getName));
		Collections.sort(medicines, Comparator.comparing(MedicineForCompany::getName, String.CASE_INSENSITIVE_ORDER));

		m.addAttribute("medicines", medicines);
		return "pharmaceuticalcompany/view_stock";
	}

	@GetMapping("/delete/{mfcid}")
	public String deleteMedicine(@PathVariable("mfcid") int mfcid, Model m, Principal p, HttpSession session) {

		try {

			MedicineForCompany medicine = this.medicineForCompanyRepository.findById(mfcid).get();
			User tempUser = this.userRepository.getUserByUserName(p.getName());
			tempUser.getMedicinesForCompany().remove(medicine);
			this.userRepository.save(tempUser);

			String name = p.getName();
			User tempUser1 = this.userRepository.getUserByUserName(name);
			List<MedicineForCompany> medicines = this.medicineForCompanyRepository.findMedicinesById(tempUser1.getId());
			m.addAttribute("medicines", medicines);

			session.setAttribute("message", new MyMessage("Deleted Successfully!! ", "alert-success"));
			return "pharmaceuticalcompany/view_stock";

		} catch (Exception e) {
			// TODO: handle exception

			session.setAttribute("message", new MyMessage("Something went wrong!! ", "alert-danger"));
			return "pharmaceuticalcompany/view_stock";
		}
	}

	@PostMapping("/updateStock/{mfcid}")
	public String updateMedicine(@PathVariable("mfcid") int id, Model m) {
		m.addAttribute("title", "Update medicine");
		MedicineForCompany tempMedicine = this.medicineForCompanyRepository.findById(id).get();
		m.addAttribute("medicine", tempMedicine);
		return "pharmaceuticalcompany/update_stock";
	}

	@PostMapping("/process-update")
	public String processUpdate(@ModelAttribute MedicineForCompany m, Principal p, Model model, HttpSession session) {
//		Medicine oldMedcine = this.medicineRepository.findById(m.getMid()).get();
		try {

			String name = p.getName();
			User tempUser = this.userRepository.getUserByUserName(name);
			m.setUser(tempUser);
			this.medicineForCompanyRepository.save(m);

			List<MedicineForCompany> medicines = this.medicineForCompanyRepository.findMedicinesById(tempUser.getId());
			model.addAttribute("medicines", medicines);

			session.setAttribute("message", new MyMessage("Successfully Updated!! ", "alert-success"));
			return "pharmaceuticalcompany/view_stock";

		} catch (Exception e) {
			// TODO: handle exception

			session.setAttribute("message", new MyMessage("Something went wrong!! ", "alert-danger"));
			return "pharmaceuticalcompany/view_stock";
		}
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal p, HttpSession session) {
		System.out.println("Old password::" + oldPassword);
		String name = p.getName();

		User tempUser = this.userRepository.getUserByUserName(name);
		String oldPassword1 = tempUser.getPassword();

		if (this.bCryptPasswordEncoder.matches(oldPassword, oldPassword1)) {
			tempUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepository.save(tempUser);

			this.userRepository.save(tempUser);
			session.setAttribute("msg", new MyMessage("Successfully Updated!! ", "alert-success"));
			return "pharmaceuticalcompany/setting";
		} else {
			System.out.println("password doesn't matches");
			session.setAttribute("msg", new MyMessage("Password doesn't match. ", "alert-danger"));
			return "pharmaceuticalcompany/setting";

		}

	}

	@RequestMapping("/medicine-details/{mfcid}")
	public String showMedicineDetail(@PathVariable("mfcid") Integer mfcid, Model model, Principal p) {
		System.out.println("mid " + mfcid);
		Optional<MedicineForCompany> medicineOptional = this.medicineForCompanyRepository.findById(mfcid);
		MedicineForCompany medicine = medicineOptional.get();
		String name = p.getName();
		User user = this.userRepository.getUserByUserName(name);
		if (user.getId() == medicine.getUser().getId()) {
			model.addAttribute("medicine", medicine);
			model.addAttribute("title", medicine.getName());
		}

		return "pharmaceuticalcompany/medicine_details";
	}

// prediction k lagi 
	@RequestMapping("/add-stock-prediction")
	public String addStockForPrediction(Model model) {

		model.addAttribute("medicineForPrediction", new MedicineForPrediction());
		model.addAttribute("title", "Prediction");
		return "pharmaceuticalcompany/add_category";
	}

	@RequestMapping("/process-stock")
	public String processMedicineForPrediciton(@Valid @ModelAttribute MedicineForPrediction medicineForPrediction,
			BindingResult result, Principal p, Model m, HttpSession session) {
//		m.addAttribute("medicine", new MedicineForPrediction());

		try {

			if (result.hasErrors()) {

				System.out.println("Error:: " + result.toString());
				return "pharmaceuticalcompany/add_category";

			}
			String tempName = p.getName();
			User user = this.userRepository.getUserByUserName(tempName);
			medicineForPrediction.setUser(user);
			user.getMedicinesForPrediction().add(medicineForPrediction);

			this.userRepository.save(user);

			System.out.println("data: " + medicineForPrediction);
			System.out.println("medicines added to database");
			String name = p.getName();
			User tempUser = this.userRepository.getUserByUserName(name);
//			List<MedicineForCompany> medicines = this.medicineForCompanyRepository.findMedicinesById(tempUser.getId());

//			Collections.sort(medicines, Comparator.comparingInt(MedicineForCompany::getMid).reversed());
//			m.addAttribute("medicines", medicines);

			session.setAttribute("message", new MyMessage("Successfully Added!! ", "alert-success"));

			return "pharmaceuticalcompany/add_category";
		} catch (Exception e) {

			session.setAttribute("message", new MyMessage("Something went wrong!! ", "alert-danger"));
			return "pharmaceuticalcompany/add_category";
		}

	}

	@RequestMapping("/view-stock-for-prediction")
	public String viewStockForPrediction(Model m, Principal p) {
		m.addAttribute("title", "View stock for prediction");
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<MedicineForPrediction> medicines = this.medicineForPredictionRepo
				.findMedicineForPredictionById(tempUser.getId());
		System.out.println(medicines);
		m.addAttribute("mfp", medicines);
		return "pharmaceuticalcompany/view_stock_for_prediction";
	}

}
