package com.pranjal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranjal.dto.ItemRequest;
import com.pranjal.dto.ItemResponse;
import com.pranjal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemResponse> fetchItems(){
        return itemService.readAll();
    }

    @PostMapping("/admin/items/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse addItem(@RequestPart("item") String itemString,
                                        @RequestPart("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        ItemRequest request = null;
        try{
            request = objectMapper.readValue(itemString, ItemRequest.class);
            return itemService.add(request, file);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/admin/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable String itemId){
        try {
            itemService.deleteItem(itemId);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found - " + itemId);
        }
    }
}
