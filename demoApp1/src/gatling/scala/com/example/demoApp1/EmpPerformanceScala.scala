package com.example.demoApp1

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class EmpPerformanceScala extends Simulation {

  // Define the base URL of your API
  val baseUrl = "http://localhost:8888/api/employees"
  
  // Define HTTP protocol
  val httpProtocol = http
    .baseUrl(baseUrl) // The base URL
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  // Scenario 1: Create a new employee
  val createEmployeeScenario = scenario("Create Employee Scenario")
    .exec(http("Create Employee")
      .post("/create")
      .body(StringBody("""{
        "name": "sahithi",
        "age": 30
      }""")).asJson
      .check(status.is(201))) // Check if employee creation is successful

  // Scenario 2: Get all employees
  val getAllEmployeesScenario = scenario("Get All Employees Scenario")
    .exec(http("Get All Employees")
      .get("/all")
      .check(status.is(200))) 

  // Scenario 3: Get employee by ID
  val getEmployeeByIdScenario = scenario("Get Employee By ID Scenario")
    .exec(http("Get Employee By ID")
      .get("/byId/1") 
      .check(status.is(200)))

  // Set the simulation configuration
  setUp(
    createEmployeeScenario.inject(
      atOnceUsers(2), // 5 users will concurrently create employees
      rampUsers(10) during (10 seconds) // Ramp up to 20 users over 20 seconds for creation
    ),
    getAllEmployeesScenario.inject(
      rampUsers(10) during (10 seconds) // Ramp up to 30 users over 30 seconds for fetching all employees
    ),
    getEmployeeByIdScenario.inject(
      constantUsersPerSec(5) during (5 seconds) // 10 users per second for 1 minute for fetching employee by ID
    )
  ).protocols(httpProtocol)
}
