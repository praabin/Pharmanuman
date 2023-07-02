package com.pharmanuman.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.Medicine;
import com.pharmanuman.entities.User;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserRepository userRepository;

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
		return "admin/admin_dashboard";
	}

	@RequestMapping("/get-all-pharmacy")
	public String getAllPharmacy(Model model) {
		List<User> tempUser = this.userRepository.findUserByRole("ROLE_PHARMACY");

		model.addAttribute("pharmacy", tempUser);
		return "userlist";
	}

	@RequestMapping("/get-all-stockist")
	public String getAllStockist(Model model) {
		List<User> tempUser = this.userRepository.findUserByRole("ROLE_STOCKIST");

		model.addAttribute("pharmacy", tempUser);
		return "user_stockist";
	}

	@RequestMapping("/get-all-company")
	public String getAllCompany(Model model) {
		List<User> tempUser = this.userRepository.findUserByRole("ROLE_PC");

		model.addAttribute("pharmacy", tempUser);
		return "user_pc";
	}

	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable (value = "id") int id) {
		
		// call delete employee method 
		this.userRepository.deleteById(id);
		return "redirect:/";
	}

}
