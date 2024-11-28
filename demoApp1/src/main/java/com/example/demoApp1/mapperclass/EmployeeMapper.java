package com.example.demoApp1.mapperclass;

import org.mapstruct.Mapper;

import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.vo.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	    EmployeeDTO employeeToEmployeeDTO(Employee employee);
	    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
