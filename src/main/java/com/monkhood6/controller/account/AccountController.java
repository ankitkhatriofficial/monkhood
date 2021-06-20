package com.monkhood6.controller.account;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monkhood6.model.dto.UserDto;
import com.monkhood6.model.dto.UserRegDto;
import com.monkhood6.model.entity.Role;
import com.monkhood6.model.entity.User;
import com.monkhood6.service.ApiService;
import com.monkhood6.utils.Utils;

@Controller
public class AccountController {

	@Autowired
	private ApiService<User> userApiService;
	@Autowired
	private ApiService<Role> roleApiService;
	@Autowired
	private Utils utils;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/* Function to show Broken Link page */
	@GetMapping("/error-page")
	public String showBrokenLinkPage() {
		return "brokenLink.jsp";
	}

	/* Function to show Reset Password page */
	@GetMapping("/reset-password")
	public String showResetPasswordPage() {
		return "resetPassword.jsp";
	}

	/* Function to reset password and send verification email */
	@PostMapping("/reset-password")
	public String ResetPassword(@RequestParam(value = "email", required = false) String email, Model model) {
		if (email == null || email.trim().isEmpty()) {
			model.addAttribute("resetPassMsg", "Invalid Email..!");
			return "resetPassword.jsp";
		}
		User user = userApiService.getSingleResult(User.class, "email", email.trim());
		if (user == null) {
			model.addAttribute("resetPassMsg", "Reset Password link has been sent to email..!");
			return "resetPassword.jsp";
		}
		if (user.isStatus() == false) {
			model.addAttribute("resetPassMsg", "Kindly verify your account first..!");
			return "resetPassword.jsp";
		}
		String passVkey = utils.generateToken() + utils.generateToken();
		user.setPassVerificationKey(passVkey);
		user.setResetPassReqDateTime(new Date());
		utils.sendEmail(user.getEmail(), utils.getResetPasswordHTML(passVkey));
		userApiService.update(user);
		model.addAttribute("resetPassMsg", "Reset Password link has been sent to email..!");
		return "resetPassword.jsp";
	}

	/* Function to show user Account reset password */
	@GetMapping("/account/reset-password")
	public String resetPasswordUsingvkey(@RequestParam(value = "passvkey", required = false) String passVkey,
			Model model) {
		if (passVkey == null || passVkey.trim().isEmpty() || utils.getAuthentication() != null)
			return "redirect:/error-page";
		User user = userApiService.getSingleResult(User.class, "passVerificationKey", passVkey.trim());
		if (user == null || !user.getPassVerificationKey().equals(passVkey))
			return "redirect:/error-page";
		model.addAttribute("passvkey", passVkey);
		return "accountResetPassword.jsp";
	}

	/* Function to perform user Account reset password */
	@PostMapping("/account/reset-password")
	public String postresetPasswordUsingvkey(@RequestParam(value = "passvkey", required = false) String passVkey,
			@RequestParam(value = "npass", required = false) String npass,
			@RequestParam(value = "ncpass", required = false) String ncpass, Model model) {

		if (passVkey == null || passVkey.trim().isEmpty() || utils.getAuthentication() != null)
			return "redirect:/error-page";
		if (npass == null || npass.trim().isEmpty() || ncpass == null || ncpass.trim().isEmpty()
				|| !npass.equals(ncpass)) {
			model.addAttribute("accountResetPassMsg", "Invalid Password or Password Mismatch..!");
			return "accountResetPassword.jsp";
		}
		if (npass.length() < 8) {
			model.addAttribute("accountResetPassMsg", "Password is too short..!");
			return "accountResetPassword.jsp";
		}
		User user = userApiService.getSingleResult(User.class, "passVerificationKey", passVkey.trim());
		if (user == null || !user.getPassVerificationKey().equals(passVkey))
			return "redirect:/error-page";
		// if(new Date().getTime() - user.getResetPassReqDateTime().getTime()){
		// model.addAttribute("Reset Password Link got expired..!" */
		// }
		user.setPassword(passwordEncoder.encode(npass));
		user.setPassVerificationKey(null);
		user.setResetPassReqDateTime(null);
		userApiService.update(user);
		model.addAttribute("accountResetPassMsg", "Password is successfully updated..!");
		return "accountResetPassword.jsp";
	}

	/* Function to show index/Home page */
	@GetMapping(value = { "/", "/index" })
	public String showHomePage() {
		if (utils.getAuthentication() == null)
			return "index.jsp";
		else
			return "redirect:/welcome";
	}

	/* Function to show login page */
	@GetMapping(value = { "/login", "signin" })
	public String showLoginPage() {
		if (utils.getAuthentication() == null)
			return "login.jsp";
		else
			return "redirect:/welcome";
	}

	/* Function to show Register/signup page */
	@GetMapping(value = { "/register", "signup" })
	public String showRegisterPage(Model model) {
		if (utils.getAuthentication() == null) {
			model.addAttribute("user", new UserRegDto());
			return "register.jsp";
		} else
			return "redirect:/welcome";
	}

	/* Function to show welcome page (After Login) based on Roles */
	@GetMapping(value = { "/welcome" })
	public String showWelcomePage(Model model) {
		if (utils.getAuthentication().getAuthorities().toString().equals("[ROLE_ADMIN]")) {
			model.addAttribute("users",
					userApiService.getAllByQuery("from User u where u.status=true ORDER BY u.signupTime DESC"));
			return "admins/verifiedUsers.jsp";
		}
		return "welcome.jsp";
	}

