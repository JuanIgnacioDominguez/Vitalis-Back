package com.uade.dam.demo.dto;

import lombok.Data;

@Data
public class TimeSlotReserveRequest {
    private String professionalId;
    private String date;
    private String time;
    private String userId; // o appointmentId, según tu modelo
}
