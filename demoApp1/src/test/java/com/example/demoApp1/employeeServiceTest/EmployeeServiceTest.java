package com.example.demoApp1.employeeServiceTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demoApp1.bo.EmployeeBO;
import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.mapperclass.EmployeeMapper;
import com.example.demoApp1.serviceImpl.EmployeeServiceImpl;
import com.example.demoApp1.vo.EmployeeVO;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
	@Mock
    private EmployeeBO employeeBO; 

    @Mock
    private EmployeeMapper employeeMapper; 

    @InjectMocks
    private EmployeeServiceImpl employeeService; 

    private EmployeeVO employeeVO; 
    private EmployeeDTO employeeDTO; 
    private List<EmployeeDTO> employeeDTOList; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 
        employeeVO = new EmployeeVO();
        	employeeVO.setId(1L);
        employeeVO.setName("sahithi");
        employeeVO.setAge(30);


        employeeDTO = new EmployeeDTO();
        	employeeDTO.setId(1L);
        employeeDTO.setName("sahithi");
        employeeDTO.setAge(30);

        employeeDTOList = new ArrayList<>();
        employeeDTOList.add(employeeDTO);
    }

    @Test
    void testCreateEmployee() {
 
        when(employeeMapper.employeeToEmployeeDTO(employeeVO)).thenReturn(employeeDTO);
        when(employeeBO.createEmployee(employeeDTO)).thenReturn(employeeDTO);
        when(employeeMapper.employeeDTOToEmployee(employeeDTO)).thenReturn(employeeVO);

        EmployeeVO result = employeeService.createEmployee(employeeVO);

        assertNotNull(result);
        assertEquals(employeeVO.getId(), result.getId());
        assertEquals(employeeVO.getName(), result.getName());
        assertEquals(employeeVO.getAge(), result.getAge());

    }

    @Test
    void testGetAllEmployees() {

        when(employeeBO.getAllEmployees()).thenReturn(employeeDTOList);
        when(employeeMapper.employeeDTOToEmployee(employeeDTO)).thenReturn(employeeVO);

        List<EmployeeVO> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeVO.getId(), result.get(0).getId());
        assertEquals(employeeVO.getName(), result.get(0).getName());

    }

    @Test
    void testGetEmployeeById() {
 
        when(employeeBO.getEmployeeById(1L)).thenReturn(employeeDTO);
        when(employeeMapper.employeeDTOToEmployee(employeeDTO)).thenReturn(employeeVO);

        EmployeeVO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(employeeVO.getId(), result.getId());
        assertEquals(employeeVO.getName(), result.getName());

    }
}

