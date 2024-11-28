package com.example.demoApp1.mapperclass;

import org.springframework.stereotype.Component;

import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.vo.Employee;

@Component
public class EmployeeMapperImpl implements EmployeeMapper{

	@Override
	public EmployeeDTO employeeToEmployeeDTO(Employee employee) {
		// TODO Auto-generated method stub
		if (employee == null) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setAge(employee.getAge());

        return employeeDTO;
	}

	@Override
	public Employee employeeDTOToEmployee(EmployeeDTO employeeDTO) {
		// TODO Auto-generated method stub
		if (employeeDTO == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setAge(employeeDTO.getAge());

        return employee;
	}

}
