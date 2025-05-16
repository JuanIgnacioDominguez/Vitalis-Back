package com.uade.dam.demo.dto;

import lombok.Data;

@Data
public class AppointmentCreateRequestDTO {
    private String profesionalId;
    private String fecha;
    private String hora;
}
