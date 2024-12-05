package com.example.demoApp1.mapperclass;

import org.mapstruct.Mapper;

import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.vo.EmployeeVO;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	    EmployeeDTO employeeToEmployeeDTO(EmployeeVO employee);
	    EmployeeVO employeeDTOToEmployee(EmployeeDTO employeeDTO);
}

