package com.springboot.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springboot.app.domain.User;

public interface UserRepository extends CrudRepository<User, String>{

	User findBylogin(String login);
	
	@Query(value = "SELECT id,login,name,salary FROM USER u WHERE u.salary >= :minSalary AND u.salary <= :maxSalary ORDER BY :sortColumn LIMIT :offset , :limit", 
			  nativeQuery = true)
	List<User> findUsersAscOrder(
			  @Param("minSalary") double minSalary, @Param("maxSalary") double maxSalary,@Param("sortColumn") int sortColumn,@Param("offset") int offset, @Param("limit") int limit);
	
	@Query(value = "SELECT id,login,name,salary FROM USER u WHERE u.salary >= :minSalary AND u.salary <= :maxSalary ORDER BY :sortColumn DESC LIMIT :offset , :limit", 
			  nativeQuery = true)
	List<User> findUsersDescOrder(
			  @Param("minSalary") double minSalary, @Param("maxSalary") double maxSalary,@Param("sortColumn") int sortColumn,@Param("offset") int offset, @Param("limit") int limit);

}
