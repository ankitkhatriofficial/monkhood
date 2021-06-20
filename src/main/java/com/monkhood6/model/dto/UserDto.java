package com.monkhood6.model.dto;

import java.util.Collection;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.monkhood6.model.entity.Role;

public class UserDto {

	@Column(length = 40)
	private String userId;

	@NotNull(message = "First Name cannot be empty..!")
	@NotBlank(message = "First Name cannot be empty..!")
	private String firstName;

	@NotNull(message = "Last Name cannot be empty..!")
	@NotBlank(message = "Last Name cannot be empty..!")
	private String lastName;

	@Email(message = "Invalid email..!")
	@NotBlank(message = "Email cannot be empty..!")
	@Length(max = 60, message = "Length of email is too large..!")
	private String email;

	@Pattern(regexp = "[6-9][0-9]{9}", message = "Invalid Phone Number..!")
	private String mobile;

	private Collection<Role> roles;

	private boolean status;

	/* Constructor */
	public UserDto() {
		super();
	}

	/* ----------------------------Getter and Setter---------------------------- */

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("UserDto [userId=%s, firstName=%s, lastName=%s, email=%s, mobile=%s, roles=%s, status=%s]",
				userId, firstName, lastName, email, mobile, roles, status);
	}

}
