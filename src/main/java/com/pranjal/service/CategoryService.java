package com.pranjal.service;

import com.pranjal.dto.CategoryRequest;
import com.pranjal.dto.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryResponse add(CategoryRequest request, MultipartFile file);

    List<CategoryResponse> readAll();

    void delete(String categoryId);
}
