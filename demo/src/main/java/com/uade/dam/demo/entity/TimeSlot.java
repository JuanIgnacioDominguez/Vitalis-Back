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

    private String appointmentId; // usuario que reservó el turno
}