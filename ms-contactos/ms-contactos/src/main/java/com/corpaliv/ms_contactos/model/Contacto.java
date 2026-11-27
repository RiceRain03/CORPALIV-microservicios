package com.corpaliv.ms_contactos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "contactos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @NotNull
    @Min(1)
    private Integer edad;

    @NotBlank
    private String fechaContacto;

    @NotNull
    private Boolean esEmpresa;

    @AssertTrue(message = "Debe aceptar los términos")
    private Boolean aceptaTerminos;

    @Column(length = 1000)
    private String mensaje;

    @Column(length = 30)
    private String tipoColaboracion;

    @Column(length = 20)
    private String estado;
}