package com.uade.dam.demo.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email;
    private String password;
    private String nombre;
    private String telefono;
    private String codigoVerificacion; 
}
