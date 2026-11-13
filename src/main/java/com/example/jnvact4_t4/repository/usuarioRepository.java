package com.example.jnvact4_t4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jnvact4_t4.entity.usuario;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Long> {

    Optional<usuario> findByUsername(String username);

    boolean existsByUsername(String username);
}
