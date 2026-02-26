package com.food.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({
        "status",
        "httpStatus",
        "message",
        "data"
})
public class ApiResponse<T> {

    private String status;
    private int httpStatus;
    private String message;
    private T data;
}
