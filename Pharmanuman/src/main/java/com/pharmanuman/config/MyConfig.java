package com.pharmanuman.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class MyConfig {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return daoAuthenticationProvider;
	}

//this is where magic happens for role based application
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().
		requestMatchers("/admin/**").hasRole("ADMIN").
		requestMatchers("/pharmacy/**").hasRole("PHARMACY").
		requestMatchers("/pc/**").hasRole("PC").
		requestMatchers("/stockist/**").hasRole("STOCKIST").
		requestMatchers("/**").permitAll().and().formLogin().loginPage("/signin")
				.loginProcessingUrl("/dologin")
				.successHandler(authenticationSuccessHandler()).
				failureUrl("/login-fail").and().csrf().disable();
		http.authenticationProvider(authenticationProvider());
		DefaultSecurityFilterChain build = http.build();
		return build;
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
	    return new RoleBasedAuthenticationSuccessHandler();
	}
}

/*
 * defaultSuccessUrl("/admin/index", true). defaultSuccessUrl("/pc/index",
 * true). defaultSuccessUrl("/stockist/index", true).
 * defaultSuccessUrl("/pharmacy/index", true)
 */
