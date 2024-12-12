package com.example.demoApp1.stepdef;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.example.demoApp1.vo.EmployeeVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmployeeStepDef {
	private static final String BASE_URL = "http://localhost:8888/api/employees";
    private RestTemplate restTemplate;
    private ResponseEntity<String> response;
    private EmployeeVO createdEmployee;
    private ObjectMapper objectMapper;
    private ResponseEntity<List<EmployeeVO>> responseList;

	 
    public EmployeeStepDef() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    // Scenario: Creating a new employee with valid details
    @Given("I have the employee details with name {string} and age {int}")
    public void givenEmployeeDetails(String name, int age) {
        createdEmployee = new EmployeeVO();
        createdEmployee.setName(name);
        createdEmployee.setAge(age);
    }

    @When("I create the employee")
    public void createEmployee() throws Exception {
        String url = BASE_URL + "/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(createdEmployee), headers);

        response = restTemplate.postForEntity(url, entity, String.class);
    }

    @Then("I should see the employee with name {string} and age {int}")
    public void checkCreatedEmployee(String name, int age) throws Exception {
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        EmployeeVO employeeResponse = objectMapper.readValue(response.getBody(), EmployeeVO.class);
        assertEquals(name, employeeResponse.getName());
        assertEquals(age, employeeResponse.getAge());
    }

    // Scenario: Retrieving an employee by ID
    @Given("an employee exists with ID {long} and name {string} and age {int}")
    public void givenEmployeeExists(Long id, String name, int age) throws Exception {
        // Assuming you have some logic to create an employee or mock it
        EmployeeVO employee = new EmployeeVO();
        employee.setId(id);
        employee.setName(name);
        employee.setAge(age);

        String url = BASE_URL + "/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(employee), headers);

        restTemplate.postForEntity(url, entity, String.class);
    }

    @When("I fetch the employee by ID {long}")
    public void fetchEmployeeById(Long id) throws Exception {
        String url = BASE_URL + "/byId/" + id;
        response = restTemplate.getForEntity(url, String.class);
    }

    @Then("I should get the employee with ID {long} and name {string} and age {int}")
    public void checkEmployeeDetails(Long id, String name, int age) throws Exception {
        assertEquals(HttpStatus.OK, response.getStatusCode());

        EmployeeVO employeeResponse = objectMapper.readValue(response.getBody(), EmployeeVO.class);
        assertEquals(id, employeeResponse.getId());
        assertEquals(name, employeeResponse.getName());
        assertEquals(age, employeeResponse.getAge());
    }
    
    @Given("I have some employees in the system ID <id> and name {string} and age {int}")
    public void i_have_some_employees_in_the_system_id_id_and_name_and_age(String string, Integer int1) {
    
    }
    
    @When("I fetch all employees")
    public void i_fetch_all_employees() {
        // Send GET request to the /all end point
        responseList = restTemplate.exchange(BASE_URL + "/all", 
                org.springframework.http.HttpMethod.GET, 
                null, 
                new ParameterizedTypeReference<List<EmployeeVO>>() {});
    }
    
    @Then("I should see a list of employees with ID <id> and name {string} and age {int}")
    public void i_should_see_a_list_of_employees_with_id_id_and_name_and_age(String string, Integer int1) {
    	// Check if the response is successful (status code 200)
    	assertEquals(HttpStatus.OK, responseList.getStatusCode());

        List<EmployeeVO> employees = responseList.getBody();
        boolean employeeFound = employees.stream()
            .anyMatch(emp -> emp.getName().equals(string) && emp.getAge() == int1);

        assertTrue("Employee not found in the list", employeeFound);
    }
}
