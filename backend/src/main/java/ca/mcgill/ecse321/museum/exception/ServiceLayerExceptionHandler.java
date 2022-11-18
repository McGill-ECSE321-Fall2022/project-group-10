/* (C)2022 */
package ca.mcgill.ecse321.museum.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ServiceLayerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceLayerException.class)
    public ResponseEntity<String> handleEventRegistrationException(ServiceLayerException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getStatus());
    }
}
