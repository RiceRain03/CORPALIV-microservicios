package com.corpaliv.ms_donaciones.service;

import com.corpaliv.ms_donaciones.model.Donacion;
import com.corpaliv.ms_donaciones.repository.DonacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonacionService {

    private final DonacionRepository donacionRepository;

    public Donacion crear(Donacion donacion) {
        if (Boolean.TRUE.equals(donacion.getEsAnonima())) {
            donacion.setNombre("Anónimo");
            donacion.setCorreoUsuario(null);
        }
        return donacionRepository.save(donacion);
    }

    public List<Donacion> listarTodas() {
        return donacionRepository.findAllByOrderByIdDesc();
    }

    public List<Donacion> listarPorCorreo(String correo) {
        return donacionRepository.findByCorreoUsuarioOrderByIdDesc(correo);
    }

    public Donacion obtenerPorId(Long id) {
        return donacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donación no encontrada con id: " + id));
    }

    public Donacion actualizar(Long id, Donacion datos) {
        Donacion existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setMonto(datos.getMonto());
        existente.setFecha(datos.getFecha());
        existente.setDestino(datos.getDestino());
        existente.setEsAnonima(datos.getEsAnonima());
        existente.setAceptaTerminos(datos.getAceptaTerminos());
        existente.setCorreoUsuario(datos.getCorreoUsuario());
        return donacionRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!donacionRepository.existsById(id)) {
            throw new RuntimeException("Donación no encontrada con id: " + id);
        }
        donacionRepository.deleteById(id);
    }
}