package dev.rbsn.springwebflux.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {TrimStringValidator.class})
public @interface TrimString {

	String message() default "field cannot have blank spaces at the beginning or at the end";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
