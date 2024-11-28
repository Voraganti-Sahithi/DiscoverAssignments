package com.example.demoApp1.bo;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoApp1.dao.EmployeeRepository;
import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.exceptions.EmployeeNotFoundException;
import com.example.demoApp1.exceptions.EmployeeValidationException;
import com.example.demoApp1.mapperclass.EmployeeMapper;
import com.example.demoApp1.serviceImpl.EmployeeServiceImpl;
import com.example.demoApp1.vo.Employee;

@Service
public class EmployeeBOImpl implements EmployeeBO{
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private EmployeeMapper employeeMapper; 
	

	@Override
	public List<EmployeeDTO> getAllEmployees(){
		logger.info("Fetching all employees from the database.");
		List<Employee> employees = employeeRepo.findAll();
		if (employees.isEmpty()) {
			logger.warn("No employees found in the database.");
            throw new EmployeeNotFoundException("No employees found.");
        }
		logger.info("Successfully retrieved {} employees.", employees.size());
		return employees.stream()
				.map(employeeMapper::employeeToEmployeeDTO)
				.collect(Collectors.toList());
	}

	public EmployeeDTO getEmployeeById(Long id) {
		logger.info("Fetching employee with ID: {}", id);
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> {
					logger.warn("Employee with ID {} not found.", id);
					return new EmployeeNotFoundException("Employee with ID " + id + " not found.");
				});
		 logger.info("Successfully retrieved employee: {}", employee);
		return employeeMapper.employeeToEmployeeDTO(employee);
	}

	@Override
	public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
		
		logger.info("Creating a new employee with details: {}", employeeDTO);
		
		if (employeeDTO.getName() == null || employeeDTO.getName().trim().isEmpty()) {
			logger.error("Validation Failed : Name is mandatory and cannot be blank.");
	        throw new EmployeeValidationException("Name is mandatory and cannot be blank.");
	    }
	    if (employeeDTO.getName().length() < 2 || employeeDTO.getName().length() > 50) {
	    	logger.error("Validation Failed : Name must be between 2 and 50 characters.");
	        throw new EmployeeValidationException("Name must be between 2 and 50 characters.");
	    }

	    // Validate Age
	    if (employeeDTO.getAge() < 18 || employeeDTO.getAge() > 65) {
	    	logger.error("Validation Failed : Age must be between 18 and 65.");
	        throw new EmployeeValidationException("Age must be between 18 and 65.");
	    }
		
		Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
		Employee savedEmployee = employeeRepo.save(employee);
		
		logger.info("Employee successfully created with ID: {}", savedEmployee.getId());
		return employeeMapper.employeeToEmployeeDTO(savedEmployee);
		// TODO Auto-generated method stub
	}

	@Override
	public List<EmployeeDTO> getEmployeeByName(String name) {
		// TODO Auto-generated method stub
		logger.info("Fetching employees with name: {}", name);
		List<Employee> employees = employeeRepo.findByName(name);
		if (employees.isEmpty()) {
			logger.warn("No employees found with name: {}", name);
			throw new EmployeeNotFoundException("No employees found with name: " + name);
        }
		logger.info("Successfully retrieved {} employees with name: {}", employees.size(), name);
        return employees.stream()
                        .map(employeeMapper::employeeToEmployeeDTO)
                        .collect(Collectors.toList());
	}
}
