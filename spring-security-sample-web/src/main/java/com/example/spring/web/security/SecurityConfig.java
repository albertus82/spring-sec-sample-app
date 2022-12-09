package com.example.spring.web.security;

import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import com.example.spring.core.constants.UserRole;
import com.example.spring.core.constants.UserRole.RoleNames;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig implements ApplicationContextAware {

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.jee().authenticatedUserDetailsService(token -> context.getBean(UserDetailsService.class).loadUserDetails(token)) // lambda required for warm context refresh
				.mappableAuthorities(UserRole.stream().map(UserRole::getName).collect(Collectors.toSet())).and().authorizeHttpRequests().requestMatchers("/admin/**").hasAuthority(RoleNames.ROLE_ADMIN).and().csrf().disable().logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));
		return http.build();
	}

	//https://github.com/spring-projects/spring-security/issues/12319#issuecomment-1338377623
	public static class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
		@Override
		protected String getDispatcherWebApplicationContextSuffix() {
			return AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
		}
	}

}
