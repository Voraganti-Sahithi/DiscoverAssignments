package com.example.demoApp1.dto;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.JAXBContextFactory;


public class JAXBConversion {
	public static void main(String[] args) throws Exception {
        EmployeeDTO emp = new EmployeeDTO();
        emp.setId(1L);
        emp.setName("sahithi");
        emp.setAge(20);

        Map<String, Object> properties = new HashMap<>();
        properties.put("eclipselink.media-type", "application/json");
        properties.put("eclipselink.json.include-root", false);
        JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{EmployeeDTO.class}, properties);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(emp, System.out);
        
        
//        String jsonString = "{ \"id\": 1, \"name\": \"shankar\", \"age\": 30 }";
//        JAXBContext jaxbContext2 = JAXBContextFactory.createContext(new Class[]{EmployeeDTO.class}, null);
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//        StringReader reader = new StringReader(jsonString);
//        EmployeeDTO employee = (EmployeeDTO) unmarshaller.unmarshal(reader);
//
//        // Print the converted object
//        System.out.println("ID: " + employee.getId());
//        System.out.println("Name: " + employee.getName());
//        System.out.println("Age: " + employee.getAge());
    }
}
