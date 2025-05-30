package com.uade.dam.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopFavoriteDTO {
    private String id;
    private String name;
    private String specialty;
    private Long favoriteCount;
}