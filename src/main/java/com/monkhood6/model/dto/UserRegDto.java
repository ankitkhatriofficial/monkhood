package com.monkhood6.model.dto;

import java.util.Date;

import javax.validation.constraints.Size;

public class UserRegDto extends UserDto {

	@Size(min = 8, message = "Password is too short..!")
	private String password;

	private String confirmPass;

	private Date signupTime;

	private String verificationKey;

	/* Constructor */
	public UserRegDto() {
		super();
	}

	/* --------------------------Getters and Setters-------------------------- */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public Date getSignupTime() {
		return signupTime;
	}

	public void setSignupTime(Date signupTime) {
		this.signupTime = signupTime;
	}

	public String getVerificationKey() {
		return verificationKey;
	}

	public void setVerificationKey(String verificationKey) {
		this.verificationKey = verificationKey;
	}

}
