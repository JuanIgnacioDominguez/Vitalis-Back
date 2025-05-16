package com.uade.dam.demo.dto;

import lombok.Data;

@Data
public class NewPasswordRequestDTO {
    private String email;
    private String code;
    private String nueva;
}
