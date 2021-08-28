package com.opensource.todo.errors.exceptions;

public final class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException() {
        super();
    }

    public ForbiddenOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ForbiddenOperationException(final String message) {
        super(message);
    }

    public ForbiddenOperationException(final Throwable cause) {
        super(cause);
    }
}
