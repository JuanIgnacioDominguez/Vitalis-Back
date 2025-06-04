package com.uade.dam.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String professionalId;
    private String date; 
    private String time; 

    @Enumerated(EnumType.STRING)
    private Status status; // AVAILABLE, RESERVED

    private String appointmentId; // null si está disponible

    public enum Status {
        AVAILABLE, RESERVED
    }
}