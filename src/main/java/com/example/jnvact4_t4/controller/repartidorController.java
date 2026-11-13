package com.example.jnvact4_t4.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.jnvact4_t4.dto.RepartidorCreateDto;
import com.example.jnvact4_t4.dto.RepartidorPageDto;
import com.example.jnvact4_t4.dto.RepartidorResponseDto;
import com.example.jnvact4_t4.dto.RepartidorUpdateDto;
import com.example.jnvact4_t4.service.repartidorService;

@RestController
@RequestMapping("/api/repartidores")
public class repartidorController {

    private final repartidorService repartidorService;

    public repartidorController(repartidorService repartidorService) {
        this.repartidorService = repartidorService;
    }

    @GetMapping
    public ResponseEntity<RepartidorPageDto> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(repartidorService.getRepartidores(pageable));
    }

    @GetMapping("/{repartidor_id}")
    public ResponseEntity<RepartidorResponseDto> getById(
            @PathVariable("repartidor_id") Long repartidor_id) {
        return repartidorService.getRepartidor(repartidor_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RepartidorResponseDto> create(
            @Valid @RequestBody RepartidorCreateDto repartidor) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(repartidorService.create(repartidor));
    }

    @PutMapping("/{repartidor_id}")
    public ResponseEntity<RepartidorResponseDto> update(
            @PathVariable("repartidor_id") Long repartidor_id,
            @Valid @RequestBody RepartidorUpdateDto repartidor) {
        return repartidorService.update(repartidor_id, repartidor)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{repartidor_id}")
    public ResponseEntity<Void> delete(@PathVariable("repartidor_id") Long repartidor_id) {
        if (!repartidorService.delete(repartidor_id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}
