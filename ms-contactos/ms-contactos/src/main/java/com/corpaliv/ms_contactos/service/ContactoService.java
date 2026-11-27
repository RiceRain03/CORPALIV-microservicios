package com.corpaliv.ms_contactos.service;

import com.corpaliv.ms_contactos.model.Contacto;
import com.corpaliv.ms_contactos.repository.ContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactoService {

    public static final List<String> ESTADOS =
            List.of("RECIBIDA", "EN_REVISION", "CONTACTADO", "CONCRETADO", "DESCARTADO");

    private final ContactoRepository contactoRepository;

    public Contacto crear(Contacto contacto) {
        contacto.setEstado("RECIBIDA");
        return contactoRepository.save(contacto);
    }

    public List<Contacto> listarTodos() {
        return contactoRepository.findAllByOrderByIdDesc();
    }

    public Contacto obtenerPorId(Long id) {
        return contactoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado con id: " + id));
    }

    public Contacto actualizar(Long id, Contacto datos) {
        Contacto existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setEdad(datos.getEdad());
        existente.setFechaContacto(datos.getFechaContacto());
        existente.setEsEmpresa(datos.getEsEmpresa());
        existente.setAceptaTerminos(datos.getAceptaTerminos());
        existente.setMensaje(datos.getMensaje());
        existente.setTipoColaboracion(datos.getTipoColaboracion());
        return contactoRepository.save(existente);
    }

    public Contacto cambiarEstado(Long id, String estado) {
        if (estado == null || !ESTADOS.contains(estado)) {
            throw new IllegalArgumentException("Estado inválido: " + estado);
        }
        Contacto existente = obtenerPorId(id);
        existente.setEstado(estado);
        return contactoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!contactoRepository.existsById(id)) {
            throw new RuntimeException("Contacto no encontrado con id: " + id);
        }
        contactoRepository.deleteById(id);
    }
}