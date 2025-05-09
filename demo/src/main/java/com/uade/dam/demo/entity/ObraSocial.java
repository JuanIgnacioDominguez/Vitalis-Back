package com.uade.dam.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "obras_sociales")
public class ObraSocial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ObraSocialEnum nombre;

    @OneToMany(mappedBy = "obraSocial")
    private List<User> usuarios;
    
    public enum ObraSocialEnum {
        PAMI,
        OSDE,
        SWISS_MEDICAL,
        GALENO,
        MEDICUS
    }
    
}