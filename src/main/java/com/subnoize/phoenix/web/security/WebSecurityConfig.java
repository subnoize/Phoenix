package com.subnoize.phoenix.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * @author ericb
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailService userDetailService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().and().formLogin().loginPage("/login").permitAll()
			.and().logout().permitAll();

		http.authorizeRequests().antMatchers("/webjars/**").permitAll();
		
		http.authorizeRequests().antMatchers("/splash").permitAll();
		http.authorizeRequests().antMatchers("/style/**").permitAll();

		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/test/whoami").hasAuthority("READ_PRIVILEGE");
		
		http.authorizeRequests().anyRequest().authenticated();
	}

	/**
	 * Data Access Authentication Provider
	 * 
	 * @param passwordEncoder
	 * @return
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

	/**
	 * Password Encryption Configuration
	 * 
	 * @param iteration
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder(@Value("${password.iteration}") int iteration) {
		return new BCryptPasswordEncoder(iteration);
	}

	
}
