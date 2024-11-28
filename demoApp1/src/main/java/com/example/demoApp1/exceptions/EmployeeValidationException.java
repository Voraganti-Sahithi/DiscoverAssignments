package com.example.demoApp1.exceptions;

public class EmployeeValidationException extends RuntimeException{
	public EmployeeValidationException(String message) {
        super(message);
    }
}
