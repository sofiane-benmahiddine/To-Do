package com.opensource.todo.errors;

import com.opensource.todo.errors.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Provides handling for exceptions throughout this service.
     */
    @ExceptionHandler({
            UserAlreadyExistException.class,
            UserNotFoundException.class,
            UserRoleNotFoundException.class,
            MethodArgumentNotValidException.class,
            ForbiddenOperationException.class,
            ProjectAlreadyExistsException.class,
            InvalidDateValueException.class,
            ProjectNotFoundException.class,
            TicketNotFoundException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof UserAlreadyExistException) {
            HttpStatus status = HttpStatus.CONFLICT;
            UserAlreadyExistException userAlreadyExistException = (UserAlreadyExistException) ex;
            return handleUserAlreadyExistsException(userAlreadyExistException, headers, status, request);
        } else if (ex instanceof MethodArgumentNotValidException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            return handleMethodArgumentNotValidException(methodArgumentNotValidException, headers, status, request);
        } else if (ex instanceof UserRoleNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserRoleNotFoundException userRoleNotFoundException = (UserRoleNotFoundException) ex;
            return handleUserRoleNotFoundException(userRoleNotFoundException, headers, status, request);
        } else if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException userNotFoundException = (UserNotFoundException) ex;
            return handleUserNotFoundException(userNotFoundException, headers, status, request);
        } else if (ex instanceof ForbiddenOperationException) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            ForbiddenOperationException forbiddenOperationException = (ForbiddenOperationException) ex;
            return handleForbiddenOperationException(forbiddenOperationException, headers, status, request);
        } else if (ex instanceof ProjectAlreadyExistsException) {
            HttpStatus status = HttpStatus.CONFLICT;
            ProjectAlreadyExistsException projectAlreadyExistsException = (ProjectAlreadyExistsException) ex;
            return handleProjectAlreadyExistsException(projectAlreadyExistsException, headers, status, request);
        } else if (ex instanceof InvalidDateValueException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            InvalidDateValueException invalidDateValueException = (InvalidDateValueException) ex;
            return handleInvalidDateValueException(invalidDateValueException, headers, status, request);
        } else if (ex instanceof ProjectNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ProjectNotFoundException projectNotFoundException = (ProjectNotFoundException) ex;
            return handleProjectNotFoundException(projectNotFoundException, headers, status, request);
        } else if (ex instanceof TicketNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            TicketNotFoundException ticketNotFoundException = (TicketNotFoundException) ex;
            return handleTicketNotFoundException(ticketNotFoundException, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    protected ResponseEntity<ApiError> handleUserAlreadyExistsException(UserAlreadyExistException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return handleExceptionInternal(ex, new ApiError(status, message, errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleUserRoleNotFoundException(UserRoleNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return handleExceptionInternal(ex, new ApiError(status, message, errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return handleExceptionInternal(ex, new ApiError(status, message, errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleForbiddenOperationException(ForbiddenOperationException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return handleExceptionInternal(ex, new ApiError(status, message, errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleProjectAlreadyExistsException(ProjectAlreadyExistsException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return handleExceptionInternal(ex, new ApiError(status, message, errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleInvalidDateValueException(InvalidDateValueException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return handleExceptionInternal(ex, new ApiError(status, message, errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleProjectNotFoundException(ProjectNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return handleExceptionInternal(ex, new ApiError(status, message, errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleTicketNotFoundException(TicketNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return handleExceptionInternal(ex, new ApiError(status, message, errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Invalid field error";
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return handleExceptionInternal(ex, new ApiError(status, message, errors), headers, status, request);
    }

    /**
     * A single place to customize the response body of all Exception types.
     */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
