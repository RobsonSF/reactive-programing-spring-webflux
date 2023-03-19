package dev.rbsn.springwebflux.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRequest (
		
		@Size(min =  3, max =50, message = "must be betwen {min} and {max} characteres")
		@NotEmpty(message = "must not be null or empty")
		String name,
		
		@Email(message = "invalid e-mail")
		@NotEmpty(message = "must not be null or empty")
		String email,
		
		@Size(min =  6, max =10, message = "must be betwen {min} and {max} characteres")
		@NotEmpty(message = "must not be null or empty")
		String password
) {}
