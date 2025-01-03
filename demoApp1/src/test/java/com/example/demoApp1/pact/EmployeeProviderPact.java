package com.example.demoApp1.pact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.server.LocalServerPort;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.example.demoApp1.vo.EmployeeVO;

@Provider("ProviderPact") // Name of the provider
@PactFolder("C:\\Users\\vosahith\\git\\Demo_app2\\demoApp2\\target\\pacts")
public class EmployeeProviderPact {

	@LocalServerPort
    private int port;
 
    public HttpTestTarget target ;
 
    @BeforeEach
    void setUp(PactVerificationContext context) {
    	target = new HttpTestTarget("localhost", 8888 , "/"); // Dynamically set the port from the Spring Boot test
        context.setTarget(target);
    }
     @State("Employee with ID 1 exists")
      public void employeeWithId1Exists() {
    	EmployeeVO empVO = new EmployeeVO();
    	empVO.setId(1L);
    	empVO.setName("sahithi");
    	empVO.setAge(30);
   }
 
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPacts(PactVerificationContext context) {
        context.verifyInteraction(); // Verifies interactions defined in the Pact file
    }
    
}
