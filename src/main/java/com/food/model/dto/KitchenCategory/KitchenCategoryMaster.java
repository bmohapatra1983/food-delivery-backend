package com.food.model.dto.KitchenCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class KitchenCategoryMaster {

    private Long id;
    private String categoryName;
    private String description;
}
