package com.pranjal.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CategoryResponse {

    private String categoryId;
    private String name;
    private String description;
    private String imageUrl;
    private String publicId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int itemCount;
}
