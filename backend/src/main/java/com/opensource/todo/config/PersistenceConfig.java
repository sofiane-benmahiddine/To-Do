package com.opensource.todo.config;

import com.opensource.todo.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class PersistenceConfig {
    private final UserService userService;

    @Bean
    public ApplicationRunner dataPreloader() {
        return args -> {
            userService.createAdmin();
        };
    }
}
