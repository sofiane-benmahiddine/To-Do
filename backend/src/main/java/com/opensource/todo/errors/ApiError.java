package com.opensource.todo.errors;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
@Data
public class ApiError {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

}
