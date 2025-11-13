package io.github.matheusplaza.fitlogtds.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.github.matheusplaza.fitlogtds.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleAuthService {

    @Value("${api.security.google.client-id}")
    private String googleClientId;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public String authenticateGoogle(String googleToken) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            //Valida o token com o Google
            GoogleIdToken idToken = verifier.verify(googleToken);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Extrai infos
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                // Busca ou cria o usuário no banco
                User user = userService.findOrCreateUserFromGoogle(email, name);

                //Gera o token JWT (o mesmo do login normal)
                return tokenService.generateToken(user);
            } else {
                throw new RuntimeException("Token do Google inválido");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Erro ao validar token do Google", e);
        }
    }
}