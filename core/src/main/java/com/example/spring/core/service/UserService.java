package com.example.spring.core.service;

import org.springframework.stereotype.Service;

import com.example.spring.core.dto.UserDTO;

@Service
public class UserService {

	public UserDTO findUserById(String userId) {
		return new UserDTO(userId, "John", "Smith");
	}

}
