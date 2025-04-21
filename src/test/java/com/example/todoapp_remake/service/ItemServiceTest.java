package com.example.todoapp_remake.service;

import com.example.todoapp_remake.dto.model.ItemDto;
import com.example.todoapp_remake.dto.request.ChangeItemStatusRequest;
import com.example.todoapp_remake.dto.request.NewItemRequest;
import com.example.todoapp_remake.entity.DependencyEntity;
import com.example.todoapp_remake.entity.ItemEntity;
import com.example.todoapp_remake.exception.CannotMarkItemCompletedBeforeTheDependentItemException;
import com.example.todoapp_remake.exception.ItemAlreadyExistException;
import com.example.todoapp_remake.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemValidateService itemValidateService;

    @Mock
    private ListValidateService listValidateService;

    @Mock
    private DependencyService dependencyService;

    @InjectMocks
    private ItemService itemService;


    @Test
    public void whenAddNewItemCalledWithValidRequest_ItShouldReturnValidItemDto() {

        NewItemRequest newItemRequest = new NewItemRequest();
        newItemRequest.setName("Test Item");
        newItemRequest.setDeadline(LocalDateTime.parse("2024-12-31T00:00:00"));

        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("1")
                .name("Test Item")
                .deadline(newItemRequest.getDeadline())
                .status(false)
                .listId("1")
                .build();

        ItemDto itemDto = new ItemDto();
        itemDto.setId("1");
        itemDto.setName("Test Item");
        itemDto.setDeadline(newItemRequest.getDeadline());
        itemDto.setStatus(false);

        Mockito.when(itemRepository.findByNameAndListId(any(),any())).thenReturn(Optional.empty());
        Mockito.when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);
        Mockito.when(modelMapper.map(any(ItemEntity.class), eq(ItemDto.class))).thenReturn(itemDto);

        ItemDto result =itemService.addNewItem(newItemRequest,"1","1");

        assertNotNull(result);
        assertEquals("Test Item", result.getName());
        verify(itemRepository, times(1)).save(any(ItemEntity.class));
    }

    @Test
    public void whenAddNewItemCalledWithInvalidRequest_ItShouldReturnThrowItemAlreadyExistException() {

        NewItemRequest newItemRequest = new NewItemRequest();
        newItemRequest.setName("Test Item");
        newItemRequest.setDeadline(LocalDateTime.parse("2024-12-31T00:00:00"));

        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("1")
                .name("Test Item")
                .deadline(LocalDateTime.parse("2024-12-31T00:00:00"))
                .status(false)
                .listId("1")
                .build();

        Mockito.when(itemRepository.findByNameAndListId(any(), any())).thenReturn(Optional.of(itemEntity));

        assertThrows(ItemAlreadyExistException.class,() ->
                itemService.addNewItem(newItemRequest,"1","1"));

        verify(itemRepository, never()).save(any(ItemEntity.class));
        verify(modelMapper, never()).map(any(ItemEntity.class), eq(ItemDto.class));
    }

    @Test
    public void whenGetAllItemsCalledWithValidRequest_ShouldReturnListOfItems() {

        List<ItemEntity> itemEntities = new ArrayList<>();

        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("1")
                .name("Test Item")
                .deadline(LocalDateTime.parse("2024-12-31T00:00:00"))
                .status(false)
                .listId("1")
                .build();

        itemEntities.add(itemEntity);

        ItemDto itemDto = new ItemDto();
        itemDto.setId("1");
        itemDto.setName("Test Item");
        itemDto.setDeadline(LocalDateTime.parse("2024-12-31T00:00:00"));
        itemDto.setStatus(false);

        Mockito.when(itemRepository.findByListId(any())).thenReturn(itemEntities);
        Mockito.when(modelMapper.map(any(ItemEntity.class), eq(ItemDto.class))).thenReturn(itemDto);

        List<ItemDto> result = itemService.getAllItems("1","1");

        assertNotNull(result);   // even if it doesn't happen
        assertEquals(1, result.size());
        assertEquals("Test Item", result.get(0).getName());
    }

    @Test
    public void whenGetItemByIdCalledWithInvalidRequest_ItShouldReturnItem() {

        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("1")
                .name("Test Item")
                .deadline(LocalDateTime.parse("2024-12-31T00:00:00"))
                .status(false)
                .listId("1")
                .build();

        ItemDto itemDto = new ItemDto();
        itemDto.setId("1");
        itemDto.setName("Test Item");
        itemDto.setDeadline(LocalDateTime.parse("2024-12-31T00:00:00"));
        itemDto.setStatus(false);

        Mockito.when(itemValidateService.retrieveItemAndValidate(any())).thenReturn(itemEntity);
        Mockito.when(modelMapper.map(any(ItemEntity.class),eq(ItemDto.class))).thenReturn(itemDto);

        ItemDto result = itemService.getItemById("1");

        assertNotNull(result); // even if it doesn't happen
        assertEquals("Test Item", result.getName());
    }

    @Test
    public void whenDeleteItemByIdCalledWithInvalidRequest_ItShouldDeleteItem(){

        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("1")
                .name("Test Item")
                .deadline(LocalDateTime.parse("2024-12-31T00:00:00"))
                .status(false)
                .listId("1")
                .build();

        Mockito.when(itemValidateService.retrieveItemAndValidate(any())).thenReturn(itemEntity);
        doNothing().when(itemRepository).deleteById(any());

        itemService.deleteItemById("1","1");  // a (void) result does not freeze

        verify(itemRepository, times(1)).deleteById(any());
    }

    @Test
    public void whenChangeStatusCalledWithInvalidRequest_ShouldChangeStatusSuccessfully(){

        ChangeItemStatusRequest changeStatusRequest = new ChangeItemStatusRequest();
        changeStatusRequest.setStatus(true);

        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("1")
                .name("Test Item")
                .deadline(LocalDateTime.parse("2024-12-31T00:00:00"))
                .status(false)
                .listId("1")
                .build();

        Mockito.when(itemValidateService.retrieveItemAndValidate(any())).thenReturn(itemEntity);
        doNothing().when(itemRepository).updateItemStatusByItemId(anyBoolean(), any());

        itemService.changeStatus("1",changeStatusRequest, "1");

        verify(itemRepository,times(1)).updateItemStatusByItemId(anyBoolean(),any());
    }

    @Test
    public void whenChangeStatusCalledWithInvalidRequest_ShouldThrowCannotMarkItemCompletedBeforeTheDependentItemException(){

        ChangeItemStatusRequest changeStatusRequest = new ChangeItemStatusRequest();
        changeStatusRequest.setStatus(true);

        ItemEntity itemEntity = ItemEntity
                .builder()
                .id("1")
                .name("Test Item")
                .deadline(LocalDateTime.parse("2024-12-31T00:00:00"))
                .status(false)
                .listId("1")
                .build();

        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setDependentItemId("2");

        List<DependencyEntity> dependencyEntities = new ArrayList<>();
        dependencyEntities.add(dependencyEntity);

        Mockito.when(itemValidateService.retrieveItemAndValidate(any())).thenReturn(itemEntity);
        Mockito.when(dependencyService.findByItemId(any())).thenReturn(dependencyEntities);
        Mockito.when(itemRepository.findById(any())).thenReturn(Optional.of(itemEntity));

        assertThrows(CannotMarkItemCompletedBeforeTheDependentItemException.class,() ->
                itemService.changeStatus("1",changeStatusRequest,"1"));

        verify(itemRepository, never()).updateItemStatusByItemId(anyBoolean(),any());
    }

}

