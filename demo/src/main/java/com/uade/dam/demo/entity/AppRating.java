package com.uade.dam.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private int puntuacion; 
    
    @ElementCollection
    @CollectionTable(name = "app_rating_aspectos_positivos", joinColumns = @JoinColumn(name = "rating_id"))
    @Column(name = "aspecto")
    private List<String> aspectosPositivos; 
    
    @ElementCollection
    @CollectionTable(name = "app_rating_aspectos_mejorar", joinColumns = @JoinColumn(name = "rating_id"))
    @Column(name = "aspecto")
    private List<String> aspectosMejorar; 
    
    @Column(length = 1000)
    private String comentario;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
}
