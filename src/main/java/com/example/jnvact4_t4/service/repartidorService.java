package com.example.jnvact4_t4.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.jnvact4_t4.dto.RepartidorCreateDto;
import com.example.jnvact4_t4.dto.RepartidorPageDto;
import com.example.jnvact4_t4.dto.RepartidorResponseDto;
import com.example.jnvact4_t4.dto.RepartidorUpdateDto;
import com.example.jnvact4_t4.entity.repartidor;
import com.example.jnvact4_t4.repository.repartidorRepository;

@Service
public class repartidorService {

    private final repartidorRepository repartidorRepository;

    public repartidorService(repartidorRepository repartidorRepository) {
        this.repartidorRepository = repartidorRepository;
    }

    public RepartidorPageDto getRepartidores(Pageable pageable) {
        Page<repartidor> page = repartidorRepository.findAll(pageable);
        return new RepartidorPageDto(
                page.map(this::toResponseDto).getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }

    public Optional<RepartidorResponseDto> getRepartidor(Long repartidor_id) {
        return repartidorRepository.findById(repartidor_id)
                .map(this::toResponseDto);
    }

    public RepartidorResponseDto create(RepartidorCreateDto dto) {
        repartidor repartidor = new repartidor();
        updateFields(repartidor, dto.nombre(), dto.apellido(), dto.direccion(), dto.numero_ruta());

        return toResponseDto(repartidorRepository.save(repartidor));
    }

    public Optional<RepartidorResponseDto> update(Long repartidor_id, RepartidorUpdateDto dto) {
        return repartidorRepository.findById(repartidor_id)
                .map(repartidor -> {
                    updateFields(repartidor, dto.nombre(), dto.apellido(), dto.direccion(), dto.numero_ruta());
                    return toResponseDto(repartidorRepository.save(repartidor));
                });
    }

    private void updateFields(
            repartidor repartidor,
            String nombre,
            String apellido,
            String direccion,
            Integer numero_ruta) {
        repartidor.setNombre(nombre);
        repartidor.setApellido(apellido);
        repartidor.setDireccion(direccion);
        repartidor.setNumero_ruta(numero_ruta);
    }

    public boolean delete(Long repartidor_id) {
        if (!repartidorRepository.existsById(repartidor_id)) {
            return false;
        }
        repartidorRepository.deleteById(repartidor_id);
        return true;
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