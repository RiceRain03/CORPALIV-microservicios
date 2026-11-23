package com.corpaliv.ms_donaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "donaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @NotNull
    @Min(1000)
    @Max(1000000)
    private Integer monto;

    @NotNull
    private Long fecha;

    @NotBlank
    private String destino;

    @NotNull
    private Boolean esAnonima;

    @AssertTrue(message = "Debe aceptar los términos")
    private Boolean aceptaTerminos;

    private String correoUsuario;
}