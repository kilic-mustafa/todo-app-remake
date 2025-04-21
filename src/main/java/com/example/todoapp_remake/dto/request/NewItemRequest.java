package com.example.todoapp_remake.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewItemRequest {

    private String name;
    private LocalDateTime deadline;

}
