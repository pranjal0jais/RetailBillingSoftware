package com.pranjal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private String name;
    private String description;
    private String categoryId;
    private Double price;
}
