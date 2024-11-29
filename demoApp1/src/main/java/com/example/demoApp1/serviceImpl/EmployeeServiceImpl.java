package com.example.demoApp1.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.example.demoApp1.bo.EmployeeBO;
import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.mapperclass.EmployeeMapper;
import com.example.demoApp1.service.EmployeeService;
import com.example.demoApp1.vo.EmployeeVO;

@Service
@Validated
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	private EmployeeBO employeeBo;

	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Override
	public EmployeeVO createEmployee(EmployeeDTO employee) {
		// TODO Auto-generated method stub
		EmployeeVO newEmp = employeeMapper.employeeDTOToEmployee(employee);
		return employeeBo.createEmployee(newEmp);
	}

	@Override
	public List<EmployeeVO> getAllEmployees() {
		// TODO Auto-generated method stub
		
		return employeeBo.getAllEmployees();
	}

	@Override
	public EmployeeVO getEmployeeById(Long id) {
		// TODO Auto-generated method stub
		return employeeBo.getEmployeeById(id);
	}

	@Override
	public List<EmployeeVO> getEmployeeByName(String name) {
		// TODO Auto-generated method stub
		return employeeBo.getEmployeeByName(name);
	}
}
