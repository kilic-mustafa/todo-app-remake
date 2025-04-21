package com.example.todoapp_remake.controller;

import com.example.todoapp_remake.dto.model.ListDto;
import com.example.todoapp_remake.dto.request.NewListRequest;
import com.example.todoapp_remake.entity.UserEntity;
import com.example.todoapp_remake.service.ListService;
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
@RequestMapping("lists")
public class ListController {

    private final ListService listService;

    @GetMapping("{listId}")
    @ResponseStatus(HttpStatus.OK)
    public ListDto getListById(@PathVariable String listId) {
        UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return listService.getListByIdAndUserId(listId, principal.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListDto addNewList(@RequestBody NewListRequest newListRequest, UriComponentsBuilder ucb, HttpServletResponse response) {
        UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ListDto listDto = listService.addNewList(newListRequest, principal.getId());

        URI locationOfNewList = ucb
                .path("/lists/{listId}")
                .buildAndExpand(listDto.getId())
                .toUri();

        response.setHeader("Location", locationOfNewList.toString());

        return listDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ListDto> getAllLists() {
        UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return listService.getAllLists(principal.getId());

    }

    @DeleteMapping("{listId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteList(@PathVariable String listId) {
        UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        listService.deleteList(listId, principal.getId());
    }

}
