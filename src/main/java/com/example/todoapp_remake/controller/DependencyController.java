package com.example.todoapp_remake.controller;

import com.example.todoapp_remake.dto.model.DependencyDto;
import com.example.todoapp_remake.dto.request.NewDependencyRequest;
import com.example.todoapp_remake.entity.UserEntity;
import com.example.todoapp_remake.service.DependencyService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("dependencies")
public class DependencyController {

    private final DependencyService dependencyService;

    @PostMapping("{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    public DependencyDto createDependency(@PathVariable String itemId, @RequestBody NewDependencyRequest newDependencyRequest, UriComponentsBuilder ucb, HttpServletResponse response) {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DependencyDto dependencyDto = dependencyService.createDependency(itemId, newDependencyRequest, userEntity.getId());

        URI locationOfNewDependency = ucb
                .path("/dependencies/{dependencyId}")
                .buildAndExpand(dependencyDto.getId())
                .toUri();

        response.setHeader("Location", locationOfNewDependency.toString());
        return dependencyDto;
    }

    @GetMapping("{dependencyId}")
    @ResponseStatus(HttpStatus.OK)
    public DependencyDto getDependencyById(@PathVariable String dependencyId) {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return dependencyService.getDependencyById(dependencyId, userEntity.getId());
    }
}
