package com.corpaliv.ms_donaciones;

import com.corpaliv.ms_donaciones.model.Donacion;
import com.corpaliv.ms_donaciones.repository.DonacionRepository;
import com.corpaliv.ms_donaciones.service.DonacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonacionServiceTest {

    @Mock
    private DonacionRepository donacionRepository;

    @InjectMocks
    private DonacionService donacionService;

    @Test
    void crearDonacionAnonimaOcultaNombreYCorreo() {
        Donacion donacion = new Donacion(null, "Carlos", 15000, 1730000000L, "Alba Lab", true, true, "carlos@correo.cl");
        when(donacionRepository.save(any(Donacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Donacion guardada = donacionService.crear(donacion);

        assertEquals("Anónimo", guardada.getNombre());
        assertNull(guardada.getCorreoUsuario());
    }

    @Test
    void actualizarDonacionExistente() {
        Donacion original = new Donacion(1L, "Carlos", 10000, 1730000000L, "Alba Lab", false, true, "carlos@correo.cl");
        Donacion nuevosDatos = new Donacion(1L, "Carlos", 25000, 1730000000L, "Escuela Jan Van Dijk", false, true, "carlos@correo.cl");

        when(donacionRepository.findById(1L)).thenReturn(Optional.of(original));
        when(donacionRepository.save(any(Donacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Donacion actualizada = donacionService.actualizar(1L, nuevosDatos);

        assertEquals(25000, actualizada.getMonto());
        assertEquals("Escuela Jan Van Dijk", actualizada.getDestino());
    }

    @Test
    void listarDonacionesPorCorreo() {
        when(donacionRepository.findByCorreoUsuarioOrderByIdDesc("carlos@correo.cl"))
                .thenReturn(List.of(new Donacion()));

        assertEquals(1, donacionService.listarPorCorreo("carlos@correo.cl").size());
    }
}