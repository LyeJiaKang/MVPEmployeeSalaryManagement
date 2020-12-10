package com.springboot.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private String id;
	
	@Column(name = "login", nullable = false)
	private String login;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "salary", nullable = false)
	private double salary;
	
	public User() {}
	


	public User (String id,String login,String name,double salary) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.salary = salary;
				
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}
