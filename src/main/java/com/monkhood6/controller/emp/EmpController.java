package com.monkhood6.controller.emp;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monkhood6.model.dto.UserRegDto;
import com.monkhood6.model.entity.Property;
import com.monkhood6.model.entity.Role;
import com.monkhood6.model.entity.User;
import com.monkhood6.service.ApiService;
import com.monkhood6.utils.Utils;

@Controller
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	private ApiService<User> userApiService;
	@Autowired
	private ApiService<Role> roleApiService;
	@Autowired
	private Utils utils;
	@Autowired
	private ApiService<Property> propertyApiService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/add-user")
	public String showAddUserPage(Model model) {
		model.addAttribute("user", new UserRegDto());
		return "emp/addUser.jsp";
	}

	@PostMapping("/add-user")
	public String addUser(@Valid @ModelAttribute("user") UserRegDto user, BindingResult result, Model model) {

		if (result.hasErrors())
			return "emp/addUser.jsp";
		else if (!user.getPassword().equals(user.getConfirmPass())) {
			model.addAttribute("addUserMsg", "Password does not Match..!");
			return "emp/addUser.jsp";
		}

		Role userRole = roleApiService.get(Role.class, 1);
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		String hashPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashPassword);
		user.setUserId(utils.generateToken());
		user.setSignupTime(new Date());
		user.setStatus(true);

		/* Converting the Dto to Entity */
		User userToBeSaved = new User();
		BeanUtils.copyProperties(user, userToBeSaved);
		try {
			userApiService.save(userToBeSaved);
		} catch (ConstraintViolationException e) {
			/* Constraint violet exception like duplicate entry for email/mobile */
			user.setPassword("");
			user.setConfirmPass("");
			String fieldName = e.getConstraintName().substring(e.getConstraintName().indexOf(".") + 1);
			model.addAttribute("addUserMsg", "Already have an account with this " + fieldName + "..!");
			return "emp/addUser.jsp";
		} catch (Exception e) {
			throw new RuntimeException("Exception from EmpController during saving the user Object..!");
		}
		model.addAttribute("addUserMsg", "User Added Successfully..!");
		model.addAttribute("user", new UserRegDto());
		return "emp/addUser.jsp";
	}

	@GetMapping("/search-properties")
	public String showSearchPropertyPage(Model model) {
		model.addAttribute("properties", propertyApiService
				.getAllByQuery("SELECT p FROM Property p ORDER BY p.propertyConfirmationDateTime DESC"));
		return "emp/filterProperties.jsp";
	}

	@PostMapping("/search-properties")
	public String searchProperties(@RequestParam(value = "searchBy", required = false) String searchBy,
			@RequestParam(value = "propertyDetails", required = false) String propertyDetails, Model model) {

		if (searchBy == null || searchBy.trim().isEmpty() || propertyDetails == null
				|| propertyDetails.trim().isEmpty())
			return "emp/filterProperties.jsp";

		StringBuilder query = new StringBuilder("SELECT p FROM Property p where p.availability=true AND ");

		if (searchBy.equalsIgnoreCase("locality"))
			query.append("p.locality LIKE '%" + propertyDetails.trim() + "%'");
		else if (searchBy.equalsIgnoreCase("pincode"))
			query.append("p.pincode='" + propertyDetails.trim() + "'");
		else if (searchBy.equalsIgnoreCase("state"))
			query.append("p.state LIKE '%" + propertyDetails.trim() + "%'");
		else if (searchBy.equalsIgnoreCase("rating"))
			query.append("p.rating='" + propertyDetails.trim() + "'");
		else {
			model.addAttribute("searchPropertyMsg", "Invalid Filter Attribute..!");
			return "emp/searchedUsers.jsp";
		}

		List<Property> properties = propertyApiService.getAllByQuery(query.toString());
		model.addAttribute("properties", properties);
		return "emp/filterProperties.jsp";
	}

	@GetMapping("/search-users")
	public String showSearchUserPage(Model model) {
		List<User> users = userApiService
				.getAllByQuery("SELECT u from User u JOIN u.roles ur where ur.id=1 ORDER BY u.signupTime DESC");
		model.addAttribute("users", users);
		return "emp/searchedUsers.jsp";
	}

	@PostMapping("/search-users")
	public String searchUserBy(@RequestParam(value = "searchBy", required = false) String searchBy,
			@RequestParam(value = "userDetails", required = false) String userDetails, Model model) {

		if (searchBy == null || searchBy.trim().isEmpty() || userDetails == null || userDetails.trim().isEmpty())
			return "emp/searchedUsers.jsp";

		/* Only Searches for the User which has roles ROLE_USER (i.e id = 1) */
		StringBuilder query = new StringBuilder("SELECT u FROM User u JOIN u.roles ur where ur.id=1 AND ");

		if (searchBy.equalsIgnoreCase("email"))
			query.append("u.email LIKE '" + userDetails.trim() + "%'");
		else if (searchBy.equalsIgnoreCase("userId"))
			query.append("u.userId= '" + userDetails.trim() + "'");
		else if (searchBy.equals("mobile"))
			query.append("u.mobile LIKE '" + userDetails.trim() + "%'");
		else if (searchBy.equalsIgnoreCase("name"))
			query.append("(u.firstName LIKE '" + userDetails.trim() + "%' OR u.lastName LIKE '" + userDetails.trim()
					+ "%')");
		else {
			model.addAttribute("searchUserMsg", "Invalid Filter Attribute..!");
			return "emp/searchedUsers.jsp";
		}

		List<User> list = userApiService.getAllByQuery(query.toString());
		model.addAttribute("users", list);
		return "emp/searchedUsers.jsp";
	}

}
