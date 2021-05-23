package com.opensource.todo.security.controllers;

import com.opensource.todo.security.models.UserDto;
import com.opensource.todo.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void processRegistration(@Valid @RequestBody UserDto userDto) {
        userRepository.save(userDto.toUser(passwordEncoder));
    }
}
