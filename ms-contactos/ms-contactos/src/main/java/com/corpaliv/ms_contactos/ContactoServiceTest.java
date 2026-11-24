package com.corpaliv.ms_contactos;

import com.corpaliv.ms_contactos.model.Contacto;
import com.corpaliv.ms_contactos.repository.ContactoRepository;
import com.corpaliv.ms_contactos.service.ContactoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactoServiceTest {

    @Mock
    private ContactoRepository contactoRepository;

    @InjectMocks
    private ContactoService contactoService;

    @Test
    void crearYConsultarContacto() {
        Contacto c = new Contacto(1L, "Empresa Inclusiva", 35, "2026-11-23", true, true, "Alianza RSE");
        when(contactoRepository.save(any(Contacto.class))).thenReturn(c);
        when(contactoRepository.findById(1L)).thenReturn(Optional.of(c));

        Contacto creado = contactoService.crear(c);
        Contacto encontrado = contactoService.obtenerPorId(1L);

        assertEquals("Empresa Inclusiva", creado.getNombre());
        assertTrue(encontrado.getEsEmpresa());
    }

    @Test
    void eliminarContactoExistente() {
        when(contactoRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> contactoService.eliminar(1L));
        verify(contactoRepository).deleteById(1L);
    }
}
