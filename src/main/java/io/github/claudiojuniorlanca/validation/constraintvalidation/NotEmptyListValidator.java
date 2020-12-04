package io.github.claudiojuniorlanca.validation.constraintvalidation;

import io.github.claudiojuniorlanca.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {

    /*
        Classe criada criada para implementar a validação uma lista vazia
    */

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {

    }

    @Override
    public boolean isValid(List value, ConstraintValidatorContext context) {
        return !value.isEmpty() && value != null;
    }
}
