package com.ildar.stockassistant.portfolio.dto.error;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public record ValidationErrorResponse(String message, List<ValidationErrorField> validationErrorFields) {
    public static ValidationErrorResponse of(String validationErrors, List<FieldError> allErrors) {
        return new ValidationErrorResponse(validationErrors, allErrors.stream()
                .map(error -> new ValidationErrorField(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList()));
    }
}