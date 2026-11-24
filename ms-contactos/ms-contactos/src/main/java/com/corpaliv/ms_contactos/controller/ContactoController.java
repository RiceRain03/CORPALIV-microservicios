package com.corpaliv.ms_contactos.controller;

import com.corpaliv.ms_contactos.model.Contacto;
import com.corpaliv.ms_contactos.service.ContactoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ContactoController {

    private final ContactoService contactoService;

    @PostMapping
    public ResponseEntity<Contacto> crear(@Valid @RequestBody Contacto contacto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactoService.crear(contacto));
    }

    @GetMapping
    public List<Contacto> listar() {
        return contactoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contacto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(contactoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contacto> actualizar(@PathVariable Long id, @Valid @RequestBody Contacto contacto) {
        return ResponseEntity.ok(contactoService.actualizar(id, contacto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        contactoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
