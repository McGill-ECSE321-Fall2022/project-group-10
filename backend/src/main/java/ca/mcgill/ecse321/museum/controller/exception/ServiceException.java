package ca.mcgill.ecse321.museum.controller.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private HttpStatus status;
	
	public ServiceException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}
}
