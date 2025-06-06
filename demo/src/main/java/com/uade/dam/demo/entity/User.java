package com.uade.dam.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nombre;
    
    @Column(unique = true)
    private String email;

    private String password;

    private String telefono;

    @Column(nullable = true)
    private String obraSocial;

    @Column(nullable = true)
    private String nroAfiliado;

    @Column(nullable = true)
    private String dni;

    private LocalDateTime fechaRegistro;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;
}