package com.uade.dam.demo.dto;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String actual;
    private String nueva;
}
