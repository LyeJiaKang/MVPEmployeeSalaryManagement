package com.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.springboot.app.domain.User;

public interface UserRepository extends CrudRepository<User, String>{

}
