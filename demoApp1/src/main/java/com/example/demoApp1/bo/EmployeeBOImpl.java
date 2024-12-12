package com.example.demoApp1.bo;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.demoApp1.dao.EmployeeRepository;
import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.exceptions.EmployeeNotFoundException;
import com.example.demoApp1.exceptions.EmployeeValidationException;
import com.example.demoApp1.mapperclass.EmployeeMapper;
import com.example.demoApp1.vo.EmployeeVO;

@Component
public class EmployeeBOImpl implements EmployeeBO{
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeBOImpl.class);
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private EmployeeMapper employeeMapper; 
	

	@Override
	public List<EmployeeDTO> getAllEmployees(){
		logger.info("Fetching all employees from the database.");
		List<EmployeeVO> employees = employeeRepo.findAll();
		List<EmployeeDTO> empRes = new ArrayList<>();
		for(EmployeeVO emp: employees) {
			EmployeeDTO result = employeeMapper.employeeToEmployeeDTO(emp);
			empRes.add(result);
		}
		
		if (empRes.isEmpty()) {
			logger.warn("No employees found in the database.");
            throw new EmployeeNotFoundException("No employees found.");
        }
		logger.info("Successfully retrieved {} employees.", employees.size());
		return empRes;
	}

	public EmployeeDTO getEmployeeById(Long id) {
		logger.info("Fetching employee with ID: {}", id);
		EmployeeVO employee = employeeRepo.findById(id)
				.orElseThrow(() -> {
					logger.warn("Employee with ID {} not found.", id);
					return new EmployeeNotFoundException("Employee with ID " + id + " not found.");
				});
		 logger.info("Successfully retrieved employee: {}", employee);
		 EmployeeDTO result = employeeMapper.employeeToEmployeeDTO(employee);
		return result;
	}

	@Override
	public EmployeeDTO createEmployee(EmployeeDTO employee){
		
		logger.info("Creating a new employee with details: {}", employee);
		
		if (employee.getName() == null || employee.getName().trim().isEmpty()) {
			logger.error("Validation Failed : Name is mandatory and cannot be blank.");
	        throw new EmployeeValidationException("Name is mandatory and cannot be blank.");
	    }
	    if (employee.getName().length() < 2 || employee.getName().length() > 50) {
	    	logger.error("Validation Failed : Name must be between 2 and 50 characters.");
	        throw new EmployeeValidationException("Name must be between 2 and 50 characters.");
	    }
	    if (employee.getAge() < 18 || employee.getAge() > 65) {
	    	logger.error("Validation Failed : Age must be between 18 and 65.");
	        throw new EmployeeValidationException("Age must be between 18 and 65.");
	    }
	    EmployeeVO employeeVO = employeeMapper.employeeDTOToEmployee(employee);
		EmployeeVO savedEmployee = employeeRepo.save(employeeVO);
		
		logger.info("Employee successfully created with ID: {}", savedEmployee.getId());
		EmployeeDTO result = employeeMapper.employeeToEmployeeDTO(savedEmployee);
		return result;
		// TODO Auto-generated method stub
	}

	@Override
	public List<EmployeeDTO> getEmployeeByName(String name) {
		// TODO Auto-generated method stub
		logger.info("Fetching employees with name: {}", name);
		List<EmployeeVO> employees = employeeRepo.findByName(name);
		if (employees.isEmpty()) {
			logger.warn("No employees found with name: {}", name);
			throw new EmployeeNotFoundException("No employees found with name: " + name);
        }
		logger.info("Successfully retrieved {} employees with name: {}", employees.size(), name);
		List<EmployeeDTO> result = new ArrayList<>();
		for( EmployeeVO emp : employees) {
			EmployeeDTO res = employeeMapper.employeeToEmployeeDTO(emp);
			result.add(res);
		}
        return result;
	}
}
