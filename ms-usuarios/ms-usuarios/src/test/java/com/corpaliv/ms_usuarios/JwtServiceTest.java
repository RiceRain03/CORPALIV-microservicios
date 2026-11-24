package com.corpaliv.ms_usuarios;

import com.corpaliv.ms_usuarios.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "Q29ycGFsaXZBbGJhTGFiU2VjcmV0S2V5MjAyNkRTVTExMDVTZWd1cmlkYWQ=");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L);
    }

    @Test
    void generarYValidarTokenExitosamente() {
        String token = jwtService.generarToken("admin@corpaliv.cl", "ADMIN", "Gestor");

        assertNotNull(token);
        assertTrue(jwtService.esTokenValido(token));
        assertEquals("admin@corpaliv.cl", jwtService.extraerCorreo(token));
        assertEquals("ADMIN", jwtService.extraerRol(token));
    }

    @Test
    void rechazarTokenManipulado() {
        String token = jwtService.generarToken("admin@corpaliv.cl", "ADMIN", "Gestor");
        String tokenAlterado = token + "abc";

        assertFalse(jwtService.esTokenValido(tokenAlterado));
    }
}
