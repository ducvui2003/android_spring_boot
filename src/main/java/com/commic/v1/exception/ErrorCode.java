package com.commic.v1.exception;

import lombok.Getter;

@Getter

public enum ErrorCode {
    INVALID_REQUEST(400, "Invalid request"),
    BOOK_EXIST(200, "Book already exist"),
    BOOK_EMPTY(204, "Book not found"),
    PARAMETER_NOT_VALID(400, "Parameter not valid"),
    USER_NOT_FOUND(204, "User not found"),
    CHAPTER_NOT_FOUND(204,"Chapter not found"),
    VALIDATION_ERROR(400, "Validation error"),
    PARAMETER_MISSING(400, "Parameter missing"),
    CREATE_SUCCESS(200, "Create success"),;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    }
