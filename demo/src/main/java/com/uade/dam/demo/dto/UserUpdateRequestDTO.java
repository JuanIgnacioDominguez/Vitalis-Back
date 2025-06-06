package com.uade.dam.demo.dto;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    private String nombre;
    private String email;
    private String dni;
    private String obraSocial;
    private String nroAfiliado;
    private String telefono;
}
