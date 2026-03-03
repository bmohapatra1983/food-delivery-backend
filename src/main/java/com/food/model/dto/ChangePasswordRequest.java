package com.food.model.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String mailId;
    private String oldPassword;
    private String newPassword;
}
