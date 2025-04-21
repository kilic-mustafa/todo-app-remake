package com.example.todoapp_remake.service;

import com.example.todoapp_remake.entity.ItemEntity;
import com.example.todoapp_remake.exception.ItemNotFoundException;
import com.example.todoapp_remake.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemValidateService {


    private final ItemRepository itemRepository;

    public ItemEntity retrieveItemAndValidate(String itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }
}
