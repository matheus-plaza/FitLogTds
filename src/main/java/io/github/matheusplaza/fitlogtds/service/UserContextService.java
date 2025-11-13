package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContextService {


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Utilizador não autenticado");
        }

        return (User) authentication.getPrincipal();
    }
}