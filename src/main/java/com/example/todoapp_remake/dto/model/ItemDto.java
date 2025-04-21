package com.example.todoapp_remake.dto.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDto {

    private String id;
    private String name;
    private Boolean status;
    private LocalDateTime deadline;
    private LocalDateTime createDate;
    private String listId;
}
