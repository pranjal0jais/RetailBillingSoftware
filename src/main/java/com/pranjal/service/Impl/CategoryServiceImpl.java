package com.pranjal.service.Impl;

import com.pranjal.dto.CategoryRequest;
import com.pranjal.dto.CategoryResponse;
import com.pranjal.entity.CategoryEntity;
import com.pranjal.repository.CategoryRepository;
import com.pranjal.repository.ItemRepository;
import com.pranjal.service.CategoryService;
import com.pranjal.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {
        CategoryEntity newCategory = convertToEntity(request);
        Map<String, String> uploadedFile = fileUploadService.uploadFile(file);
        newCategory.setImgUrl(uploadedFile.get("secure_url"));
        newCategory.setPublicId(uploadedFile.get("public_id"));
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> readAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity existingEntity = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(()->new RuntimeException("Category Not found with Id - " + categoryId));
        fileUploadService.delete(existingEntity.getPublicId());
        categoryRepository.delete(existingEntity);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .imageUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .imageUrl(newCategory.getImgUrl())
                .publicId(newCategory.getPublicId())
                .itemCount(itemRepository.countByCategoryId(newCategory.getId()))
                .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .build();

    }
}
