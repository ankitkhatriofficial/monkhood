package com.monkhood6.utils;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	/* Set your Email Id and encrypted password for Sending Emails */
	private String sender = "ankit@gmail.com";
	private String password = "jkehkrhrherk";

	public String generateToken() {
		return UUID.randomUUID().toString();
	}

	public String getSystemPath() {
		return System.getProperty("user.dir");
	}

	public Authentication getAuthentication() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || AnonymousAuthenticationToken.class.isAssignableFrom(auth.getClass()))
			return null;
		return auth;
	}

	public void sendEmail(String receiptent, String html) {
		// Getting system properties
		Properties properties = System.getProperties();
		// Setting up mail server
		properties.put("mail.smtp.auth", "true"); // for username and password authentication
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com"); // here host is gmail.com
		properties.put("mail.smtp.port", "587");
		Session gs = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password); // pass your email id and password here

			}
		});
		try {
			messageContent(gs, sender, receiptent, html);
		} catch (Exception e) {
			throw new RuntimeException("Exception from Utils while sending Registration Email..!");
		}
	}

	/* Message Sender Email */
	private Message messageContent(Session gs, String emailId, String receiver, String html) throws Exception {
		try {
			Message msg = new MimeMessage(gs);
			msg.setFrom(new InternetAddress("MONKHOOD"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			msg.setSubject("Account verification mail"); // to set the subject (not mandatory)
			msg.setContent(html, "text/html");
			Transport.send(msg);
			return msg;
		} catch (MessagingException e) {
		}
		return null;
	}

	/* HTML to send Email for Account Confirmation */
	public String getRegHTML(String vkey) {
		return "<img src='https://picsum.photos/220' class='top_img' /><h2 >Hello You registered an account on monkhood.in, before beingable to use your account you need to verify that this is your emailaddress by clicking here:</h2><a href='http://localhost:8080/verify/user?vkey="
				+ vkey + "&timestamp=" + new Date().getTime() + "&securePath=true&varArgPch=" + generateToken()
				+ "'>Verify Now</a><p>If you did not create an account, no further action is required.</p>'";
	}

	/* HTML to send Reset password */
	public String getResetPasswordHTML(String passVkey) {
		return "<img src='https://picsum.photos/220' class='top_img' /><h2 >Hello You requested Reset password on monkhood.in, inorder to reset your password by clicking here:</h2><a href='http://localhost:8080/account/reset-password?passvkey="
				+ passVkey + "&timestamp=" + new Date().getTime() + "&securePath=true&varArgPch=" + generateToken()
				+ "'>Reset Password</a><p>If you did not request for password reset, kindly keep updating your password to be safe.</p>'";
	}
}
