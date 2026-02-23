package com.food.model.dto;

import lombok.Data;

@Data
public class UserRegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private String userType;
}
