package com.example.spring.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.spring.core.constants.UserRole;
import com.example.spring.core.dto.UserDTO;
import com.example.spring.core.service.UserService;

@Service
public class UserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	private final UserService userService;

	@Autowired
	public UserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserDetails(final PreAuthenticatedAuthenticationToken token) {
		Objects.requireNonNull(token, "token must not be null");
		if (token.getPrincipal() == null) {
			throw new UsernameNotFoundException(Principal.class.getSimpleName() + " is null!");
		}

		String username = token.getPrincipal().toString().toUpperCase(Locale.ROOT);

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if (token.getDetails() instanceof GrantedAuthoritiesContainer) {
			GrantedAuthoritiesContainer details = (GrantedAuthoritiesContainer) token.getDetails();
			for (GrantedAuthority jeeRole : details.getGrantedAuthorities()) {
				authorities.add(new SimpleGrantedAuthority(jeeRole.getAuthority().toUpperCase(Locale.ROOT)));
			}
		}
		Set<UserRole> roles = decodeRoles(username, authorities);
		AuthenticatedUser user = new AuthenticatedUser(username, roles, authorities);

		token.setAuthenticated(true);

		log.info("Authenticated user: [{}].", user);

		UserDTO userDto = userService.findUserById(username);
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());

		return user;
	}

	private static Set<UserRole> decodeRoles(final String username, final Collection<? extends GrantedAuthority> authorities) {
		Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
		authorities.stream().map(GrantedAuthority::getAuthority).forEach(authority -> UserRole.forName(authority).ifPresentOrElse(roles::add, () -> log.warn("Ignored {}: [{}].", username, authority)));
		if (!roles.isEmpty()) {
			return roles;
		}
		else {
			throw new AuthenticationException("Missing role") {
				private static final long serialVersionUID = 3204690333617231158L;
			};
		}
	}

}
