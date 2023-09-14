package com.pharmanuman.services;

import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	public boolean sendTo(String to, String subject, String message) {

		boolean f = false;

		String from = "prabinshah2018@gmail.com";

		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
		System.out.println("Properties:: " + properties);

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Step 1: to get session object
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("prabinshah2018@gmail.com", "imkictioqaogyydp");
			}

		});

		session.setDebug(true);

		// step 2: compose the message text, multimedia

		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(from);
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

			mimeMessage.setSubject(subject);
//			mimeMessage.setText(message);
			mimeMessage.setContent(message,"text/html");
			
			

			// step 3 send message using Transport class
			Transport.send(mimeMessage);
			System.out.println("Sent success....");
			f = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return f;

	}


}
