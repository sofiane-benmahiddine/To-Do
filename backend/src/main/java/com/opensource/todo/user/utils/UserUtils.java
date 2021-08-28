package com.opensource.todo.user.utils;

import com.opensource.todo.errors.exceptions.ForbiddenOperationException;
import com.opensource.todo.user.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserUtils {

    public UserDetailsImpl getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return (UserDetailsImpl) securityContext.getAuthentication().getPrincipal();
    }

    public List<String> getCurrentUserRoles() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public List<String> userAccessFilter(Long recordUserId) {
        Long currentUserId = getCurrentUser().getId();
        List<String> currentUserRoles = getCurrentUserRoles();
        if (!currentUserId.equals(recordUserId) && !currentUserRoles.contains("ROLE_ADMIN")) {
            throw new ForbiddenOperationException("Forbidden operation");
        }
        return currentUserRoles;
    }
}
