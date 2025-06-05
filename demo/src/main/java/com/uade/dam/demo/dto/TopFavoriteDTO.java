package com.uade.dam.demo.dto;

import com.uade.dam.demo.entity.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopFavoriteDTO {
    private String id;
    private String name;
    private Specialty specialty;
    private Long favoriteCount;
}