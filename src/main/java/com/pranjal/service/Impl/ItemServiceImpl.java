package com.pranjal.service.Impl;

import com.pranjal.dto.ItemRequest;
import com.pranjal.dto.ItemResponse;
import com.pranjal.entity.CategoryEntity;
import com.pranjal.entity.ItemEntity;
import com.pranjal.repository.CategoryRepository;
import com.pranjal.repository.ItemRepository;
import com.pranjal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileUploadServiceImpl uploadService;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public ItemResponse add(ItemRequest itemRequest, MultipartFile file) {
        Map<String, String> uploadedFile = uploadService.uploadFile(file);
        ItemEntity newItem = convertToEntity(itemRequest);
        CategoryEntity category = categoryRepository.
                findByCategoryId(itemRequest.getCategoryId())
                .orElseThrow(()->new RuntimeException("No Category found : " + itemRequest.getCategoryId()));
        newItem.setCategory(category);
        newItem.setImageUrl(uploadedFile.get("secure_url"));
        newItem.setPublicId(uploadedFile.get("public_id"));
        newItem = itemRepository.save(newItem);

        return convertToResponse(newItem);
    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .categoryId(newItem.getCategory().getCategoryId())
                .categoryName(newItem.getCategory().getName())
                .price(newItem.getPrice())
                .imageUrl(newItem.getImageUrl())
                .updatedAt(newItem.getUpdatedAt())
                .createdAt(newItem.getCreatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest itemRequest) {
        return ItemEntity.builder().itemId(UUID.randomUUID().toString())
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .price(itemRequest.getPrice())
                .build();
    }

    @Override
    public List<ItemResponse> readAll() {
        return itemRepository.findAll()
                .stream().map((this::convertToResponse))
                .toList();
    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity existingItem = itemRepository.findByItemId(itemId).
                orElseThrow(()->new RuntimeException("No item found: " + itemId));
        uploadService.delete(existingItem.getPublicId());
        itemRepository.delete(existingItem);
    }
}
