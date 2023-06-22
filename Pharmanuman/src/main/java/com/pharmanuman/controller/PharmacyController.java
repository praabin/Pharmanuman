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

import com.pharmanuman.dao.MedicineRepository;
import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.Medicine;
import com.pharmanuman.entities.User;
import com.pharmanuman.helper.MyMessage;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MedicineRepository medicineRepository;

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
		model.addAttribute("title", "User Dashboard");
		return "pharmacy/pharmacy_dashboard";
	}

	@RequestMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("title", "User Dashboard");
		return "pharmacy/profile";
	}

	@RequestMapping("/setting")
	public String setting(Model model) {
		model.addAttribute("title", "User Dashboard");
		return "pharmacy/setting";
	}

	@GetMapping("/order-medicine")
	public String openAddContactForm(Model model, Principal p) {
		model.addAttribute("title", "Order medicine");
		model.addAttribute("medicine", new Medicine());
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<Medicine> medicines = this.medicineRepository.findMedicinesById(tempUser.getId());
		model.addAttribute("medicines", medicines);
		return "pharmacy/add_medicine";
	}

	@RequestMapping("/process-order")
	public String processOrder(@ModelAttribute Medicine medicine, Principal p, Model m, HttpSession session) {

		try {
		String tempName = p.getName();
		User user = this.userRepository.getUserByUserName(tempName);
		medicine.setUser(user);
		user.getMedicines().add(medicine);
		this.userRepository.save(user);

		System.out.println("data: " + medicine);
		System.out.println("medicines added to database");
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<Medicine> medicines = this.medicineRepository.findMedicinesById(tempUser.getId());
		Collections.sort(medicines, Comparator.comparingInt(Medicine::getMid).reversed());
		m.addAttribute("medicines", medicines);
		session.setAttribute("msg", new MyMessage("Successfully added!! ","alert-success"));
		return "pharmacy/add_medicine";
		
		}catch (Exception e) {
			// TODO: handle exception
			m.addAttribute("medicines", medicine);
			session.setAttribute("msg", new MyMessage("Something went wrong!! "+e.getMessage(),"alert-danger"));
			return "pharmacy/add_medicine";
		}
		
	}

	/*
	 * @RequestMapping("/process-order") public String
	 * processOrder(@ModelAttribute("medicineList") List<Medicine> medicineList,
	 * Principal principal, Model model) {
	 * 
	 * String username = principal.getName(); User user =
	 * userRepository.getUserByUserName(username);
	 * 
	 * for (Medicine medicine : medicineList) { medicine.setUser(user);
	 * user.getMedicines().add(medicine); }
	 * 
	 * userRepository.save(user);
	 * System.out.println("Medicines added to the database");
	 * 
	 * List<Medicine> medicines =
	 * medicineRepository.findMedicinesById(user.getId());
	 * Collections.sort(medicines,
	 * Comparator.comparingInt(Medicine::getMid).reversed());
	 * model.addAttribute("medicines", medicines);
	 * 
	 * return "pharmacy/order_medicine"; }
	 * 
	 */

	@RequestMapping("/view-medicine")
	public String viewMedicine(Model m, Principal p) {
		m.addAttribute("title", "View Medicine");
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<Medicine> medicines = this.medicineRepository.findMedicinesById(tempUser.getId());
		m.addAttribute("medicines", medicines);
		return "pharmacy/view_medicine";
	}

	@GetMapping("/delete/{mid}")
	public String deleteMedicine(@PathVariable("mid") int mid, Model m, Principal p) {

		Medicine medicine = this.medicineRepository.findById(mid).get();
		User tempUser = this.userRepository.getUserByUserName(p.getName());
		tempUser.getMedicines().remove(medicine);
		this.userRepository.save(tempUser);
		return "redirect:/pharmacy/view-medicine";
	}

	@GetMapping("/deleteProcessOrder/{mid}")
	public String deleteMedicineFromProcessOrder(@PathVariable("mid") int mid, Model m, Principal p) {

		Medicine medicine = this.medicineRepository.findById(mid).get();
		User tempUser = this.userRepository.getUserByUserName(p.getName());
		tempUser.getMedicines().remove(medicine);
		this.userRepository.save(tempUser);

		String name = p.getName();
		User tempUser1 = this.userRepository.getUserByUserName(name);
		List<Medicine> medicines = this.medicineRepository.findMedicinesById(tempUser1.getId());
		m.addAttribute("medicines", medicines);
		return "pharmacy/order_medicine";
	}

	@PostMapping("/updateMedicine/{mid}")
	public String updateMedicine(@PathVariable("mid") int id, Model m) {
		m.addAttribute("title", "Update medicine");
		Medicine tempMedicine = this.medicineRepository.findById(id).get();
		m.addAttribute("medicine", tempMedicine);
		return "pharmacy/update_medicine";
	}

	@PostMapping("/process-update")
	public String processUpdate(@ModelAttribute Medicine m, Principal p, Model model) {
//		Medicine oldMedcine = this.medicineRepository.findById(m.getMid()).get();
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		m.setUser(tempUser);
		this.medicineRepository.save(m);

		/*
		 * List<Medicine> medicines =
		 * this.medicineRepository.findMedicinesById(tempUser.getId());
		 * model.addAttribute("medicines", medicines);
		 */
		return "pharmacy/update_medicine";
	}

}
