package com.example.demoApp1.runner;


import io.cucumber.junit.CucumberOptions;

@CucumberOptions(
	    features = "com.example.demoApp1.features",
	    glue = "com.example.demoApp1.stepdef",
	    plugin = {"pretty", "json:target/cucumber.json"}
	)
public class CucumberTestRunner {

}
