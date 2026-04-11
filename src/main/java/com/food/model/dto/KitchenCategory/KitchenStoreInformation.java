package com.food.model.dto.KitchenCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class KitchenStoreInformation {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String mobile;
}
