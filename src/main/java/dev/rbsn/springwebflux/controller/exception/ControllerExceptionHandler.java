package dev.rbsn.springwebflux.controller.exception;

import static  org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDateTime;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(DuplicateKeyException.class)
	ResponseEntity<Mono<StandardError>> duplicateKeyException(
			DuplicateKeyException err, ServerHttpRequest request
			){
		return ResponseEntity.badRequest()
				.body(Mono.just(
						StandardError.builder()
							.timestamp(LocalDateTime.now())
							.status(BAD_REQUEST.value())
							.error(BAD_REQUEST.getReasonPhrase())
							.message(verifyMessageException(err.getMessage()))
							.path(request.getPath().toString())
							.build()
				));
	}
	
	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<Mono<ValidationError>> validationError (
			WebExchangeBindException err, ServerHttpRequest request
			) {
		ValidationError validationError = new ValidationError(
				LocalDateTime.now(),
				request.getPath().toString(),
				BAD_REQUEST.value(),
				"Validation error",
				"Error on validation attributes");
		
		for(FieldError x : err.getBindingResult().getFieldErrors()) {
			validationError.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(BAD_REQUEST).body(Mono.just(validationError));
		
	}
	
	private String verifyMessageException(String message) {
		if(message.contains("email dup key")) {
			return "Email already registered";
		}
		return "email duplicate key";
	}
}
