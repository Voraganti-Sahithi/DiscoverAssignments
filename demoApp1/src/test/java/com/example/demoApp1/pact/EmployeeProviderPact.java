package com.example.demoApp1.pact;

import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ExtendWith(PactProviderTestExt.class) // Extension for running provider tests with Pact
@PactProvider("ProviderPact") 
public class EmployeeProviderPact {

	 @RestController
	    @RequestMapping("/api/employees")
	    public static class EmployeeController {
	        @GetMapping("/byId/1")
	        public ResponseEntity<Map<String, Object>> getEmployee() {
	            return ResponseEntity.ok(Map.of("id", 1, "name", "sahithi", "age", 30));
	        }
	    }

	    @PactVerificationResult
	    @Test
	    void verifyPacts() {
	        // Using PactVerifier to verify the contract against the provider
	        PactVerifier pactVerifier = new PactVerifier();
	        pactVerifier.setProvider("ProviderPact");

	        // Check the contract against the provider
	        pactVerifier.verify();
	    }
}