	/* function to register a new user */
	@PostMapping("/register")
	public String registerNewUser(@Valid @ModelAttribute("user") UserRegDto user, BindingResult result, Model model) {
		if (result.hasErrors())
			return "register.jsp";
		else if (!user.getPassword().equals(user.getConfirmPass())) {
			model.addAttribute("regMsg", "Password does not match..!");
			return "register.jsp";
		}

		String verificationKey = utils.generateToken() + utils.generateToken();
		user.setVerificationKey(verificationKey);
		String hashPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashPassword);
		user.setSignupTime(new Date());
		user.setUserId(utils.generateToken());
		user.setStatus(false);

		/* setting the user role as "USER" i.e id == "1" (id -> 1) */
		Set<Role> roles = new HashSet<>(Arrays.asList(roleApiService.get(Role.class, 1)));
		user.setRoles(roles);

		/* Copying the Dto to Entity */
		User userToBeSaved = new User();
		BeanUtils.copyProperties(user, userToBeSaved);
		/* Attempting to save the object to database */
		try {
			userApiService.save(userToBeSaved);
			utils.sendEmail(user.getEmail(), utils.getRegHTML(verificationKey));
		} catch (ConstraintViolationException e) {
			/* Constraint violet exception like duplicate entry for email/mobile */
			user.setPassword("");
			user.setConfirmPass("");
			String fieldName = e.getConstraintName().substring(e.getConstraintName().indexOf(".") + 1);
			model.addAttribute("regMsg", "Already have an account with this " + fieldName + "..!");
			return "register.jsp";
		}
		/* Server Error */
		catch (Exception e) {
			user.setPassword("");
			user.setConfirmPass("");
			model.addAttribute("regMsg", "Error occured while Registration..!");
			return "register.jsp";
		}

		/* Registration Successful */
		model.addAttribute("regMsg", "Kindly check your email to verify the Account..!");
		model.addAttribute("user", new UserRegDto());
		return "register.jsp";
	}

	@GetMapping("/verify/user")
	public String verifyUser(@RequestParam(value = "vkey", required = false) String vkey, Model model) {
		if (vkey == null || vkey.trim().isEmpty() || utils.getAuthentication() != null)
			return "redirect:/error-page";

		User user = userApiService.getSingleResult(User.class, "verificationKey", vkey.trim());
		if (user == null || !user.getVerificationKey().equals(vkey))
			return "redirect:/error-page";
		user.setStatus(true);
		user.setVerificationKey(null);
		userApiService.update(user);
		model.addAttribute("accountVerificationMsg", "Account is successfully verified..!");
		return "accountVerificationMsg.jsp";
	}

	@GetMapping("/profile")
	public String showProfile(Model model) {
		User userInDB = userApiService.getSingleResult(User.class, "email", utils.getAuthentication().getName());
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userInDB, userDto);
		model.addAttribute("user", userDto);
		return "profile.jsp";
	}

	@PostMapping("/profile")
	public String modifyProfileDetails(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
		if (result.hasErrors())
			return "profile.jsp";
		if (!user.getEmail().equals(utils.getAuthentication().getName())) {
			model.addAttribute("profileMsg", "Access Denied..!");
			return "profile.jsp";
		}

		User userToBeUpdated = userApiService.getSingleResult(User.class, "userId", user.getUserId().trim());
		if (userToBeUpdated == null)
			return "redirect:/error-page";

		BeanUtils.copyProperties(user, userToBeUpdated);

		try {
			userApiService.update(userToBeUpdated);
		} catch (ConstraintViolationException e) {
			String fieldName = e.getConstraintName().substring(e.getConstraintName().indexOf(".") + 1);
			model.addAttribute("profileMsg", "Already have an account with this " + fieldName + "..!");
			return "profile.jsp";
		} catch (Exception e) {
			throw new RuntimeException("Exceptionn from Account Controller while updating the profile..!");
		}
		model.addAttribute("profileMsg", "Details Modified..!");
		return "profile.jsp";
	}

	@GetMapping("/change-password")
	public String showChangePasswordForm() {
		return "changePassword.jsp";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam(value = "oldpass", required = false) String oldpass,
			@RequestParam(value = "newpass", required = false) String newpass,
			@RequestParam(value = "confirmpass", required = false) String confirmpass, Model model) {

		if (oldpass == null || oldpass.trim().isEmpty() || newpass == null || newpass.trim().isEmpty()
				|| confirmpass == null || confirmpass.trim().isEmpty()) {
			model.addAttribute("changePassMsg", "Please fill out this fields..!");
			return "changePassword.jsp";
		} else if (!newpass.equals(confirmpass)) {
			model.addAttribute("changePassMsg", "Password does not Match..!");
			return "changePassword.jsp";
		}

		User userInDB = userApiService.getSingleResult(User.class, "email", utils.getAuthentication().getName());

		boolean match = passwordEncoder.matches(oldpass, userInDB.getPassword());
		if (match == false) {
			model.addAttribute("changePassMsg", "Incorrect old Password..!");
			return "changePassword.jsp";
		}

		String hashNewPassword = passwordEncoder.encode(newpass);
		userInDB.setPassword(hashNewPassword);

		try {
			userApiService.update(userInDB);
		} catch (Exception e) {
			throw new RuntimeException("Exception from AccountController while changing Password..!");
		}

		model.addAttribute("changePassMsg", "Password successfully updated..!");
		return "changePassword.jsp";
	}

}
