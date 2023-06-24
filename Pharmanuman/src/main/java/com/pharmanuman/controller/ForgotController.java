package com.pharmanuman.controller;

import java.security.Principal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.User;
import com.pharmanuman.helper.MyMessage;
import com.pharmanuman.services.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private Random r = new Random(100000);

	@RequestMapping("/forgot")
	public String openForgotPasswordForm() {
		return "pharmacy/forgot_password";
	}

	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email, HttpSession session) {
		System.out.println("Email: " + email);
		// generating otp

		int otp = r.nextInt(999999);

		System.out.println("OTP:: " + otp);
		// now it's time to write code to send otp to email

		String subject = "OTP from Pharmanuman";
//		String message = "<h1> OTP = " + otp + "</h1>";

		String message = "<div style=\"border: 1px solid #e1e1e1; border-radius: 5px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\">"
				+ "<div style=\"background-color: #003399; padding: 20px; border-radius: 5px 5px 0 0;\">"
				+ "<h1 style=\"color: #fff; margin: 0; font-size: 24px;\">Pharmanuman</h1>"
				+ "<h3 style=\"color: #fff; margin-top: 10px; font-size: 18px;\">OTP Verification</h3>" + "</div>"
				+ "<div style=\"padding: 20px;\">" + "<p style=\"font-size: 16px; color: #333;\">Dear User,</p>"
				+ "<p style=\"font-size: 18px; color: #333;\">Your One-Time Password (OTP) for verification is:</p>"
				+ "<h2 style=\"font-size: 28px; color: #333; margin-top: 10px;\">" + otp + "</h2>"
				+ "<p style=\"font-size: 16px; color: #333;\">Please use this OTP to complete the verification process.</p>"
				+ "</div>" + "<div style=\"background-color: #f8f8f8; padding: 20px; border-radius: 0 0 5px 5px;\">"
				+ "<p style=\"font-size: 14px; color: #666;\">Pharmanuman - Secure Verification Service</p>" + "</div>"
				+ "</div>";

		String to = email;

		boolean flag = this.emailService.sendTo(to, subject, message);

		if (flag) {
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			return "verify_otp";
		} else {
			session.setAttribute("message", "Check your email id...");
			return "pharmacy/forgot_password";

		}

	}

	// email ko lagi

	@PostMapping("/send-otp-email")
	public String sendOTPEmail(@RequestParam("email") String email, HttpSession session) {
		System.out.println("Email: " + email);
		// generating otp

		int otp = r.nextInt(999999);

		System.out.println("OTP:: " + otp);
		// now it's time to write code to send otp to email

		String subject = "OTP from Pharmanuman";
//		String message = "<h1> OTP = " + otp + "</h1>";

		String message = "<div style=\"border: 1px solid #e1e1e1; border-radius: 5px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\">"
				+ "<div style=\"background-color: #003399; padding: 20px; border-radius: 5px 5px 0 0;\">"
				+ "<h1 style=\"color: #fff; margin: 0; font-size: 24px;\">Pharmanuman</h1>"
				+ "<h3 style=\"color: #fff; margin-top: 10px; font-size: 18px;\">OTP Verification</h3>" + "</div>"
				+ "<div style=\"padding: 20px;\">" + "<p style=\"font-size: 16px; color: #333;\">Dear User,</p>"
				+ "<p style=\"font-size: 18px; color: #333;\">Your One-Time Password (OTP) for verification is:</p>"
				+ "<h2 style=\"font-size: 28px; color: #333; margin-top: 10px;\">" + otp + "</h2>"
				+ "<p style=\"font-size: 16px; color: #333;\">Please use this OTP to complete the verification process.</p>"
				+ "</div>" + "<div style=\"background-color: #f8f8f8; padding: 20px; border-radius: 0 0 5px 5px;\">"
				+ "<p style=\"font-size: 14px; color: #666;\">Pharmanuman - Secure Verification Service</p>" + "</div>"
				+ "</div>";

		String to = email;

		boolean flag = this.emailService.sendTo(to, subject, message);

		if (flag) {
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			return "verify_otp_email";
		} else {
			session.setAttribute("message", "Check your email id...");
			return "pharmacy/forgot_password";

		}

	}

	@PostMapping("/verify-otp")
	public String verifyOTP(@RequestParam("otp") int otp, HttpSession session) {
		int myOtp = (int) session.getAttribute("myotp");
		String email = (String) session.getAttribute("email");

		if (myOtp == otp) {
			User tempUser = this.userRepository.getUserByUserName(email);

			if (tempUser == null) {

				session.setAttribute("message", "User doesn't exist with this email...");
				return "pharmacy/forgot_password";

			} else {

				return "change_password";
			}
		} else {
			session.setAttribute("message", "You have entered wrong OTP");
			return "verify_otp";
		}

	}

	@PostMapping("/verify-otp-email")
	public String verifyOTPForEmail(@RequestParam("otp") int otp, HttpSession session) {
		int myOtp = (int) session.getAttribute("myotp");
		String email = (String) session.getAttribute("email");

		System.out.println(
				"In this article, we will learn to send the activation link to the user’s email address once they registered on our spring boot application.\r\n"
						+ "\r\n"
						+ "In most of the websites, when you registered or create a new account, then you automatically received an activation link to your email address, and once you click on that link, your account gets fully activated.\r\n"
						+ "\r\n"
						+ "So, this feature we are going to implement in our spring boot application and will build the application from scratch to finish."
						+ ""
						+ "In this article, we will learn to send the activation link to the user’s email address once they registered on our spring boot application.\r\n"
						+ "\r\n"
						+ "In most of the websites, when you registered or create a new account, then you automatically received an activation link to your email address, and once you click on that link, your account gets fully activated.\r\n"
						+ "\r\n"
						+ "So, this feature we are going to implement in our spring boot application and will build the application from scratch to finish.");
		System.out.println("prabin k check kara k mana kailkai " + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + ""
				+ "   " + email);
		session.setAttribute("email", email);

		/*
		 * if (myOtp == otp) { User tempUser =
		 * this.userRepository.getUserByUserName(email);
		 * 
		 * if (tempUser == null) {
		 * 
		 * session.setAttribute("message", "User doesn't exist with this email...");
		 * return "pharmacy/forgot_password";
		 * 
		 * } else {
		 * 
		 * return "change_password"; } } else { session.setAttribute("message",
		 * "You have entered wrong OTP"); return "verify_otp"; }
		 */

		if (myOtp == otp) {
//			session.setAttribute("email", email);
			return "dummy";
		} else {
			session.setAttribute("message", "You have entered wrong OTP, please verify first");
			return "verify_email";
		}

	}

	@PostMapping("/change-password-form")
	public String changePassword(@RequestParam("newPassword") String newPassword, HttpSession session) {
		String email = (String) session.getAttribute("email");

		User tempUser = this.userRepository.getUserByUserName(email);
		tempUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
		this.userRepository.save(tempUser);

		return "redirect:/signin?change=Password change successfully";
	}

}