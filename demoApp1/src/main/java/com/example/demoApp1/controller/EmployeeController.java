package com.example.demoApp1.controller;

import java.util.List;
import java.util.Optional;

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
import com.example.demoApp1.util.Constants;
import com.example.demoApp1.vo.EmployeeVO;

@RestController
@RequestMapping(Constants.EMPLOYEE_API)
public class EmployeeController {
	
	@Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@Validated @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeVO createdEmployee = employeeService.createEmployee(employeeDTO);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (EmployeeValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
        List<EmployeeVO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    } 

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
        try {
            EmployeeVO employee = employeeService.getEmployeeById(id);
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
