package com.uade.dam.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int score;
    private String comment;
    private LocalDateTime date;
}
