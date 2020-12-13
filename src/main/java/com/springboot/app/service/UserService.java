package com.springboot.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.domain.User;

public interface UserService {
	public abstract boolean uploadFile(MultipartFile file);
	public abstract List<User> getUsers(double minSalary, double maxSalary, int offset, int limit, String sort);
}
