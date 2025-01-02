package com.example.demoApp1.bo;

import java.util.List;

import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.vo.EmployeeVO;

public interface EmployeeBO {
//	EmployeeVO createEmployee(EmployeeVO employee);
//	List<EmployeeVO> getAllEmployees();
//	EmployeeVO getEmployeeById(Long id);
//	List<EmployeeVO> getEmployeeByName(String name);

	EmployeeDTO createEmployee(EmployeeDTO employee);
	List<EmployeeDTO> getAllEmployees();
	EmployeeDTO getEmployeeById(Long id);
	List<EmployeeDTO> getEmployeeByName(String name);
	EmployeeDTO getEmployeeByIdAndName(Long id, String name);

}
