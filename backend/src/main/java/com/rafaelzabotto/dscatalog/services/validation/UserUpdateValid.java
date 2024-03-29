package com.rafaelzabotto.dscatalog.services.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Anotação criada por nós do bootcamp, não é original do SpringBoot

@Constraint(validatedBy = UserUpdateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface UserUpdateValid {
    String message() default "Validation Error";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
