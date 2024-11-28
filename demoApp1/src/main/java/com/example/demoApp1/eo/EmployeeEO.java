package com.example.demoApp1.eo;

import java.util.List;

import com.example.demoApp1.dto.EmployeeDTO;

public interface EmployeeEO {
	EmployeeDTO createEmployee(EmployeeDTO employee);
	List<EmployeeDTO> getAllEmployees();
	EmployeeDTO getEmployeeById(Long id);
	List<EmployeeDTO> getEmployeeByName(String name);
}
