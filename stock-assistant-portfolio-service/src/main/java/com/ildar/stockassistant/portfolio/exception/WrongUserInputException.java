package com.ildar.stockassistant.portfolio.exception;

public class WrongUserInputException extends BusinessException {
    public WrongUserInputException(String message) {
        super(message);
    }
}
