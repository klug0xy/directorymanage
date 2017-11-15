package fr.amu.directorymanage.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

@Email(message="Please provide a valid email address")
@Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
@Target( { ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ExtendedEmailValidator {
    String message() default "Please provide a valid email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}