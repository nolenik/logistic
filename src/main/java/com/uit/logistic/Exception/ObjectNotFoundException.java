package com.uit.logistic.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



//Custom exception if record not found (Помилка для того якщо поле не знайдене)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
    
	
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String exception) {
    	super(exception);
    }
}
