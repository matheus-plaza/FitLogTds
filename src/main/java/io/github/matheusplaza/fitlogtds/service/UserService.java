package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.controller.dto.UserCreateDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.UserDTO;
import io.github.matheusplaza.fitlogtds.exceptions.DuplicateResourceException;
import io.github.matheusplaza.fitlogtds.exceptions.NotFoundException;
import io.github.matheusplaza.fitlogtds.mapper.UserMapper;
import io.github.matheusplaza.fitlogtds.model.User;
import io.github.matheusplaza.fitlogtds.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));
    }

    //TODO: No checkpoint de Seguranca irei implementar o hash da senha com PasswordEncoder antes de persistir
    @Transactional
    public UserDTO saveUser(UserCreateDTO userDTO) {
        if(repository.existsByEmail(userDTO.email())){
            throw new DuplicateResourceException("Email ja cadastrado");
        }
        User user = new User();
        user.setEmail(userDTO.email());
        user.setName(userDTO.name());
        user.setPassword(userDTO.password());
        return mapper.toDTO(repository.save(user));
    }

    public UserDTO getUser(Long id) {
        return mapper.toDTO(findById(id));
    }

    public List<UserDTO> getListUsers() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = findById(id);
        Optional.ofNullable(userDTO.name()).ifPresent(user::setName);
        if (userDTO.email() != null) {
            Optional<User> email = repository.findByEmail(userDTO.email());
            if (email.isPresent() && !user.getId().equals(email.get().getId())) {
                throw new DuplicateResourceException("Email ja cadastrado");
            }
            user.setEmail(userDTO.email());
        }
        return mapper.toDTO(repository.save(user));
    }

    public void deleteUser(Long id) {
        User user = findById(id);
        repository.delete(user);
    }
}
