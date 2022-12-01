package com.example.spring.security;

import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.spring.core.constants.UserRole;
import com.example.spring.core.constants.UserRole.RoleNames;

@Configuration
@ComponentScan
@EnableWebMvc
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig implements ApplicationContextAware {

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.jee().authenticatedUserDetailsService(token -> context.getBean(UserDetailsService.class).loadUserDetails(token)).mappableAuthorities(UserRole.stream().map(UserRole::getName).collect(Collectors.toSet())) // lambda required for warm context refresh
		    .and().authorizeHttpRequests().requestMatchers("/admin/**").hasAuthority(RoleNames.ROLE_ADMIN) // this doesn't work but antMatchers did 
		    .and().csrf().disable().logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));
		return http.build();
	}

}
