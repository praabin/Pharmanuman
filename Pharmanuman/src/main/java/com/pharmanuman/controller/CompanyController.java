package com.pharmanuman.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pharmanuman.dao.MedicineForCompanyRepository;
import com.pharmanuman.dao.MedicineRepository;
import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.Medicine;
import com.pharmanuman.entities.MedicineForCompany;
import com.pharmanuman.entities.User;
import com.pharmanuman.helper.MyMessage;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pc")
public class CompanyController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	MedicineForCompanyRepository medicineForCompanyRepository;

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
		model.addAttribute("mfc", new MedicineForCompany());
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<MedicineForCompany> medicines = this.medicineForCompanyRepository.findMedicinesById(tempUser.getId());
		model.addAttribute("medicines", medicines);
		return "pharmaceuticalcompany/add_stock";
	}

	@RequestMapping("/process-order")
	public String processOrder(@ModelAttribute MedicineForCompany medicineForCompany, Principal p, Model m,
			HttpSession session) {

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
//		Collections.sort(medicines, Comparator.comparingInt(MedicineForCompany::getMid).reversed());
		m.addAttribute("medicines", medicines);

		session.setAttribute("message", new MyMessage("Successfully Updated!! ", "alert-success"));
		return "pharmaceuticalcompany/view_stock";
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
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		m.setUser(tempUser);
		this.medicineForCompanyRepository.save(m);

		List<MedicineForCompany> medicines = this.medicineForCompanyRepository.findMedicinesById(tempUser.getId());
		model.addAttribute("medicines", medicines);

		session.setAttribute("message", new MyMessage("Successfully Updated!! ", "alert-success"));
		return "pharmaceuticalcompany/view_stock";
	}
	
	
	

}
