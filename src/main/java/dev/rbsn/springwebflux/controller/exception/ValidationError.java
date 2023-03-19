package dev.rbsn.springwebflux.controller.exception;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationError extends StandardError implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private final List<UtilFildError> errors =  new ArrayList<>();
	
	ValidationError(LocalDateTime timestamp, String path, Integer status, String error, String message) {
		super(timestamp, path, status, error, message);
	}
	
	public void addError(String fildName,  String message) {
		this.errors.add(new UtilFildError(fildName, message));
	}

}
