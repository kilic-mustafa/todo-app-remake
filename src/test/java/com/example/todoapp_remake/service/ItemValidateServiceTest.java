package com.example.todoapp_remake.service;

import com.example.todoapp_remake.entity.ItemEntity;
import com.example.todoapp_remake.exception.ItemNotFoundException;
import com.example.todoapp_remake.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ItemValidateServiceTest {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemValidateService itemValidateService;


    @BeforeEach
    void setUp() {

        itemRepository = Mockito.mock(ItemRepository.class);

        itemValidateService = new ItemValidateService(itemRepository);
    }
    @Test
    public void whenRetrieveItemAndValidateCalledWithValidRequest_ItShouldReturnItemEntity() {

        String itemId = "1";
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemId);

        Mockito.when(itemRepository.findById(any())).thenReturn(Optional.of(itemEntity));

        ItemEntity result = itemValidateService.retrieveItemAndValidate(itemId);

        assertEquals(itemEntity,result);
        verify(itemRepository).findById(itemId);
    }

    @Test
    public void whenRetrieveItemAndValidateCalledWithInvalidRequest_ItShouldThrowItemNotFoundException() {

        String itemId = "1";

        Mockito.when(itemRepository.findById(eq(itemId))).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,() ->
           itemValidateService.retrieveItemAndValidate(itemId));

        assertEquals("Item not found for id : "+ itemId, exception.getMessage());
        verify(itemRepository).findById(itemId);
    }

}