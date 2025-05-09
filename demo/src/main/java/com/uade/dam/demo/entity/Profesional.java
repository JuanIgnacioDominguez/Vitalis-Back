package com.uade.dam.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profesionales")
public class Profesional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String availableHours;
    private String room;

    @ManyToMany
    @JoinTable(
        name = "profesional_especialidad",
        joinColumns = @JoinColumn(name = "profesional_id"),
        inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private List<Especialidad> especialidades;

    @OneToMany(mappedBy = "profesional")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "profesional")
    private List<HistorialMedico> historiales;
}