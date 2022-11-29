package com.example.spring.security;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.spring.core.constants.UserRole;

public class AuthenticatedUser extends User {

	private static final long serialVersionUID = -1114997325868536102L;

	private final Set<UserRole> roles = EnumSet.noneOf(UserRole.class);

	private String firstName;
	private String lastName;

	public AuthenticatedUser(String username, Collection<UserRole> roles, Collection<? extends GrantedAuthority> authorities) {
		super(username, "", authorities);
		this.roles.addAll(roles);
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
