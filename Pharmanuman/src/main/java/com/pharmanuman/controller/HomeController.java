package com.pharmanuman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.User;
import com.pharmanuman.helper.MyMessage;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String home(Model m) {
		m.addAttribute("title", "Home");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model m) {
		m.addAttribute("title", "About");
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Signup");
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping("/signin")
	public String login(Model model) {
		model.addAttribute("title", "Signup");
		return "signin";
	}

	// handler for registering user
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {
			if (!agreement) {
				System.out.println("You haven't agreed terms and conditions.");
				throw new Exception("You haven't agreed terms and conditions.");
			}

			if (result1.hasErrors()) {
				System.out.println("Error " + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}
//			user.setRole("ROLE_PHARMACY");
			
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			System.out.println("Agreement " + agreement);
			System.out.println("User " + user);
			this.userRepository.save(user);
			model.addAttribute("user", new User());
			model.addAttribute("session", session);

			session.setAttribute("message", new MyMessage("Successfully Registered!", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new MyMessage("Something went wrong!" + e.getMessage(), "alert-danger"));
			return "signup";
		}

	}

}
