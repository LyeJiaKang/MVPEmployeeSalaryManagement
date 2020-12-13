package com.springboot.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/user/upload")
	public boolean uploadUserFile(@RequestParam("file") MultipartFile file){
		return userService.uploadFile(file);
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/user")
	public Optional<User> getUser(@RequestParam("id") String id){
		return userRepo.findById(id);
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(@RequestParam("minSalary") double minSalary,@RequestParam("maxSalary") double maxSalary
			,@RequestParam("offset") int offset,@RequestParam("limit") int limit,@RequestParam("sort") String sort){
		
			List<User> result = new ArrayList<User>();
			HttpStatus status = null;
		if(minSalary < 0 || maxSalary < 0 || offset < 0 || limit < 0 || sort == null) {
			status = HttpStatus.BAD_REQUEST;
		}else {
			
			char character = sort.charAt(0);    
			int ascii = (int) character;
			//43 for + and 45 for -
			
			if(ascii != 43 && ascii != 45) {
				status = HttpStatus.BAD_REQUEST;
			}else if (sort.substring(1, sort.length()).equals("id")||sort.substring(1, sort.length()).equals("login")||sort.substring(1, sort.length()).equals("name")
					||sort.substring(1, sort.length()).equals("salary")) {
				result = userService.getUsers(minSalary,maxSalary,offset,limit,sort);
				status = HttpStatus.OK;
			}else {
				status = HttpStatus.BAD_REQUEST;
			}
		}
		return new ResponseEntity<List<User>>(result, new HttpHeaders(), status);
	}
}