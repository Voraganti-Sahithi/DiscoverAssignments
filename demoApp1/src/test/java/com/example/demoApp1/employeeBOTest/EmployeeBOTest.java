package com.example.demoApp1.employeeBOTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.demoApp1.bo.EmployeeBOImpl;
import com.example.demoApp1.dao.EmployeeRepository;
import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.exceptions.EmployeeNotFoundException;
import com.example.demoApp1.exceptions.EmployeeValidationException;
import com.example.demoApp1.mapperclass.EmployeeMapper;
import com.example.demoApp1.vo.EmployeeVO;

public class EmployeeBOTest {

	
	@Mock
    private EmployeeRepository employeeRepo; 

    @Mock
    private EmployeeMapper employeeMapper; 

    @InjectMocks
    private EmployeeBOImpl employeeBO;

    private EmployeeVO employeeVO; 
    private EmployeeDTO employeeDTO; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock EmployeeVO and EmployeeDTO for tests
        employeeVO = new EmployeeVO();
        employeeVO.setId(1L);
        employeeVO.setName("sahithi");
        employeeVO.setAge(30);
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("sahithi");
        employeeDTO.setAge(30);
    }

    @Test
    void testGetAllEmployees_Success() {
  
        List<EmployeeVO> employeeList = List.of(employeeVO);
        when(employeeRepo.findAll()).thenReturn(employeeList);
        when(employeeMapper.employeeToEmployeeDTO(employeeVO)).thenReturn(employeeDTO);

        List<EmployeeDTO> result = employeeBO.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeDTO, result.get(0));

        verify(employeeRepo, times(1)).findAll();
        verify(employeeMapper, times(1)).employeeToEmployeeDTO(employeeVO);
    }


    @Test
    void testGetAllEmployees_NoEmployees() {

        when(employeeRepo.findAll()).thenReturn(List.of());

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeBO.getAllEmployees();
        });
        assertEquals("No employees found.", exception.getMessage());

        verify(employeeRepo, times(1)).findAll();
    }
    @Test
    void testGetEmployeeById_Success() {

        Long employeeId = 1L;
        when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employeeVO));
        when(employeeMapper.employeeToEmployeeDTO(employeeVO)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeBO.getEmployeeById(employeeId);

        assertNotNull(result);
        assertEquals(employeeDTO, result);

        verify(employeeRepo, times(1)).findById(employeeId);
        verify(employeeMapper, times(1)).employeeToEmployeeDTO(employeeVO);
    }
    @Test
    void testGetEmployeeById_NotFound() {

        Long employeeId = 1L;
        when(employeeRepo.findById(employeeId)).thenReturn(Optional.empty());

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeBO.getEmployeeById(employeeId);
        });
        assertEquals("Employee with ID " + employeeId + " not found.", exception.getMessage());

        verify(employeeRepo, times(1)).findById(employeeId);
    }

    @Test
    void testCreateEmployee_Success() {
        when(employeeMapper.employeeDTOToEmployee(employeeDTO)).thenReturn(employeeVO);
        when(employeeRepo.save(employeeVO)).thenReturn(employeeVO);
        when(employeeMapper.employeeToEmployeeDTO(employeeVO)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeBO.createEmployee(employeeDTO);

        assertNotNull(result);
        assertEquals(employeeDTO, result);

        verify(employeeMapper, times(1)).employeeDTOToEmployee(employeeDTO);
        verify(employeeRepo, times(1)).save(employeeVO);
    }

    @Test
    void testCreateEmployee_InvalidName() {
  
    	 EmployeeDTO invalidEmployeeDTO = new EmployeeDTO();
         invalidEmployeeDTO.setId(1L);
         invalidEmployeeDTO.setName("");
        invalidEmployeeDTO.setAge(30);
    	when(employeeMapper.employeeDTOToEmployee(invalidEmployeeDTO)).thenReturn(employeeVO);

        EmployeeValidationException exception = assertThrows(EmployeeValidationException.class, () -> {
            employeeBO.createEmployee(invalidEmployeeDTO);
        });
        
        assertEquals("Name is mandatory and cannot be blank.", exception.getMessage());
    }
    @Test
    void testCreateEmployee_InvalidAge() {

    	 EmployeeDTO invalidEmployeeDTO = new EmployeeDTO();
         invalidEmployeeDTO.setId(1L);
         invalidEmployeeDTO.setName("sahithi");
        invalidEmployeeDTO.setAge(17); // Invalid age

        EmployeeValidationException exception = assertThrows(EmployeeValidationException.class, () -> {
            employeeBO.createEmployee(invalidEmployeeDTO);
        });
        assertEquals("Age must be between 18 and 65.", exception.getMessage());
    }
}
