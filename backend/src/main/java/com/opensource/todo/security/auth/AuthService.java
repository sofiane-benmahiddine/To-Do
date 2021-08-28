package com.opensource.todo.security.auth;

import com.opensource.todo.security.auth.dto.JwtResponse;
import com.opensource.todo.security.auth.dto.LoginRequest;
import com.opensource.todo.security.jwt.JwtUtils;
import com.opensource.todo.user.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserUtils userUtils;

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new JwtResponse(
                userUtils.getCurrentUser().getUsername(),
                userUtils.getCurrentUserRoles(),
                jwtUtils.generateJwtToken(authentication));
    }
}
