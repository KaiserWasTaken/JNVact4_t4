package com.example.jnvact4_t4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.jnvact4_t4.dto.RepartidorRequestDto;
import com.example.jnvact4_t4.dto.RepartidorResponseDto;
import com.example.jnvact4_t4.entity.repartidor;
import com.example.jnvact4_t4.repository.repartidorRepository;

@Service
public class repartidorService {

    private final repartidorRepository repartidorRepository;

    public repartidorService(repartidorRepository repartidorRepository) {
        this.repartidorRepository = repartidorRepository;
    }

    public List<RepartidorResponseDto> getRepartidores() {
        return repartidorRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    public Optional<RepartidorResponseDto> getRepartidor(Long repartidor_id) {
        return repartidorRepository.findById(repartidor_id)
                .map(this::toResponseDto);
    }

    public RepartidorResponseDto saveOrUpdate(RepartidorRequestDto dto) {
        repartidor repartidor = new repartidor();
        repartidor.setRepartidor_id(dto.repartidor_id());
        repartidor.setNombre(dto.nombre());
        repartidor.setApellido(dto.apellido());
        repartidor.setDireccion(dto.direccion());
        repartidor.setNumero_ruta(dto.numero_ruta());

        return toResponseDto(repartidorRepository.save(repartidor));
    }

    public boolean exists(Long repartidor_id) {
        return repartidorRepository.existsById(repartidor_id);
    }

    public void delete(Long repartidor_id) {
        repartidorRepository.deleteById(repartidor_id);
    }

    public RepartidorResponseDto toResponseDto(repartidor repartidor) {
        return new RepartidorResponseDto(
                repartidor.getRepartidor_id(),
                repartidor.getNombre(),
                repartidor.getApellido(),
                repartidor.getDireccion(),
                repartidor.getNumero_ruta());
    }

}