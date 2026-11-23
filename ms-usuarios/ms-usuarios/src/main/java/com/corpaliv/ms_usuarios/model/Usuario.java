package com.corpaliv.ms_usuarios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @NotBlank
    @Column(nullable = false)
    private String clave;

    @Column(nullable = false, length = 20)
    private String rol = "USER";

    private String fotoUrl;
}