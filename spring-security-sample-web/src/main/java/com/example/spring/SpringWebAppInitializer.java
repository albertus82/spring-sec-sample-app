package com.example.spring;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import com.example.spring.core.RootConfig;
import com.example.spring.security.SecurityConfig;
import com.example.spring.web.ServletConfig;

//https://github.com/spring-projects/spring-security/issues/12319#issuecomment-1338377623
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { SecurityConfig.class, ServletConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	public static class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
		@Override
		protected String getDispatcherWebApplicationContextSuffix() {
			return AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
		}
	}

}
