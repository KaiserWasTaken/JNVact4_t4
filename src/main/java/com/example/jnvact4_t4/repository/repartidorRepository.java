package com.example.jnvact4_t4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jnvact4_t4.entity.repartidor;

@Repository
public interface repartidorRepository extends JpaRepository<repartidor, Long> {
}
