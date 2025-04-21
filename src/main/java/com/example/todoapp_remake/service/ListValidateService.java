package com.example.todoapp_remake.service;

import com.example.todoapp_remake.entity.ListEntity;
import com.example.todoapp_remake.exception.ListNotFoundException;
import com.example.todoapp_remake.repository.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListValidateService {


    private ListRepository listRepository;

    public ListEntity retrieveListAndValidate(String listId, String userId) {
        return listRepository.findByIdAndUserId(listId, userId)
                .orElseThrow(() -> new ListNotFoundException(listId));
    }
}
