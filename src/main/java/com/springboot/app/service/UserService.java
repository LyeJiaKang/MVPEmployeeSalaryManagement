package com.springboot.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	public abstract boolean uploadFile(MultipartFile file);
}
