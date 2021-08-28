package com.opensource.todo.errors.exceptions;

public final class InvalidDateValueException extends RuntimeException {

    public InvalidDateValueException() {
        super();
    }

    public InvalidDateValueException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidDateValueException(final String message) {
        super(message);
    }

    public InvalidDateValueException(final Throwable cause) {
        super(cause);
    }
}
