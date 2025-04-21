package com.example.todoapp_remake.service;

import com.example.todoapp_remake.dto.model.ItemDto;
import com.example.todoapp_remake.dto.request.ChangeItemStatusRequest;
import com.example.todoapp_remake.dto.request.NewItemRequest;
import com.example.todoapp_remake.entity.DependencyEntity;
import com.example.todoapp_remake.entity.ItemEntity;
import com.example.todoapp_remake.exception.CannotMarkItemCompletedBeforeTheDependentItemException;
import com.example.todoapp_remake.exception.ItemAlreadyExistException;
import com.example.todoapp_remake.exception.ItemNotFoundException;
import com.example.todoapp_remake.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final ListValidateService listValidateService;
    private final ItemValidateService itemValidateService;
    private final DependencyService dependencyService;
    
    public ItemDto addNewItem(NewItemRequest newItemRequest, String listId, String userId) {
        listValidateService.retrieveListAndValidate(listId, userId);

        validateItemNotExists(newItemRequest, listId);

        ItemEntity itemEntity = ItemEntity
                .builder()
                .name(newItemRequest.getName())
                .deadline(newItemRequest.getDeadline())
                .status(false)
                .listId(listId)
                .build();

        ItemEntity savedItemEntity = itemRepository.save(itemEntity);

        return modelMapper.map(savedItemEntity, ItemDto.class);
    }

    private void validateItemNotExists(NewItemRequest newItemRequest, String listId) {
        Optional<ItemEntity> optionalItem = itemRepository.findByNameAndListId(newItemRequest.getName(), listId);

        if(optionalItem.isPresent()) {
            throw new ItemAlreadyExistException(newItemRequest.getName());
        }
    }

    public List<ItemDto> getAllItems(String listId, String userId) {
        listValidateService.retrieveListAndValidate(listId, userId);

        List<ItemEntity> allItems = itemRepository.findByListId(listId);

        return allItems.stream().map(itemEntity -> modelMapper.map(itemEntity, ItemDto.class)).toList();
    }

    public ItemDto getItemById(String itemId) {
        ItemEntity item = itemValidateService.retrieveItemAndValidate(itemId);
        return modelMapper.map(item, ItemDto.class);
    }



    public void deleteItemById(String itemId, String userId) {
        ItemEntity item = itemValidateService.retrieveItemAndValidate(itemId);

        listValidateService.retrieveListAndValidate(item.getListId(), userId);

        itemRepository.deleteById(itemId);
    }

    public void changeStatus(String itemId, ChangeItemStatusRequest changeStatusRequest, String userId) {
        ItemEntity item = itemValidateService.retrieveItemAndValidate(itemId);

        listValidateService.retrieveListAndValidate(item.getListId(), userId);

        if (changeStatusRequest.isMarkRequest()) {
            validateAllDependenciesAreMarked(itemId);
        }

        itemRepository.updateItemStatusByItemId(changeStatusRequest.getStatus(), itemId);
    }

    private void validateAllDependenciesAreMarked(String itemId) {
        List<DependencyEntity> dependencies = dependencyService.findByItemId(itemId);

        for (DependencyEntity d : dependencies) {
            validateDependentItemIsMarked(d);
        }
    }

    private void validateDependentItemIsMarked(DependencyEntity d) {
        ItemEntity dependentItem = itemRepository.findById(d.getDependentItemId())
                .orElseThrow(() -> new ItemNotFoundException(d.getItemId()));

        if (dependentItem.isNotMarked()) {
            throw new CannotMarkItemCompletedBeforeTheDependentItemException();
        }
    }

}