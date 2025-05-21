package com.pranjal.service;

import com.pranjal.dto.ItemRequest;
import com.pranjal.dto.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    ItemResponse add(ItemRequest itemRequest, MultipartFile file);

    List<ItemResponse> readAll();

    void deleteItem(String itemId);
}
