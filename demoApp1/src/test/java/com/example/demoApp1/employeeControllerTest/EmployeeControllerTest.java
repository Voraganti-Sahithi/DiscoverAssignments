package com.example.demoApp1.employeeControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demoApp1.controller.EmployeeController;
import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.exceptions.EmployeeNotFoundException;
import com.example.demoApp1.exceptions.EmployeeValidationException;
import com.example.demoApp1.service.EmployeeService;
import com.example.demoApp1.vo.EmployeeVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private EmployeeService employeeService;

	    @InjectMocks
	    private EmployeeController employeeController;

	    private EmployeeDTO employeeDTO;
	    private EmployeeVO employeeVO;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	        
	        employeeVO = new EmployeeVO();
	        employeeVO.setId(1L);
	        employeeVO.setName("sahithi");
	        employeeVO.setAge(30);
	    }

	    @Test
	    public void testCreateEmployee_Success() throws Exception {
	        Mockito.when(employeeService.createEmployee(Mockito.any(EmployeeVO.class))).thenReturn(employeeVO);

	        mockMvc.perform(MockMvcRequestBuilders
	                .post("/api/employees/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(new ObjectMapper().writeValueAsString(employeeVO)))
	                .andExpect(MockMvcResultMatchers.status().isCreated())  
	                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))  
	                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("sahithi")) 
	                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30));  
	    }
	    
	    @Test
	    public void testCreateEmployee_ValidationFailed() throws Exception {
	        Mockito.when(employeeService.createEmployee(Mockito.any(EmployeeVO.class)))
	                .thenThrow(new EmployeeValidationException("Validation Failed : Name must be from 2 to 50 characters and Age must be between 18 and 65"));
	        mockMvc.perform(MockMvcRequestBuilders
	                .post("/api/employees/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(new ObjectMapper().writeValueAsString(employeeVO)))
	                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // Assert HTTP status code 400
	                .andExpect(MockMvcResultMatchers.content().string("Validation Failed : Name must be from 2 to 50 characters and Age must be between 18 and 65"));
	    }
	    
	    @Test
	    public void testGetAllEmployees_Success() throws Exception {
	        EmployeeVO employee1 = new EmployeeVO();
	        employee1.setId(1L);
	        employee1.setName("sahithi");
	        employee1.setAge(30);
	        
	        EmployeeVO employee2 = new EmployeeVO();
	        employee2.setId(2L);
	        employee2.setName("shankar");
	        employee2.setAge(35);
	        
	        List<EmployeeVO> employees = Arrays.asList(employee1, employee2);
	        when(employeeService.getAllEmployees()).thenReturn(employees);
	        mockMvc.perform(get("/api/employees/all")
	                        .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.length()").value(2)) 
	                .andExpect(jsonPath("$[0].id").value(1))
	                .andExpect(jsonPath("$[0].name").value("sahithi"))
	                .andExpect(jsonPath("$[0].age").value(30))
	                .andExpect(jsonPath("$[1].id").value(2))
	                .andExpect(jsonPath("$[1].name").value("shankar"))
	                .andExpect(jsonPath("$[1].age").value(35));
	        verify(employeeService, times(1)).getAllEmployees();
	    }

	    @Test
	    public void testGetAllEmployees_EmptyList() throws Exception {
	        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList());
	        mockMvc.perform(get("/api/employees/all")
	                        .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNotFound())
	                .andExpect(content().string("No employees found."));

	        verify(employeeService, times(1)).getAllEmployees();
	    }
	    
	    @Test
	    public void testGetEmployeeById_Success() throws Exception {
	        // Given
	        Long employeeId = 1L;
	        EmployeeVO employee = new EmployeeVO();
	        employee.setId(employeeId);
	        employee.setName("sahithi");
	        employee.setAge(30);

	        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

	        mockMvc.perform(get("/api/employees/byId/{id}", employeeId)
	                        .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.id").value(employeeId))
	                .andExpect(jsonPath("$.name").value("sahithi"))
	                .andExpect(jsonPath("$.age").value(30));

	        verify(employeeService, times(1)).getEmployeeById(employeeId);
	    }

	    @Test
	    public void testGetEmployeeById_NotFound() throws Exception {

	    	Long employeeId = 1L;

	        when(employeeService.getEmployeeById(employeeId)).thenThrow(new EmployeeNotFoundException("Employee with ID " + employeeId + " not found."));

	        mockMvc.perform(get("/api/employees/byId/{id}", employeeId)
	                    .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNotFound()) 
	                .andExpect(content().string("Employee with ID 1 not found.")); 

	        verify(employeeService, times(1)).getEmployeeById(employeeId);
	    }
	    
	    @Test
	    public void testHealthCheck_InvalidEmployeeId() throws Exception {
	    	
	    	String invalidInput = "abc";  
	        
	    	 
	        mockMvc.perform(get("/api/employees/healthCheck/{value}", invalidInput))
	                .andDo(print())
	                .andExpect(status().isInternalServerError())
	                .andExpect(content().string("Health check failed: Input is not a valid numeric ID"));
	    }
	    
	    @Test
	    public void testHealthCheckUserFound() throws Exception {
	        // Mock the service to return a user with ID 1
	    	when(employeeService.getEmployeeById(1L)).thenReturn(employeeVO);
	    	String invalidInput = "1";  
	        mockMvc.perform(get("/api/employees/healthCheck/{value}",invalidInput))
	                .andDo(print())
	                .andExpect(status().isOk()) 
	                .andExpect(content().string("Success health check"));
	    }
	    

}
