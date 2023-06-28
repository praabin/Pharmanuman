package com.pharmanuman.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pharmanuman.dao.MedicineRepository;
import com.pharmanuman.dao.PlaceOrderRepository;
import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.Medicine;
import com.pharmanuman.entities.PlaceOrder;
import com.pharmanuman.entities.User;
import com.pharmanuman.helper.MyMessage;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/stockist")
public class StockistController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private PlaceOrderRepository placeOrderRepository;

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
		model.addAttribute("title", "User Dashboard");
		return "stockist/stockist_dashboard";
	}

	@RequestMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("title", "User Dashboard");
		return "stockist/profile";
	}

	@RequestMapping("/setting")
	public String setting(Model model) {
		model.addAttribute("title", "User Dashboard");
		return "stockist/setting";
	}

	@GetMapping("/order-medicine")
	public String openAddContactForm(Model model, Principal p) {
		model.addAttribute("title", "Order medicine");
		model.addAttribute("medicine", new Medicine());
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<Medicine> medicines = this.medicineRepository.findMedicinesById(tempUser.getId());
		model.addAttribute("medicines", medicines);
		return "stockist/add_stock";
	}

	@RequestMapping("/process-order")
	public String processOrder(@ModelAttribute Medicine medicine, Principal p, Model m, HttpSession session) {

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

		session.setAttribute("message", new MyMessage("Successfully Updated!! ", "alert-success"));
		return "stockist/add_stock";
	}

	@RequestMapping("/view-medicine")
	public String viewMedicine(Model m, Principal p) {
		m.addAttribute("title", "View Medicine");
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<Medicine> medicines = this.medicineRepository.findMedicinesById(tempUser.getId());

		// Sort the list alphabetically by name
//		Collections.sort(medicines, Comparator.comparing(Medicine::getName));
		Collections.sort(medicines, Comparator.comparing(Medicine::getName, String.CASE_INSENSITIVE_ORDER));

		m.addAttribute("medicines", medicines);
		return "stockist/view_stock";
	}

	@GetMapping("/delete/{mid}")
	public String deleteMedicineFromStockist(@PathVariable("mid") int mid, Model m, Principal p, HttpSession session) {

		Medicine medicine = this.medicineRepository.findById(mid).get();
		User tempUser = this.userRepository.getUserByUserName(p.getName());
		tempUser.getMedicines().remove(medicine);
		this.userRepository.save(tempUser);

		String name = p.getName();
		User tempUser1 = this.userRepository.getUserByUserName(name);
		List<Medicine> medicines = this.medicineRepository.findMedicinesById(tempUser1.getId());
		m.addAttribute("medicines", medicines);

		session.setAttribute("message", new MyMessage("Deleted Successfully!! ", "alert-success"));
		return "stockist/view_stock";
	}

	@PostMapping("/updateStock/{mid}")
	public String updateMedicine(@PathVariable("mid") int id, Model m) {
		m.addAttribute("title", "Update medicine");
		Medicine tempMedicine = this.medicineRepository.findById(id).get();
		m.addAttribute("medicine", tempMedicine);
		return "stockist/view_stock";
	}

	@PostMapping("/process-update")
	public String processUpdate(@ModelAttribute Medicine m, Principal p, Model model, HttpSession session) {
//		Medicine oldMedcine = this.medicineRepository.findById(m.getMid()).get();
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		m.setUser(tempUser);
		this.medicineRepository.save(m);

		List<Medicine> medicines = this.medicineRepository.findMedicinesById(tempUser.getId());
		model.addAttribute("medicines", medicines);

		session.setAttribute("message", new MyMessage("Successfully Updated!! ", "alert-success"));
		return "stockist/view_stock";
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
			return "stockist/setting";
		} else {
			System.out.println("password doesn't matches");
			session.setAttribute("msg", new MyMessage("Password doesn't match. ", "alert-danger"));
			return "stockist/setting";

		}

	}

	@RequestMapping("/medicine-details/{mid}")
	public String showMedicineDetail(@PathVariable("mid") Integer mid, Model model, Principal p) {
		System.out.println("mid " + mid);
		Optional<Medicine> medicineOptional = this.medicineRepository.findById(mid);
		Medicine medicine = medicineOptional.get();
		String name = p.getName();
		User user = this.userRepository.getUserByUserName(name);
		if (user.getId() == medicine.getUser().getId()) {
			model.addAttribute("medicine", medicine);
			model.addAttribute("title", medicine.getName());
		}

		return "stockist/medicine_details";
	}

	@RequestMapping("/see-order-stockist")
	public String seeOrder(Model model, Principal principal) {

		String username = principal.getName();

		User user = userRepository.getUserByUserName(username);

		List<PlaceOrder> orders = placeOrderRepository.findPlaceOrderByOrderStockist(user.getName());

		model.addAttribute("placeorder", orders);

		return "stockist/see_order_stockist";

	}

	@PostMapping("/updateOrder/{poid}")
	public String updateOrder(@PathVariable("poid") int poid, Model m) {
		m.addAttribute("title", "Update order");
		PlaceOrder placeOrder = this.placeOrderRepository.findById(poid).get();
		m.addAttribute("order", placeOrder);
		return "stockist/update_order";
	}

	@PostMapping("/process-update-order")
	public String processUpdateOrder(@ModelAttribute PlaceOrder m, Principal p, Model model) {

		int a = m.getUser().getId();
//		System.out.println(a +" user id");

		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
//		tempUser.setId(a);
		m.setUser(tempUser);
		this.placeOrderRepository.save(m);
		return "redirect:/stockist/see-order-stockist";
	}

}
