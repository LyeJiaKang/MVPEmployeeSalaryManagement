package com.springboot.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.domain.User;
import com.springboot.app.repository.UserRepository;
import com.springboot.app.service.UserService;
import com.sun.istack.NotNull;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@Validated
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepo;

	@PostMapping("/user/upload")
	public boolean uploadUserFile(@RequestBody @RequestParam("file") MultipartFile file){
		return userService.uploadFile(file);
	}
	

	@GetMapping("/user")
	public Optional<User> getUser(@NotBlank @RequestParam("id") String id){
		return userRepo.findById(id);
	}
	

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers( @Valid @NotNull @Min(0) @RequestParam("minSalary") Double minSalary,@Valid @NotNull  @RequestParam("maxSalary") Double maxSalary
			,@Valid @NotNull @RequestParam("offset") Integer offset,  @Valid @NotNull @RequestParam("limit") Integer limit, @Valid @NotBlank @NotNull @RequestParam("sort") String sort){
		
			List<User> result = new ArrayList<User>();
			HttpStatus status = null;
			
			
		if(minSalary>maxSalary||minSalary<0||maxSalary<0||offset<0||limit<1||sort.equals("")) {
			status = HttpStatus.BAD_REQUEST;
		}else {
			
			char character = sort.charAt(0);    
			int ascii = (int) character;
			String column = sort.substring(1, sort.length());
			//43 for + and 45 for -
			
			if(ascii != 43 && ascii != 45) {
				status = HttpStatus.BAD_REQUEST;
			}else if (column.equals("id")||column.equals("login")||column.equals("name")
					||column.equals("salary")) {
				result = userService.getUsers(minSalary,maxSalary,offset,limit,column,ascii);
				status = HttpStatus.OK;
			}else {
				status = HttpStatus.BAD_REQUEST;
			}
		}
		return new ResponseEntity<List<User>>(result, new HttpHeaders(), status);
	}
}