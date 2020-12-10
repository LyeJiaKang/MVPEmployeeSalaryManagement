package com.springboot.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.domain.User;
import com.springboot.app.repository.UserRepository;
import com.springboot.app.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepo;

	
	@PostMapping("/user/upload")
	public boolean uploadUserFile(@RequestParam("file") MultipartFile file){
		
		return userService.uploadFile(file);
	}
	
	@GetMapping("/user")
	public Optional<User> getUser(@RequestParam("id") String id){
		return userRepo.findById(id);
	}
}
