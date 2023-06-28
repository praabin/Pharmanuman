package com.pharmanuman.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
@RequestMapping("/pharmacy")
public class PharmacyController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private PlaceOrderRepository placeOrderRepository;

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
			session.setAttribute("msg", new MyMessage("Successfully added!! ", "alert-success"));

			return "pharmacy/add_medicine";

		} catch (Exception e) {

			m.addAttribute("medicines", medicine);
			session.setAttribute("msg", new MyMessage("Something went wrong!! " + e.getMessage(), "alert-danger"));
			return "pharmacy/add_medicine";
		}

	}

	@RequestMapping("/view-medicine")
	public String viewMedicine(Model m, Principal p) {
		m.addAttribute("title", "View Medicine");
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<Medicine> medicines = this.medicineRepository.findMedicinesById(tempUser.getId());
		m.addAttribute("medicines", medicines);
		return "pharmacy/view_medicine";
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
		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		m.setUser(tempUser);
		this.medicineRepository.save(m);
		return "pharmacy/update_medicine";
	}

	// password change module
	@RequestMapping("/setting")
	public String setting(Model model) {
		model.addAttribute("title", "User Dashboard");
		return "pharmacy/setting";
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
			return "pharmacy/setting";
		} else {
			System.out.println("password doesn't matches");
			session.setAttribute("msg", new MyMessage("Password doesn't match. ", "alert-danger"));
			return "pharmacy/setting";

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

		return "pharmacy/medicine_details";
	}

	// order

	@PostMapping("/process-order-form")
	public String proceOrderForm(@ModelAttribute PlaceOrder placeOrder, Model m, Principal p, HttpSession session) {

		User tempUser = this.userRepository.getUserByUserName(p.getName());
		double price = 0.0;
		placeOrder.setPrice(price);
		placeOrder.setStatus("Pending");

		placeOrder.setPharmacyName(tempUser.getName());
		placeOrder.setTotal(price);

		placeOrder.setUser(tempUser);

		tempUser.getPlaceOrders().add(placeOrder);
		this.userRepository.save(tempUser);
		System.out.println("pharmacy name " + placeOrder.getPharmacyName());
		session.setAttribute("pharmacyname", placeOrder.getPharmacyName());

//		System.out.println("medicine added to database" + placeOrder);

		return "pharmacy/add_order";

	}

	@RequestMapping("/see-order")
	public String seeOrder(Model model, Principal p) {

		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		List<PlaceOrder> orders = this.placeOrderRepository.findPlaceOrderById(tempUser.getId());
		model.addAttribute("placeorder", orders);

		return "pharmacy/see_order";
	}

	// to open order form
	@GetMapping("/users")
	public String getUserRoleStockist(Model model) {
		model.addAttribute("title", "Order medicine");
		model.addAttribute("placeorder", new PlaceOrder());

		List<User> stockistUsers = userRepository.findByRole("ROLE_STOCKIST");
		System.out.println("list of users" + stockistUsers);
		model.addAttribute("stockistUsers", stockistUsers);
		return "pharmacy/add_order";
	}

	// to open update form

	@PostMapping("/updateOrder/{poid}")
	public String updateOrder(@PathVariable("poid") int poid, Model m) {
		m.addAttribute("title", "Update ordere");
		PlaceOrder placeOrder = this.placeOrderRepository.findById(poid).get();
		m.addAttribute("order", placeOrder);
		return "pharmacy/update_order";
	}

	@PostMapping("/process-update-order")
	public String processUpdateOrder(@ModelAttribute PlaceOrder m, Principal p, Model model) {

		m.setStatus("Pending");

		String name = p.getName();
		User tempUser = this.userRepository.getUserByUserName(name);

		m.setUser(tempUser);

		this.placeOrderRepository.save(m);
		return "redirect:/pharmacy/see-order";
	}

	@GetMapping("/delete/{mid}")
	public String deleteMedicine(@PathVariable("mid") int mid, Model m, Principal p) {

		Medicine medicine = this.medicineRepository.findById(mid).get();
		User tempUser = this.userRepository.getUserByUserName(p.getName());
		tempUser.getMedicines().remove(medicine);
		this.userRepository.save(tempUser);
		return "redirect:/pharmacy/view-medicine";
	}

	@GetMapping("/delete-order/{poid}")
	public String deleteOrder(@PathVariable("poid") int poid, Model m, Principal p) {

		PlaceOrder medicine = this.placeOrderRepository.findById(poid).get();
		System.out.println("order id " + medicine.getPoid());
		User tempUser = this.userRepository.getUserByUserName(p.getName());
		tempUser.getPlaceOrders().remove(medicine);
		this.userRepository.save(tempUser);
		System.out.println("deleted successfully");
		return "redirect:/pharmacy/see-order";
	}

	/*
	 * @RequestMapping("/see-order-stockist") public String seeUpdatedOrder(Model
	 * model, Principal principal) {
	 * 
	 * // List<PlaceOrder> findAll = this.placeOrderRepository.findAll();
	 * 
	 * String name = principal.getName(); User tempUser =
	 * this.userRepository.getUserByUserName(name); int id = tempUser.getId();
	 * List<PlaceOrder> findAll = this.placeOrderRepository.findPlaceOrderById(id);
	 * 
	 * 
	 * PlaceOrder foundPlaceOrder = null;
	 * 
	 * for (PlaceOrder placeOrder : findAll) {
	 * 
	 * foundPlaceOrder = placeOrder; }
	 * 
	 * List<PlaceOrder> orders = this.placeOrderRepository
	 * .findPlaceOrderByPharmacyName(foundPlaceOrder.getPharmacyName());
	 * 
	 * model.addAttribute("orderr", orders); return "pharmacy/see_order_stockist";
	 * 
	 * }
	 */

	@RequestMapping("/see-order-stockist")
	public String seeUpdatedOrder(Model model, Principal principal) {
		String name = principal.getName();
		User tempUser = this.userRepository.getUserByUserName(name);
		int id = tempUser.getId();
		List<PlaceOrder> findAll = this.placeOrderRepository.findPlaceOrderById(id);

		PlaceOrder foundPlaceOrder = null;

		for (PlaceOrder placeOrder : findAll) {
			if (placeOrder.getPharmacyName() != null) {
				foundPlaceOrder = placeOrder;
				break; // Exit the loop once a non-null pharmacyName is found
			}
		}

		List<PlaceOrder> orders = new ArrayList<>();
		if (foundPlaceOrder != null && foundPlaceOrder.getPharmacyName() != null) {
			orders = this.placeOrderRepository.findPlaceOrderByPharmacyName(foundPlaceOrder.getPharmacyName());
		}

		model.addAttribute("orderr", orders);
		return "pharmacy/see_order_stockist";
	}

}
