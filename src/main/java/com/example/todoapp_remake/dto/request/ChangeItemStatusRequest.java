package com.example.todoapp_remake.dto.request;

import lombok.Data;

@Data
public class ChangeItemStatusRequest {

    private Boolean status;

    public boolean isMarkRequest() {
        return status;
    }

    public boolean isUnmarkRequest() {
        return !status;
    }
}
