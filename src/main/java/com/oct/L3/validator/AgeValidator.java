package com.oct.L3.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class AgeValidator implements ConstraintValidator<AgeConstraint, Date> {

    private int minAge;
    private int maxAge;

    @Override
    public void initialize(AgeConstraint age) {
        this.minAge = age.min();
        this.maxAge = age.max();
    }

    @Override
    public boolean isValid(Date dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return false;
        }
        LocalDate birthDate = new java.sql.Date(dateOfBirth.getTime()).toLocalDate();
        LocalDate today = LocalDate.now();
        if (birthDate.isAfter(today)) {
            return false;
        }
        int age = Period.between(birthDate, today).getYears();
        return age >= minAge && age <= maxAge;
    }
}