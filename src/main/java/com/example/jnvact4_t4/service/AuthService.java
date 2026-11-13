package com.example.jnvact4_t4.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jnvact4_t4.dto.AuthResponseDto;
import com.example.jnvact4_t4.dto.LoginRequestDto;
import com.example.jnvact4_t4.dto.RegisterRequestDto;
import com.example.jnvact4_t4.dto.UsuarioResponseDto;
import com.example.jnvact4_t4.entity.usuario;
import com.example.jnvact4_t4.repository.usuarioRepository;
import com.example.jnvact4_t4.security.JwtService;

@Service
public class AuthService {

    private final usuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            usuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UsuarioResponseDto register(RegisterRequestDto dto) {
        if (usuarioRepository.existsByUsername(dto.username())) {
            throw new UsernameAlreadyExistsException();
        }

        usuario usuario = new usuario();
        usuario.setUsername(dto.username());
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuario.setRol("USER");

        return toResponse(usuarioRepository.save(usuario));
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        usuario usuario = usuarioRepository.findByUsername(dto.username())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(dto.password(), usuario.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return new AuthResponseDto(
                jwtService.generateToken(usuario),
                "Bearer",
                jwtService.getExpirationSeconds());
    }

    private UsuarioResponseDto toResponse(usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getUsuario_id(),
                usuario.getUsername(),
                usuario.getRol());
    }
}
