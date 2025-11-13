package io.github.matheusplaza.fitlogtds.controller;

import io.github.matheusplaza.fitlogtds.controller.dto.AuthenticationDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.LoginResponseDTO;
import io.github.matheusplaza.fitlogtds.model.User;
import io.github.matheusplaza.fitlogtds.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.matheusplaza.fitlogtds.controller.dto.GoogleLoginDTO;
import io.github.matheusplaza.fitlogtds.service.GoogleAuthService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private GoogleAuthService googleAuthService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        // O Spring Security verifica email e senha automaticamente aqui
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Se chegou aqui, a senha ta correta, entao gera o token.
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponseDTO> loginGoogle(@RequestBody GoogleLoginDTO data) {
        String token = googleAuthService.authenticateGoogle(data.token());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}