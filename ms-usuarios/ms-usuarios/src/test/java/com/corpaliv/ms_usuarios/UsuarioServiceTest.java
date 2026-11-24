package com.corpaliv.ms_usuarios;

import com.corpaliv.ms_usuarios.dto.AuthResponse;
import com.corpaliv.ms_usuarios.dto.LoginRequest;
import com.corpaliv.ms_usuarios.model.Usuario;
import com.corpaliv.ms_usuarios.repository.UsuarioRepository;
import com.corpaliv.ms_usuarios.security.JwtService;
import com.corpaliv.ms_usuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void registrarUsuarioNuevoConClaveEncriptada() {
        Usuario nuevo = new Usuario(null, "Ana Pérez", "ana@corpaliv.cl", "clave123", "USER", null);
        when(usuarioRepository.existsByCorreo("ana@corpaliv.cl")).thenReturn(false);
        when(passwordEncoder.encode("clave123")).thenReturn("$2a$10$hashed");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            u.setId(1L);
            return u;
        });
        when(jwtService.generarToken("ana@corpaliv.cl", "USER", "Ana Pérez")).thenReturn("jwt-token-demo");

        AuthResponse res = usuarioService.registrar(nuevo);

        assertEquals("jwt-token-demo", res.getToken());
        assertEquals(1L, res.getId());
        verify(passwordEncoder).encode("clave123");
    }

    @Test
    void lanzarErrorSiCorreoYaExiste() {
        Usuario duplicado = new Usuario(null, "Ana", "ana@corpaliv.cl", "clave123", "USER", null);
        when(usuarioRepository.existsByCorreo("ana@corpaliv.cl")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrar(duplicado));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void loginExitosoDevuelveToken() {
        Usuario almacenado = new Usuario(1L, "Ana", "ana@corpaliv.cl", "$2a$10$hashed", "ADMIN", null);
        LoginRequest req = new LoginRequest();
        req.setCorreo("ana@corpaliv.cl");
        req.setClave("clave123");

        when(usuarioRepository.findByCorreo("ana@corpaliv.cl")).thenReturn(Optional.of(almacenado));
        when(passwordEncoder.matches("clave123", "$2a$10$hashed")).thenReturn(true);
        when(jwtService.generarToken("ana@corpaliv.cl", "ADMIN", "Ana")).thenReturn("token-valido");

        AuthResponse res = usuarioService.login(req);

        assertEquals("token-valido", res.getToken());
        assertEquals("ADMIN", res.getRol());
    }

    @Test
    void eliminarUsuarioInexistenteLanzaExcepcion() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> usuarioService.eliminar(99L));
    }
}