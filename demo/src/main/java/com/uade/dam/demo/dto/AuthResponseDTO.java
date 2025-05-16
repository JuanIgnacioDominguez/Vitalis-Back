package com.uade.dam.demo.dto;

import com.uade.dam.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private User user;
}
