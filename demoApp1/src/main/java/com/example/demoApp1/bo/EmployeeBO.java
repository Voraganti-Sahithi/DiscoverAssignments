package com.example.demoApp1.bo;

import java.util.List;
import com.example.demoApp1.vo.EmployeeVO;

public interface EmployeeBO {
	EmployeeVO createEmployee(EmployeeVO employee);
	List<EmployeeVO> getAllEmployees();
	EmployeeVO getEmployeeById(Long id);
	List<EmployeeVO> getEmployeeByName(String name);
}
