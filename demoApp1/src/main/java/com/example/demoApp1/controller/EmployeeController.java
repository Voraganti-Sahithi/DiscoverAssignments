package com.example.demoApp1.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.example.demoApp1.vo.EmployeeVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	 
	@Autowired
    private EmployeeService employeeService;

	
	@Operation(summary = "Create a new employee", description = "Creates a new employee with the provided details.",
	        responses = {
	            @ApiResponse(responseCode = "201", description = "Employee successfully created"),
	            @ApiResponse(responseCode = "400", description = "Validation Failed: Name must be from 2 to 50 characters and Age must be between 18 and 65"),
	            @ApiResponse(responseCode = "500", description = "Unexpected error")
	        })
	@PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeVO employeeVO){
    	logger.info("Creating a new employee with details: {}", employeeVO);
        try {
            EmployeeVO createdEmployee = employeeService.createEmployee(employeeVO);
            logger.info("Employee successfully created with ID: {}", createdEmployee.getId());
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (EmployeeValidationException e) {
        	logger.error("Validation Failed : Name must be from 2 to 50 characters and  Age must be between 18 and 65 ");
            return new ResponseEntity<>("Validation Failed : Name must be from 2 to 50 characters and Age must be between 18 and 65", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }


	@Operation(summary = "Get all employees", description = "Fetches all employees from the database.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "Successfully retrieved employees"),
	            @ApiResponse(responseCode = "404", description = "No employees found"),
	            @ApiResponse(responseCode = "500", description = "Unexpected error")
	        })
	@GetMapping("/all")
    public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
    	logger.info("Fetching all employees from the database.");
        List<EmployeeVO> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
			logger.warn("No employees found in the database.");
            throw new EmployeeNotFoundException("No employees found.");
//			return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        logger.info("Successfully retrieved {} employees.", employees.size());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    } 
	
	@Operation(summary = "Get employee by ID", description = "Fetches an employee by their ID.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "Successfully retrieved employee"),
	            @ApiResponse(responseCode = "404", description = "Employee not found"),
	            @ApiResponse(responseCode = "500", description = "Unexpected error")
	        })
	@GetMapping("/byId/{id}")
    public ResponseEntity<?> getEmployeeById(@Parameter(
            description  = "Unique Identity of the Employee",
            required = true) @PathVariable("id") Long id) {
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
    
	@Operation(summary = "Get employee by name", description = "Fetches employees by their name.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "Successfully retrieved employees"),
	            @ApiResponse(responseCode = "404", description = "Employee not found"),
	            @ApiResponse(responseCode = "500", description = "Unexpected error")
	        })
    @GetMapping("/byName/{name}")
    public ResponseEntity<?> getEmployeeByName(@Parameter(
            description  = "Name of the Employee",
            required = true) @PathVariable("name") String name) {
        try {
            List<EmployeeVO> employees = employeeService.getEmployeeByName(name);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
    
	@Operation(summary = "Health check by ID", description = "Performs a health check by employee ID.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "Success health check"),
	            @ApiResponse(responseCode = "500", description = "Health check failed")
	        })
    @GetMapping("/healthCheck/{value}")
    public ResponseEntity<String> healthCheckId(@PathVariable("value") String value) {
	    try {
            //String input = "abc"; // Change this to test other cases, e.g., "abc"
            Long employeeId;
            try {
                employeeId = Long.parseLong(value);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Health check failed: Input is not a valid numeric ID");
            }
            EmployeeVO employee = employeeService.getEmployeeById(employeeId);
	    	Optional<EmployeeVO> optionalEmployee = Optional.ofNullable(employee);
            if (optionalEmployee.isPresent()) {
                return ResponseEntity.ok("Success health check");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed health check: Employee not found");
            }
        } catch (EmployeeNotFoundException e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Health check success: Employee not found");
//            return ResponseEntity.ok("Health check Success, but Employee not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Health check failed: An unexpected error occurred");
        }
	}
}

