package com.ildar.stockassistant.portfolio.dto.error;

public record ErrorResponse(String message) {

    public static ErrorResponse internalError() {
        return new ErrorResponse("Error encountered during operation. Please try again later.");
    }

    public static ErrorResponse withMessage(String message) {
        return new ErrorResponse(message);
    }
}