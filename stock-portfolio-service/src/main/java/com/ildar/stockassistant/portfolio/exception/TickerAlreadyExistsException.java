package com.ildar.stockassistant.portfolio.exception;

import com.ildar.stockassistant.portfolio.dto.StockDTO;

public class TickerAlreadyExistsException extends WrongUserInputException {

    private static final String ERROR_MESSAGE = "Ticker %s already exists for exchange %s.";

    public TickerAlreadyExistsException(StockDTO stockDTO) {
        super(String.format(ERROR_MESSAGE, stockDTO.ticker(), stockDTO.exchange()));
    }
}
