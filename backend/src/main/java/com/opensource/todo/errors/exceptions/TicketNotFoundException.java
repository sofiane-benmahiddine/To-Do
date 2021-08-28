package com.opensource.todo.errors.exceptions;

public final class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException() {
        super();
    }

    public TicketNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TicketNotFoundException(final String message) {
        super(message);
    }

    public TicketNotFoundException(final Throwable cause) {
        super(cause);
    }
}
