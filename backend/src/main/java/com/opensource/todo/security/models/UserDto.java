package com.opensource.todo.security.models;

import com.opensource.todo.security.models.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotBlank(message = "username is required")
    @Size(max = 20, message = "maximum size is 20 characters")
    private String username;

    @NotBlank(message = "password is required")
    @Size(max = 50, message = "maximum size is 50 characters")
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password));
    }
}
