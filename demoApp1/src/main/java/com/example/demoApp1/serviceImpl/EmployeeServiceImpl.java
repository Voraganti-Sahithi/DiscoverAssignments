package com.example.demoApp1.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.example.demoApp1.bo.EmployeeBO;
import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.service.EmployeeService;

@Service
@Validated
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	private EmployeeBO employeeBo;

	@Override
	public EmployeeDTO createEmployee(EmployeeDTO employee) {
		// TODO Auto-generated method stub
		return employeeBo.createEmployee(employee);
	}

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		// TODO Auto-generated method stub
		
		return employeeBo.getAllEmployees();
	}

	@Override
	public EmployeeDTO getEmployeeById(Long id) {
		// TODO Auto-generated method stub
		return employeeBo.getEmployeeById(id);
	}

	@Override
	public List<EmployeeDTO> getEmployeeByName(String name) {
		// TODO Auto-generated method stub
		return employeeBo.getEmployeeByName(name);
	}
}
