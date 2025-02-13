Feature: Employee Management System

	Scenario: Creating a new employee with valid details
    Given I have the employee details with name "<name>" and age <age>
    When I create the employee
    Then I should see the employee with name "<name>" and age <age>

    Examples:
      | name        | age |
      | sahithi     | 30  |
      | srilatha     | 40  |
	  | shankar   	| 50  |
	
 	Scenario: Retrieving an employee by ID
    Given an employee exists with ID <id> and name "<name>" and age <age>
    When I fetch the employee by ID <id>
    Then I should get the employee with ID <id> and name "<name>" and age <age>

    Examples:
      | id  | name       | age |
      | 1   | sahithi    | 30  |
      | 2   | srilatha    | 40  |
      | 3   | shankar   | 50  |

	Scenario: Retrieve all employees
	Given I have some employees in the system ID <id> and name "<name>" and age <age>
	When I fetch all employees
	Then I should see a list of employees with ID <id> and name "<name>" and age <age>
	Examples:
      | name        | age |
      | sahithi     | 30  |
      | srilatha     | 40  |
	  | shankar   	| 50  |
	  
	  