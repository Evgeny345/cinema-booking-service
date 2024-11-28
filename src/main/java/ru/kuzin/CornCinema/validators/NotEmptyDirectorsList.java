package ru.kuzin.CornCinema.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyDirectorsListValidator.class)
@Documented
public @interface NotEmptyDirectorsList {
	
	String message() default "Please select director";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};

}
