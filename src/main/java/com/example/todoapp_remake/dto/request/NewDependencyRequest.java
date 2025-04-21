package com.example.todoapp_remake.dto.request;

import lombok.Data;

@Data
public class NewDependencyRequest {

    private String dependentItemId;

    public static void setDependentItemId(String name) {
        // I added this
    }
}
