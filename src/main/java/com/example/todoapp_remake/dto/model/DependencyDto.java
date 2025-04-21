package com.example.todoapp_remake.dto.model;

import lombok.Data;

@Data
public class DependencyDto {

    private String id;
    private String itemId;
    private String dependentItemId;
}
