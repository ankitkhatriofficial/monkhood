package com.monkhood6.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email", name = "email"),
		@UniqueConstraint(columnNames = "mobile", name = "mobile"),
		@UniqueConstraint(columnNames = "userId", name = "userId") })
public class User implements Serializable {

	private static final long serialVersionUID = -4821401928567812661L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 40)
	private String userId;

	@NotNull(message = "First Name cannot be empty..!")
	@NotBlank(message = "First Name cannot be empty..!")
	@Column(length = 25)
	private String firstName;

	@NotNull(message = "Last Name cannot be empty..!")
	@NotBlank(message = "Last Name cannot be empty..!")
	private String lastName;

	@Email(message = "Invalid email..!")
	@NotBlank(message = "Email cannot be empty..!")
	@Length(max = 60, message = "Length of email is too large..!")
	@Column(length = 60)
	private String email;

	@Pattern(regexp = "[6-9][0-9]{9}", message = "Invalid Phone Number..!")
	@Column(length = 10)
	private String mobile;

	@Size(min = 8, message = "Password is too short..!")
	private String password;

	@Transient
	private String confirmPass;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE,
			CascadeType.DETACH }, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	private Date signupTime;

	private boolean status;

	private String verificationKey;

	private String passVerificationKey;

	private Date resetPassReqDateTime;

	public String getPassVerificationKey() {
		return passVerificationKey;
	}

	public void setPassVerificationKey(String passVerificationKey) {
		this.passVerificationKey = passVerificationKey;
	}

	public Date getResetPassReqDateTime() {
		return resetPassReqDateTime;
	}

	public void setResetPassReqDateTime(Date resetPassReqDateTime) {
		this.resetPassReqDateTime = resetPassReqDateTime;
	}

	/* Constructor */
	public User() {
		super();
	}

	/* Getter and setter */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", mobile=" + mobile + ", password=" + password + ", confirmPass=" + confirmPass
				+ ", roles=" + roles + ", signupTime=" + signupTime + ", status=" + status + ", verificationKey="
				+ verificationKey + "]";
	}

}
