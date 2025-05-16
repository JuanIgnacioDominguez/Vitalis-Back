package com.uade.dam.demo.dto;

import lombok.Data;

@Data
public class PasswordVerifyRequest {
    private String email;
    private String code;
}
