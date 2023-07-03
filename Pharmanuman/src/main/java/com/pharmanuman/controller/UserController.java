package com.pharmanuman.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.User;

@Controller
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	/*
	 * @GetMapping("/users") public String getUsers(Model model) { List<User>
	 * userList = userRepository.findAll(); model.addAttribute("users", userList);
	 * return "userList"; }
	 */
    
    
    //badh dekhe partai 7/03
    
	/*
	 * @GetMapping("/users/stockist") public String getUserRoleStockist(Model model)
	 * { List<User> userList = userRepository.findAll(); List<User> stockistList =
	 * userList.stream() .filter(user -> user.getRole().equals("ROLE_STOCKIST"))
	 * .collect(Collectors.toList()); model.addAttribute("stockist", stockistList);
	 * return "userList"; }
	 */
    
	/*
	 * @GetMapping("/users/pharmacy") public String getUsers(Model model) {
	 * List<User> userList = userRepository.findAll(); List<User> stockistList =
	 * userList.stream() .filter(user -> user.getRole().equals("ROLE_PHARMACY"))
	 * .collect(Collectors.toList()); model.addAttribute("stockist", stockistList);
	 * return "userList"; }
	 */
}

