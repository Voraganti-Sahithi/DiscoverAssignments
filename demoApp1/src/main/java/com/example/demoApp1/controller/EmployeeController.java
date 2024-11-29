package com.example.demoApp1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.example.demoApp1.util.Constants;
import com.example.demoApp1.vo.Employee;

@RestController
@RequestMapping(Constants.EMPLOYEE_API)
public class EmployeeController {
	@Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@Validated @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (EmployeeValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
        try {
            EmployeeDTO employee = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
    
    @GetMapping("/byName/{name}")
    public ResponseEntity<?> getEmployeeByName(@PathVariable("name") String name) {
        try {
            List<EmployeeDTO> employees = employeeService.getEmployeeByName(name);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
    @GetMapping("/healthCheckById")
    public ResponseEntity<String> healthCheckId() {
	    try {
	    	EmployeeDTO employee = employeeService.getEmployeeById(100L);
	    	Optional<EmployeeDTO> optionalEmployee = Optional.ofNullable(employee); // Example: Check for user with ID 1
	        if (optionalEmployee.isPresent()) {
	            return ResponseEntity.ok("Success health check");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed health check");
	        }
	    } catch (EmployeeNotFoundException e) {
	    	 return ResponseEntity.ok("Success health check");
	    }
	}
    @GetMapping("/healthCheck")
    public ResponseEntity<?> healthCheck(){
    	 
        try {
            // Try to fetch some data from the H2 database as a simple check
            long empCount = employeeService.getAllEmployees().size();
            return new ResponseEntity<>("Backend is healthy. Total employees: " + empCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Backend is not healthy", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
