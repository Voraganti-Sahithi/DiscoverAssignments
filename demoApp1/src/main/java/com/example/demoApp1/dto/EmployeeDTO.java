package com.example.demoApp1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EmployeeDTO {
	
	@Schema(description = "Employee Id of the Employee", example = "1", required = true)
	private Long id;
	
	@Schema(description = "Name of the Employee", example = "Sahithi", required = true)
	private String name;
	
	@Schema(description = "Age of the Employee", example = "john.doe@example.com", required = true)
	private Integer age;
	
	// Getters and Setters	    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
