package com.example.jnvact4_t4.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.jnvact4_t4.dto.RepartidorRequestDto;
import com.example.jnvact4_t4.dto.RepartidorResponseDto;
import com.example.jnvact4_t4.service.repartidorService;

@RestController
@RequestMapping("/api/repartidores")
public class repartidorController {

    private final repartidorService repartidorService;

    public repartidorController(repartidorService repartidorService) {
        this.repartidorService = repartidorService;
    }

    @GetMapping
    public ResponseEntity<List<RepartidorResponseDto>> getAll() {
        return ResponseEntity.ok(repartidorService.getRepartidores());
    }

    @GetMapping("/{repartidor_id}")
    public ResponseEntity<RepartidorResponseDto> getById(
            @PathVariable("repartidor_id") Long repartidor_id) {
        return repartidorService.getRepartidor(repartidor_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RepartidorResponseDto> saveOrUpdate(
            @Valid @RequestBody RepartidorRequestDto repartidor) {
        boolean updating = repartidor.repartidor_id() != null;

        if (updating && !repartidorService.exists(repartidor.repartidor_id())) {
            return ResponseEntity.notFound().build();
        }

        RepartidorResponseDto saved = repartidorService.saveOrUpdate(repartidor);
        HttpStatus status = updating ? HttpStatus.OK : HttpStatus.CREATED;
        return ResponseEntity.status(status).body(saved);
    }

    @DeleteMapping("/{repartidor_id}")
    public ResponseEntity<Void> delete(@PathVariable("repartidor_id") Long repartidor_id) {
        if (!repartidorService.exists(repartidor_id)) {
            return ResponseEntity.notFound().build();
        }

        repartidorService.delete(repartidor_id);
        return ResponseEntity.ok().build();
    }
}
