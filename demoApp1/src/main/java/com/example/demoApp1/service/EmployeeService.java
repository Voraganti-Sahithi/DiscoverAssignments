package com.example.demoApp1.service;

import java.util.List;
import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.vo.EmployeeVO;


public interface EmployeeService {
	
	 EmployeeVO createEmployee(EmployeeDTO employee);
	 List<EmployeeVO> getAllEmployees();
	 EmployeeVO getEmployeeById(Long id);
	 List<EmployeeVO> getEmployeeByName(String name);
}