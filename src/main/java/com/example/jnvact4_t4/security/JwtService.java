package com.example.jnvact4_t4.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.example.jnvact4_t4.entity.usuario;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs) {
        if (secret.length() < 32) {
            throw new IllegalStateException("JWT_SECRET debe tener al menos 32 caracteres");
        }

        SecretKeySpec key = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256");
        this.jwtEncoder = NimbusJwtEncoder.withSecretKey(key).build();
        this.expirationMs = expirationMs;
    }

    public String generateToken(usuario usuario) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(expirationMs, ChronoUnit.MILLIS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(usuario.getUsername())
                .claim("rol", usuario.getRol())
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(
                        org.springframework.security.oauth2.jwt.JwsHeader
                                .with(MacAlgorithm.HS256)
                                .build(),
                        claims))
                .getTokenValue();
    }

    public long getExpirationSeconds() {
        return expirationMs / 1000;
    }
}
