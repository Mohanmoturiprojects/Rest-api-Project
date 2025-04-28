package example.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExeptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>>handleValidationExceptoins(MethodArgumentNotValidException exception){
		Map<String, String>errormap=new HashMap<>();
		exception.getBindingResult()
		         .getAllErrors()
		         .forEach(error->{
		        	 String fieldName=((FieldError) error).getField();
		        	 String errorMessage=error.getDefaultMessage();
		        	 errormap.put(fieldName, errorMessage);
		         });
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				              .header("info", "handle the exception")
				              .body(errormap);
	}

}
