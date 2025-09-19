package io.github.matheusplaza.fitlogtds.controller;

import io.github.matheusplaza.fitlogtds.controller.dto.UserCreateDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.UserDTO;
import io.github.matheusplaza.fitlogtds.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController implements GenericController{

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserCreateDTO dto) {
        UserDTO user = service.saveUser(dto);
        URI location = gerarHeadLocation(user.id());
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUser(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(service.getListUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        return  ResponseEntity.ok(service.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
