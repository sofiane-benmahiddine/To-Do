package com.opensource.todo.errors.exceptions;

public final class UserRoleNotFoundException extends RuntimeException {
    public UserRoleNotFoundException() {
        super();
    }

    public UserRoleNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserRoleNotFoundException(final String message) {
        super(message);
    }

    public UserRoleNotFoundException(final Throwable cause) {
        super(cause);
    }
}
