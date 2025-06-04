package com.uade.dam.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String specialty;
    // Puedes agregar horariosDisponibles como JSON o entidad aparte
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;
}
