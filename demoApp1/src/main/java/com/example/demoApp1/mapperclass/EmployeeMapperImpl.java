package com.example.demoApp1.mapperclass;

import com.example.demoApp1.dto.EmployeeDTO;
import com.example.demoApp1.vo.EmployeeVO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-29T14:33:50+0530",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDTO employeeToEmployeeDTO(EmployeeVO employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId( employee.getId() );
        employeeDTO.setName( employee.getName() );
        employeeDTO.setAge( employee.getAge() );

        return employeeDTO;
    }

    @Override
    public EmployeeVO employeeDTOToEmployee(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        EmployeeVO employee = new EmployeeVO();

        employee.setId( employeeDTO.getId() );
        employee.setName( employeeDTO.getName() );
        employee.setAge( employeeDTO.getAge() );

        return employee;
    }
}
