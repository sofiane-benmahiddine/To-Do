package com.opensource.todo.errors.exceptions;

public final class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {
        super();
    }

    public ProjectNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProjectNotFoundException(final String message) {
        super(message);
    }

    public ProjectNotFoundException(final Throwable cause) {
        super(cause);
    }
}
