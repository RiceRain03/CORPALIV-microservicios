package com.corpaliv.ms_usuarios.service;

import com.corpaliv.ms_usuarios.dto.AuthResponse;
import com.corpaliv.ms_usuarios.dto.LoginRequest;
import com.corpaliv.ms_usuarios.model.Usuario;
import com.corpaliv.ms_usuarios.repository.UsuarioRepository;
import com.corpaliv.ms_usuarios.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse registrar(Usuario usuario) {
        String correoNormalizado = usuario.getCorreo().trim().toLowerCase();
        if (usuarioRepository.existsByCorreo(correoNormalizado)) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        usuario.setCorreo(correoNormalizado);
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        if (usuario.getRol() == null || usuario.getRol().isBlank()) {
            usuario.setRol("USER");
        }
        Usuario guardado = usuarioRepository.save(usuario);
        String token = jwtService.generarToken(guardado.getCorreo(), guardado.getRol(), guardado.getNombre());
        return new AuthResponse(token, guardado.getId(), guardado.getNombre(), guardado.getCorreo(), guardado.getRol());
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo().trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getClave(), usuario.getClave())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = jwtService.generarToken(usuario.getCorreo(), usuario.getRol(), usuario.getNombre());
        return new AuthResponse(token, usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol());
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public Usuario actualizar(Long id, Usuario datos) {
        Usuario existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        if (datos.getFotoUrl() != null) {
            existente.setFotoUrl(datos.getFotoUrl());
        }
        if (datos.getRol() != null && !datos.getRol().isBlank()) {
            existente.setRol(datos.getRol());
        }
        if (datos.getClave() != null && !datos.getClave().isBlank()) {
            existente.setClave(passwordEncoder.encode(datos.getClave()));
        }
        return usuarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
