package io.github.claudiojuniorlanca.validation;

import io.github.claudiojuniorlanca.validation.constraintvalidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    /*
    Annotation criada pra validar uma lista vazia
     */

    String message() default "A lista não pode ser vazia";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
