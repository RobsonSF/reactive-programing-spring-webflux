package dev.rbsn.springwebflux.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class UtilFildError {
	private String fildName;
	private  String message;

}
