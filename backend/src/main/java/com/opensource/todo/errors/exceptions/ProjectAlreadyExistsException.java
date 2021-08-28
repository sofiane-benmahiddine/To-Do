package com.opensource.todo.errors.exceptions;

public final class ProjectAlreadyExistsException extends RuntimeException {
    public ProjectAlreadyExistsException() {
        super();
    }

    public ProjectAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProjectAlreadyExistsException(final String message) {
        super(message);
    }

    public ProjectAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

}
