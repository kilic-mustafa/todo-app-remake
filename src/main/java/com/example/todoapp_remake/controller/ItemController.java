package com.example.todoapp_remake.controller;

import com.example.todoapp_remake.dto.model.ItemDto;
import com.example.todoapp_remake.dto.request.ChangeItemStatusRequest;
import com.example.todoapp_remake.dto.request.NewItemRequest;
import com.example.todoapp_remake.entity.UserEntity;
import com.example.todoapp_remake.service.ItemService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("{listId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addNewItem(@RequestBody NewItemRequest newItemRequest, @PathVariable String listId, UriComponentsBuilder ucb, HttpServletResponse response) {
        UserEntity user = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ItemDto itemDto = itemService.addNewItem(newItemRequest, listId, user.getId());

        URI locationOfNewList = ucb
                .path("/items/{listId}")
                .buildAndExpand(itemDto.getListId())
                .toUri();

        response.setHeader("location", locationOfNewList.toString());
        return itemDto;
    }

    @GetMapping("{listId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getAllItems(@PathVariable String listId) {
        UserEntity user = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return itemService.getAllItems(listId, user.getId());
    }

    @GetMapping("get/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto getItemById(@PathVariable String itemId) {
        return itemService.getItemById(itemId);
    }

    @DeleteMapping("{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemById(@PathVariable String itemId) {
        UserEntity user = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemService.deleteItemById(itemId, user.getId());
    }

    @PatchMapping("{itemId}")
    public void changeStatus(@PathVariable String itemId, @RequestBody ChangeItemStatusRequest changeStatusRequest) {
        UserEntity user = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemService.changeStatus(itemId, changeStatusRequest, user.getId());
    }

}
