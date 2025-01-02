package com.example.demoApp1.serviceImpl;

import java.util.ArrayList;
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
	public EmployeeVO createEmployee(EmployeeVO employee) {
		// TODO Auto-generated method stub
		EmployeeDTO newEmp = employeeMapper.employeeToEmployeeDTO(employee);
		EmployeeDTO resEmp = employeeBo.createEmployee(newEmp);
		EmployeeVO result = employeeMapper.employeeDTOToEmployee(resEmp);
		return result;
	}

	@Override
	public List<EmployeeVO> getAllEmployees() {
		// TODO Auto-generated method stub
		
		List<EmployeeDTO> emp = employeeBo.getAllEmployees();
		List<EmployeeVO> result = new ArrayList<>();
		for(EmployeeDTO e : emp) {
			EmployeeVO res = employeeMapper.employeeDTOToEmployee(e);
			result.add(res);
		}
		return result;
			
	}

	@Override
	public EmployeeVO getEmployeeById(Long id) {
		// TODO Auto-generated method stub
		EmployeeDTO emp = employeeBo.getEmployeeById(id);
		EmployeeVO result = employeeMapper.employeeDTOToEmployee(emp);
		return result;
	}

	@Override
	public List<EmployeeVO> getEmployeeByName(String name) {
		// TODO Auto-generated method stub
		List<EmployeeDTO> emp = employeeBo.getEmployeeByName(name);
		List<EmployeeVO> result = new ArrayList<>();
		for(EmployeeDTO e : emp) {
			EmployeeVO res = employeeMapper.employeeDTOToEmployee(e);
			result.add(res);
		}
		return result;
	}

	@Override
	public EmployeeVO getEmployeeByIdAndName(Long id, String name) {
		// TODO Auto-generated method stub
		EmployeeDTO emp = employeeBo.getEmployeeByIdAndName(id, name);
		EmployeeVO employee = employeeMapper.employeeDTOToEmployee(emp);
		return employee;
	}
}
