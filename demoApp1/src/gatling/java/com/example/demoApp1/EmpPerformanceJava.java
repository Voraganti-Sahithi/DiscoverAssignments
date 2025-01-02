package com.example.demoApp1;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
public class EmpPerformanceJava extends Simulation {

    private static final String BASE_URL = "http://localhost:8888"; // Adjust to your server's base URL

    // Define HTTP protocol configuration
    HttpProtocolBuilder httpProtocol = http.baseUrl(BASE_URL) // Base URL of the app
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Scenario for creating an employee
    ScenarioBuilder createEmployeeScenario = scenario("Create Employee")
                .exec(http("Create Employee Request")
                        .post("/api/employees/create")
                        .body(StringBody("{ \"name\": \"sahithi\", \"age\": 30 }")).asJson()
                        .check(status().is(201))
                );

    // Scenario for fetching all employees
	ScenarioBuilder getAllEmployeesScenario = scenario("Get All Employees")
                .exec(http("Get All Employees Request")
                        .get("/api/employees/all")
                        .check(status().is(200)) 
                );
    
    
    
   
    
  

    // Scenario for fetching an employee by ID
	ScenarioBuilder getEmployeeByIdScenario = scenario("Get Employee By ID")
                .exec(http("Get Employee By ID Request")
                        .get("/api/employees/byId/1")
                        .check(status().is(200)) 
                );
	
	
	{
        setUp(
            // Run both scenarios with different users
        		createEmployeeScenario.injectOpen(rampUsers(50).during(30)),
        		getAllEmployeesScenario.injectOpen(rampUsers(50).during(30)),
        		getEmployeeByIdScenario.injectOpen((rampUsers(50)).during(30))
        ).protocols(httpProtocol);
    }
}