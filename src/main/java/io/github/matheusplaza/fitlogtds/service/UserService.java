package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.exceptions.NotFoundException;
import io.github.matheusplaza.fitlogtds.model.User;
import io.github.matheusplaza.fitlogtds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));
    }
}
