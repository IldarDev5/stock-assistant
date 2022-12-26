package com.ildar.stockassistant.portfolio.exception;

public class StockDoesNotExistException extends WrongUserInputException {

    private static final String ERROR_MESSAGE = "Stock with ID %s doesn't exist.";

    public StockDoesNotExistException(Long id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}
