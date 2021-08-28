package com.opensource.todo.user.services;

import com.google.common.collect.ImmutableList;
import com.opensource.todo.errors.exceptions.UserAlreadyExistException;
import com.opensource.todo.errors.exceptions.UserNotFoundException;
import com.opensource.todo.errors.exceptions.UserRoleNotFoundException;
import com.opensource.todo.user.dto.UserDto;
import com.opensource.todo.user.dto.UserForm;
import com.opensource.todo.user.models.Role;
import com.opensource.todo.user.models.User;
import com.opensource.todo.user.repositories.RoleRepository;
import com.opensource.todo.user.repositories.UserRepository;
import com.opensource.todo.user.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserUtils userUtils;

    public List<User> getAllUsers() {
        return ImmutableList.copyOf(userRepository.findAll());
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID: <" + id + "> not found"));
    }

    public User addUser(UserForm userForm) {
        isUserValid(userForm);
        User user = new User(userForm.getUsername(),
                userForm.getEmail(),
                encoder.encode(userForm.getPassword()));
        Set<String> strRoles = userForm.getRole();
        Set<Role> roles = addUserRoles(strRoles);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User editUser(Long id, UserDto newUser) {
        List<String> userRoles = userUtils.userAccessFilter(id);
        User user = getUserById(id);
        isUserValid(newUser, user);
        if (newUser.getUsername() != null)
            user.setUsername(newUser.getUsername());
        if (newUser.getEmail() != null)
            user.setEmail(newUser.getEmail());
        if (newUser.getPassword() != null)
            user.setPassword((encoder.encode(newUser.getPassword())));
        if (userRoles.contains("ROLE_ADMIN")) {
            user.setRoles(editUserRoles(newUser.getRoles()));
        }
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID: <" + id + "> not found");
        }
        userRepository.deleteById(id);
    }

    private void isUserValid(UserDto newUser, User oldUser) {
        if (userRepository.existsByUsername(newUser.getUsername())
                && !oldUser.getUsername().equals(newUser.getUsername())) {
            throw new UserAlreadyExistException("Username already taken");
        }
        if (userRepository.existsByEmail(newUser.getEmail())
                && !oldUser.getEmail().equals(newUser.getEmail())) {
            throw new UserAlreadyExistException("Email already in use");
        }
    }

    private void isUserValid(UserForm newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new UserAlreadyExistException("Username already taken");
        }
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new UserAlreadyExistException("Email already in use");
        }
    }

    private Set<Role> addUserRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();
        List<String> currentUserRoles = userUtils.getCurrentUserRoles();
        if (strRoles == null || strRoles.isEmpty() || !currentUserRoles.contains("ROLE_ADMIN")) {
            Role userRole = roleRepository.findByTitle("user")
                    .orElseThrow(() -> new UserRoleNotFoundException("Role: " + strRoles + " not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role userRole = roleRepository.findByTitle(role)
                        .orElseThrow(() -> new UserRoleNotFoundException("Role: " + role + " not found"));
                roles.add(userRole);
            });
        }
        return roles;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private Set<Role> editUserRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();
        if (strRoles == null || strRoles.isEmpty()) {
            throw new UserRoleNotFoundException("Role field empty");
        }
        strRoles.forEach(role -> {
            Role userRole = roleRepository.findByTitle(role)
                    .orElseThrow(() -> new UserRoleNotFoundException("Role: " + role + " not found"));
            roles.add(userRole);
        });
        return roles;
    }

    public void createAdmin() {
        User user = new User("admin",
                "admin@email.com",
                encoder.encode("admin"));
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByTitle("admin")
                .orElseThrow(() -> new UserRoleNotFoundException("admin role not found"));
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
