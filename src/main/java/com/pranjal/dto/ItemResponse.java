package com.pranjal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private String itemId;
    private String name;
    private String description;
    private String categoryId;
    private String categoryName;
    private double price;
    private String imageUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
