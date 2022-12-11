package com.example.spring.web.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.core.constants.UserRole.RoleNames;
import com.example.spring.core.security.AuthenticatedUser;

@RestController
public class SampleController {

	@RequestMapping(path = "/public/dynamic/user-info", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getUserInfo(@AuthenticationPrincipal AuthenticatedUser principal) {
		return getCurrentUserInfo(principal);
	}

	@Secured(RoleNames.ROLE_ADMIN)
	@RequestMapping(path = "/secured/dynamic/admin-only-secured", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getAdminOnly(@AuthenticationPrincipal AuthenticatedUser principal) {
		return getCurrentUserInfo(principal);
	}

	@RequestMapping(path = "/secured/dynamic/admin/admin-only-matcher", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getAdminOnly2(@AuthenticationPrincipal AuthenticatedUser principal) {
		return getCurrentUserInfo(principal);
	}

	@RequestMapping(path = "/secured/dynamic/all-roles", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getUserOnly(@AuthenticationPrincipal AuthenticatedUser principal) {
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
