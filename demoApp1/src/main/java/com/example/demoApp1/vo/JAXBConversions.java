package com.example.demoApp1.vo;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JAXBConversions {

	public String convertPojoToJson(EmployeeVO employeeVO) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(employeeVO);
    }
	
	public EmployeeVO convertJsonToPojo(String json) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, EmployeeVO.class);
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		EmployeeVO employee = new EmployeeVO();
		employee.setId(1L);
		employee.setName("sahithi");
		employee.setAge(20);
		
		JAXBConversions obj = new JAXBConversions();
		String json = obj.convertPojoToJson(employee);
		System.out.println("Converting POJO to JSON : " + json);
		
		EmployeeVO pojo = obj.convertJsonToPojo(json);
		System.out.println("Converting JSON to POJO : " + pojo);

	}

}
