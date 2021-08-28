package com.opensource.todo.audit;

import com.opensource.todo.user.services.UserDetailsImpl;
import com.opensource.todo.user.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Long> {
    private final UserUtils userUtils;

    @Override
    public Optional<Long> getCurrentAuditor() {
        UserDetailsImpl currentUser = userUtils.getCurrentUser();
        return Optional.of(currentUser.getId());
    }

}