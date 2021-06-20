package com.monkhood6.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.monkhood6.service.impl.UserServiceImpl;

/* This is a security class. It will handle all the anonymous entry or exit points, session Management, 
 * _csrf Token, Role Based Authentication & Authorization, Login Processing etc.
 * 
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserServiceImpl userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		/* Default or Open URLS for all users */
		.antMatchers("/css/**").permitAll()
		.antMatchers("/imgs/**").permitAll()
		.antMatchers("/js/**").permitAll()
		.antMatchers(HttpMethod.GET, "/").permitAll()
		.antMatchers(HttpMethod.GET, "/index").permitAll()
		.antMatchers(HttpMethod.GET, "/register").permitAll()
		.antMatchers(HttpMethod.GET, "/signup").permitAll()
		.antMatchers(HttpMethod.GET, "/signin").permitAll()
		.antMatchers(HttpMethod.POST, "/register").permitAll()
		.antMatchers(HttpMethod.GET, "/verify/user/**").permitAll()
		.antMatchers("/reset-password").permitAll()
		.antMatchers("/account/reset-password").permitAll()
		.antMatchers("/u/**").hasAuthority("ROLE_USER")
		.antMatchers("/emp/**").hasAuthority("ROLE_EMP")
		.antMatchers("/property/**").hasAnyAuthority("ROLE_USER", "ROLE_EMP")
		.antMatchers("/api/u/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers("/api/e/**").hasAnyAuthority("ROLE_EMP","ROLE_ADMIN")
		/* Only accessed by Admin */
		.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
		.antMatchers("/api/a/**").hasAnyAuthority("ROLE_ADMIN")
		.anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/welcome")
					.failureUrl("/login?error")
					.usernameParameter("email")
					.passwordParameter("password")
					.permitAll()
				.and()
					.logout()
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
					.logoutUrl("/logout")
					.logoutSuccessUrl("/index")
					.permitAll()
				.and()
					.exceptionHandling()
					.accessDeniedPage("/403");
		
	}

}
