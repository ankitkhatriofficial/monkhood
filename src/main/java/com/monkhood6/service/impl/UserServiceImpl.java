package com.monkhood6.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.monkhood6.model.entity.User;
import com.monkhood6.service.ApiService;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private ApiService<User> userApiService;

	/* It will load the user from the database for authentication purpose */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		if (email == null || email.trim().isEmpty())
			throw new UsernameNotFoundException("user not found");
		/* getting the user from the databse */
		User user = userApiService.getSingleResult(User.class, "email", email.trim());
		if (user == null || user.isStatus() == false)
			throw new UsernameNotFoundException("user not found");

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getAuthorities(user));
	}

	/* It will return the authorities of the user */
	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getRole()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

}
