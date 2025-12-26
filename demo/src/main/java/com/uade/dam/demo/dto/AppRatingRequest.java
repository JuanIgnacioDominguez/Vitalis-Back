package com.uade.dam.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class AppRatingRequest {
    private int puntuacion; // 1-5
    private List<String> aspectosPositivos; 
    private List<String> aspectosMejorar; 
    private String comentario; 
}
