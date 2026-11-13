package com.example.jnvact4_t4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jnvact4_t4.entity.usuario;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Long> {

    @Query("select u from usuario u where u.username = :username")
    Optional<usuario> findByUsername(@Param("username") String username);

    @Query("select case when count(u) > 0 then true else false end "
            + "from usuario u where u.username = :username")
    boolean existsByUsername(@Param("username") String username);
}
