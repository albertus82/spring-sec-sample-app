package com.example.spring.web.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.core.constants.UserRole.RoleNames;
import com.example.spring.core.security.AuthenticatedUser;

@RestController
@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE)
public class SampleController {

	@GetMapping("/public/dynamic/user-info")
	public String getUserInfo(@AuthenticationPrincipal AuthenticatedUser principal) {
		return getCurrentUserInfo(principal);
	}

	@Secured(RoleNames.ROLE_ADMIN)
	@GetMapping("/secured/dynamic/admin-only-secured")
	public String getAdminOnlySecured(@AuthenticationPrincipal AuthenticatedUser principal) {
		return getCurrentUserInfo(principal);
	}

	@GetMapping("/secured/dynamic/admin/admin-only-matcher")
	public String getAdminOnlyMatcher(@AuthenticationPrincipal AuthenticatedUser principal) {
		return getCurrentUserInfo(principal);
	}

	@GetMapping("/secured/dynamic/all-roles")
	public String getAllRoles(@AuthenticationPrincipal AuthenticatedUser principal) {
		return getCurrentUserInfo(principal);
	}

	private static String getCurrentUserInfo(AuthenticatedUser principal) {
		if (principal != null) {
			return "Authenticated user: " + principal.getUsername() + " - Roles: " + principal.getRoles();
		}
		else {
			return "Anonymous";
		}
	}

}
