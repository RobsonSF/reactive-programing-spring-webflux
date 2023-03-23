package dev.rbsn.springwebflux.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class UtilFieldError {
	private String fieldName;
	private  String message;

}
