package com.example.spring.core.constants;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum UserRole {

	ROLE_ADMIN(RoleNames.ROLE_ADMIN),
	ROLE_USER(RoleNames.ROLE_USER);

	private final String name;

	private UserRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Stream<UserRole> stream() {
		return Arrays.stream(UserRole.values());
	}

	public static Optional<UserRole> forName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			for (UserRole role : UserRole.values()) {
				if (name.equalsIgnoreCase(role.name)) {
					return Optional.of(role);
				}
			}
		}
		return Optional.empty();
	}

	public static class RoleNames {

		public static final String ROLE_ADMIN = "ROLE_ADMIN";
		public static final String ROLE_USER = "ROLE_USER";

		private RoleNames() {}
	}

}
