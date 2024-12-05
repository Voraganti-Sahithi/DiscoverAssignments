package com.example.demoApp1.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.exceptions.EmployeeNotFoundException;
import com.example.demoApp1.exceptions.EmployeeValidationException;
import com.example.demoApp1.service.EmployeeService;
import com.example.demoApp1.serviceImpl.EmployeeServiceImpl;
import com.example.demoApp1.util.Constants;
import com.example.demoApp1.vo.EmployeeVO;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@Validated @RequestBody EmployeeDTO employeeDTO) {
    	logger.info("Creating a new employee with details: {}", employeeDTO);
        try {
            EmployeeVO createdEmployee = employeeService.createEmployee(employeeDTO);
            logger.info("Employee successfully created with ID: {}", createdEmployee.getId());
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (EmployeeValidationException e) {
        	logger.error("Validation Failed : Name must be from 2 to 50 characters and  Age must be between 18 and 65 ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
    	logger.info("Fetching all employees from the database.");
        List<EmployeeVO> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
			logger.warn("No employees found in the database.");
            throw new EmployeeNotFoundException("No employees found.");
        }
        logger.info("Successfully retrieved {} employees.", employees.size());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    } 

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
    	logger.info("Fetching employee with ID: {}", id);
        try {
            EmployeeVO employee = employeeService.getEmployeeById(id);
            logger.info("Successfully retrieved employee: {}", employee);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
        	logger.warn("Employee with ID {} not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
    
    @GetMapping("/byName/{name}")
    public ResponseEntity<?> getEmployeeByName(@PathVariable("name") String name) {
        try {
            List<EmployeeVO> employees = employeeService.getEmployeeByName(name);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheckId() {
	    try {
            // Hardcoded input value
            String input = "abc"; // Change this to test other cases, e.g., "abc"

            // Validate if the input is a valid Long
            Long employeeId;
            try {
                employeeId = Long.parseLong(input);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Health check failed: Input is not a valid numeric ID");
            }

            // Fetch employee by ID
            EmployeeVO employee = employeeService.getEmployeeById(employeeId);
	    	Optional<EmployeeVO> optionalEmployee = Optional.ofNullable(employee);
            if (optionalEmployee.isPresent()) {
                return ResponseEntity.ok("Success health check");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed health check: Employee not found");
            }
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.ok("Health check Success, but Employee not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Health check failed: An unexpected error occurred");
        }
	}
}
