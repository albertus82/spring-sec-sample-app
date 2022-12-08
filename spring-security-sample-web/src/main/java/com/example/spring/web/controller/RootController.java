package com.example.spring.web.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.core.security.AuthenticatedUser;

@RestController
@RequestMapping("/")
public class RootController {

	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	public String get(@AuthenticationPrincipal AuthenticatedUser principal) {
		if (principal != null) {
			return "Authenticated user: " + principal.getUsername() + " - Roles: " + principal.getRoles();
		}
		else {
			return "Anonymous";
		}
	}
}
