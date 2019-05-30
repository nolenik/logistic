package com.uit.logistic.Exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@RestController
public class CustomExceptionHandler{
	
	//Handle all exceptions
	//Оброблення всіх помилок
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllException (Exception ex,
    		WebRequest request)  {
    	return new ResponseEntity<ExceptionResponse>
    	(new ExceptionResponse(new Date(),"Exception."
    			,request.getDescription(false)),
    			HttpStatus.INTERNAL_SERVER_ERROR);
    }	
    
    //Handle exception with not valid fields in json 1 (if type of fields is not valid)
    //Оброблення помилок з неправильним полем в json 1 (якщо тип поля неправильний)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
    		WebRequest request) {
    	return new ResponseEntity<>(new ExceptionResponse(new Date(),
    			"Exception.Incorrect data was inputted."
    			,request.getDescription(false)),
    			HttpStatus.BAD_REQUEST);
    	
    }
    
    //Handle exception if
    @ExceptionHandler(ObjectNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleCarNotFoundException (ObjectNotFoundException ex,
    		WebRequest request) {
    	ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
    			ex.getMessage(),request.getDescription(false));
    	return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
    }
    
    //Handle exceptions with not valid fields in json 2 (if value of field is not valid)
    //Оброблення помилок з неправильним полем в json 2 (якщо значення неправильне)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationException (MethodArgumentNotValidException ex) {
    	Map<String,String> errors = new HashMap<>();
    	ex.getBindingResult().getAllErrors().forEach((error)->{
    		String fieldName = ((FieldError) error).getField();
    		String errorMessage = error.getDefaultMessage();
    		errors.put(fieldName, errorMessage);
    	});
    	return errors;
    }
    
    //Handle exceptions with not valid type of id in url
    //Оброблення помилок з неправильним типом id в адресі
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ExceptionResponse>
    handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
    		WebRequest request) {
    	ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
    			"Incorrect URL. ID must be integer.",request.getDescription(false));
    	return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }
}
