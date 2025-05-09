package com.uade.dam.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import com.uade.dam.demo.entity.ObraSocial;
import com.uade.dam.demo.entity.HistorialMedico;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "id_obra_social")
    private ObraSocial obraSocial;

    @OneToMany(mappedBy = "paciente")
    private List<Appointment> turnos;

    @OneToMany(mappedBy = "paciente")
    private List<HistorialMedico> historialMedico;
}