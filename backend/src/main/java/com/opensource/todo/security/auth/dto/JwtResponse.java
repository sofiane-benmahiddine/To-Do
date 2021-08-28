package com.opensource.todo.security.auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private final String username;
    private final List<String> roles;
    private final String type = "Bearer";
    private final String token;
}
