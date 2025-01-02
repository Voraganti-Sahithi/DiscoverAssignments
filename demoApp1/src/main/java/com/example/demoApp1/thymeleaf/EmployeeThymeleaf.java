package com.example.demoApp1.thymeleaf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demoApp1.exceptions.EmployeeNotFoundException;
import com.example.demoApp1.exceptions.EmployeeValidationException;
import com.example.demoApp1.service.EmployeeService;
import com.example.demoApp1.vo.EmployeeVO;

@Controller
@RequestMapping("/api/employees")
public class EmployeeThymeleaf {

	@Autowired
	EmployeeService empService;
	
	public EmployeeThymeleaf() {
	}

	public EmployeeThymeleaf(EmployeeService empService) {
		super();
		this.empService = empService;
	}
	
	@GetMapping("/Home")
	public String showHomePage() {
		return "HomeEmp";
	}
	
	@GetMapping("/allEmployee")
	public String getAll(Model model) throws EmployeeNotFoundException{
		List<EmployeeVO> employees = empService.getAllEmployees();
		model.addAttribute("AllEmployees", employees);
		model.addAttribute("employees", employees);
		return "allEmployees";
	}
	
	@PostMapping("/createE")
	public String CreateEmp(@ModelAttribute("employees") EmployeeVO employees) {
		if(employees.getName() == null || employees.getName().isEmpty()) {
			return "Employee name cannot be null";
		}
		
		empService.createEmployee(employees);
		return "empSuccess";
	}
	
	@GetMapping("/createEmployee")
	public String showCreateEmp(Model model) throws EmployeeValidationException{
		
		model.addAttribute("employees", new EmployeeVO());
		return "createEmp";
	}
	
	
	@GetMapping("/employeeByIdForm")
    public String showEmployeeIdForm() {
        return "empIdForm";
    }

    // Method to handle form submission, retrieve employee by ID, and show details
    @GetMapping("/getEmployeeById")
    public String getEmployeeById(@RequestParam("id") Long id, Model model) {
        try {
            EmployeeVO employees = empService.getEmployeeById(id);
            if (employees == null) {
                model.addAttribute("error", "Employee not found");
                return "empById";
            }
            model.addAttribute("employees", employees);
            return "empById";  
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "empById";
        }
    }
    
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // This is the Thymeleaf template name for login page
    }

    // Handle login form submission
    @GetMapping("/details")
    public String handleLogin(@RequestParam("id") Long id, @RequestParam("name") String name, RedirectAttributes redirectAttributes) {
        // Fetch employee based on ID and name
        EmployeeVO employee = empService.getEmployeeByIdAndName(id, name);

        if (employee != null) {
            // If employee found, pass employee data to the redirectAttributes
            redirectAttributes.addAttribute("id", employee.getId());
            redirectAttributes.addAttribute("name", employee.getName());
            redirectAttributes.addAttribute("age", employee.getAge());
            return "redirect:/api/employees/employeeDetails";  // Redirect to the employee details page
        } else {
            // If employee not found, show error message
            redirectAttributes.addFlashAttribute("error", "Invalid credentials or employee not found");
            return "redirect:/api/employees/login";  // Redirect back to login page
        }
    }

    // Show employee details page after successful login
    @GetMapping("/employeeDetails")
    public String showEmployeeDetails(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("age") Integer age, Model model) {
        // Add employee data to the model to be displayed on the employee details page
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "empDetails";  // Return the employee details page template
    }
}
 
