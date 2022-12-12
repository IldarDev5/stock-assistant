package com.ildar.stockassistant.portfolio.controller;

import com.ildar.stockassistant.portfolio.dto.error.ErrorResponse;
import com.ildar.stockassistant.portfolio.dto.error.ValidationErrorResponse;
import com.ildar.stockassistant.portfolio.exception.BusinessException;
import com.ildar.stockassistant.portfolio.exception.WrongUserInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandlerController {

    private static final String VALIDATION_ERRORS = "Validation error(s) for incoming request body.";

    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException exc) {
        log.error("Error during database operation.", exc);
        return ResponseEntity.internalServerError().body(ErrorResponse.internalError());
    }

    @ExceptionHandler({WrongUserInputException.class})
    public ResponseEntity<ErrorResponse> handleWrongUserInputException(WrongUserInputException exc) {
        log.debug("Application-level error during request execution.", exc);
        return ResponseEntity.badRequest().body(ErrorResponse.withMessage(exc.getMessage()));
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exc) {
        log.error("Application-level error during application logic execution.", exc);
        return ResponseEntity.internalServerError().body(ErrorResponse.withMessage(exc.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exc) {
        log.debug(VALIDATION_ERRORS, exc);
        return ResponseEntity.badRequest()
                .body(ValidationErrorResponse.of(VALIDATION_ERRORS, exc.getBindingResult().getFieldErrors()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exc) {
        log.error("Broad exception during application execution.", exc);
        return ResponseEntity.internalServerError().body(ErrorResponse.internalError());
    }
}
