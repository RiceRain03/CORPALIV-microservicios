package com.corpaliv.ms_donaciones.controller;

import com.corpaliv.ms_donaciones.model.Donacion;
import com.corpaliv.ms_donaciones.service.DonacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DonacionController {

    private final DonacionService donacionService;

    @PostMapping
    public ResponseEntity<Donacion> crear(@Valid @RequestBody Donacion donacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donacionService.crear(donacion));
    }

    @GetMapping
    public List<Donacion> listar() {
        return donacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donacion> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(donacionService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{correo}")
    public List<Donacion> listarPorUsuario(@PathVariable String correo) {
        return donacionService.listarPorCorreo(correo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donacion> actualizar(@PathVariable Long id, @Valid @RequestBody Donacion donacion) {
        return ResponseEntity.ok(donacionService.actualizar(id, donacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        donacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
