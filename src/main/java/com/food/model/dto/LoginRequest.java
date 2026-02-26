package com.food.model.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String userId;   // email or mobile
    private String password;

}