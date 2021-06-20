package com.monkhood6;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.monkhood6.model.entity.Role;
import com.monkhood6.model.entity.User;
import com.monkhood6.service.ApiService;
import com.monkhood6.utils.Utils;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })
public class Monkhood6Application {

	@Autowired
	private ApiService<Role> roleApiService;
	@Autowired
	private ApiService<User> userApiService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private Utils util;

	/* Main Function to Start the Program */
	public static void main(String[] args) {
		SpringApplication.run(Monkhood6Application.class, args);
	}

	/* Bcrypt Password Encoder Bean */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/* This will add roles if the corresponding role is not present */
	public void setRoles() throws Exception {
		if (roleApiService.get(Role.class, 1) == null)
			roleApiService.save(new Role("ROLE_USER"));
		if (roleApiService.get(Role.class, 2) == null)
			roleApiService.save(new Role("ROLE_EMP"));
		if (roleApiService.get(Role.class, 3) == null)
			roleApiService.save(new Role("ROLE_ADMIN"));
	}

	/* Function to add the testAdmin to Database as a default Admin */
	@PostConstruct
	public void setTestAdmin() throws Exception {
		/* Set Roles function */
		setRoles();

		User userInDB = userApiService.getSingleResult(User.class, "email", "test@admin.com");
		if (userInDB != null)
			return;

		User admin = new User();
		admin.setFirstName("Test");
		admin.setLastName("Admin");
		admin.setEmail("test@admin.com");
		admin.setMobile(null);
		admin.setStatus(true);
		admin.setUserId(util.generateToken());
		admin.setSignupTime(new Date());
		Set<Role> roles = new HashSet<>(Arrays.asList(roleApiService.get(Role.class, 3)));
		admin.setRoles(roles);
		String hashPassword = passwordEncoder.encode("test@admin");
		admin.setPassword(hashPassword);

		userApiService.save(admin);
	}
}
