package com.springboot.app.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.domain.User;

public interface UserRepository extends JpaRepository<User, String>{

	User findBylogin(String login);
	
	List<User> findAllBySalaryGreaterThanEqualAndSalaryLessThanEqual(Double minSalary,Double maxSalary, Pageable sortedByPriceDesc);
	

}
