package com.example.spring.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.core.dto.UserDTO;
import com.example.spring.core.service.UserService;
import com.example.spring.web.dto.UserResponseDTO;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public UserResponseDTO get(@PathVariable("id") String userId) {
		UserDTO user = userService.findUserById(userId);
		return new UserResponseDTO(user.getUserId(), user.getFirstName(), user.getLastName());
	}

}
