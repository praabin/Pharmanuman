package com.pharmanuman.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotController {
	

	private Random r = new Random(100000);

	@RequestMapping("/forgot")
	public String openForgotPasswordForm() {
		return "pharmacy/forgot_password";
	}

	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email) {
		System.out.println("Email: " + email);
		// generating otp

		int otp = r.nextInt(999999);

		System.out.println("OTP:: " + otp);
		// now it's time to write code to send otp to email
		         
		return "verify_otp";
	}

}