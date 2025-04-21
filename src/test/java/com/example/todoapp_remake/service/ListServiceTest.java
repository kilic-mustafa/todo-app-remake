package com.example.todoapp_remake.service;

import com.example.todoapp_remake.dto.model.ListDto;
import com.example.todoapp_remake.dto.request.NewListRequest;
import com.example.todoapp_remake.entity.ListEntity;
import com.example.todoapp_remake.exception.ListAlreadyExistsException;
import com.example.todoapp_remake.repository.ListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ListRepository listRepository;

    @Mock
    private ListValidateService listValidateService;

    @InjectMocks
    private ListService listService;


    @Test
    public void whenGetListByIdAndUserIdCalledWithValidRequest_ItShouldReturnListDto() {

        ListEntity listEntity = ListEntity
                .builder()
                .id("1")
                .name("Test List")
                .userId("1")
                .build();

        ListDto listDto = new ListDto();
        listDto.setId("1");
        listDto.setName("Test List");

        Mockito.when(listValidateService.retrieveListAndValidate(eq("1"), eq("1"))).thenReturn(listEntity);
        Mockito.when(modelMapper.map(any(ListEntity.class), eq(ListDto.class))).thenReturn(listDto);

        ListDto result = listService.getListByIdAndUserId("1", "1");

        assertEquals(listDto, result);
        verify(listValidateService).retrieveListAndValidate("1", "1");
        verify(modelMapper).map(listEntity, ListDto.class);
    }

    @Test
    public void whenAddNewListCalledWithValidRequest_ItReturnValidListDto() {

        NewListRequest newListRequest = new NewListRequest();
        newListRequest.setName("Test List");

        ListEntity listEntity = ListEntity
                .builder()
                .id("1")
                .name("Test List")
                .userId("1")
                .build();

        ListDto listDto = new ListDto();
        listDto.setId("1");
        listDto.setName("Test List");

        Mockito.when(listRepository.findByNameAndUserId(any(), any())).thenReturn(Optional.empty());
        Mockito.when(listRepository.save(any(ListEntity.class))).thenReturn(listEntity);
        Mockito.when(modelMapper.map(any(ListEntity.class), eq(ListDto.class))).thenReturn(listDto);

        ListDto result = listService.addNewList(newListRequest, "1");

        assertNotNull(result);
        assertEquals(listDto, result);
        verify(listRepository).findByNameAndUserId("Test List", "1");
        verify(listRepository, times(1)).save(any(ListEntity.class));
        verify(modelMapper).map(listEntity, ListDto.class);
    }

    @Test
    public void whenUpdateListCalledWithValidRequest_ItReturnValidateListNotExists() {

        NewListRequest newListRequest = new NewListRequest();
        newListRequest.setName("Test List");

        ListEntity listEntity = ListEntity
                .builder()
                .id("1")
                .name("Test List")
                .userId("1")
                .build();

        ListDto listDto = new ListDto();
        listDto.setId("1");
        listDto.setName("Test List");

        Mockito.when(listRepository.findByNameAndUserId(any(), any())).thenReturn(Optional.of(listEntity));

        assertThrows(ListAlreadyExistsException.class, () ->
                listService.addNewList(newListRequest, "1"));

        verify(listRepository).findByNameAndUserId("Test List", "1");
        verify(listRepository, never()).save(any(ListEntity.class));
        verify(modelMapper, never()).map(any(ListEntity.class), eq(ListDto.class));
    }

    @Test
    public void whenGetAllListsCalledWithValidRequest_ItShouldReturnListOfListDto() {

        List<ListEntity> listEntityList = new ArrayList<>();

        ListEntity listEntity = ListEntity
                .builder()
                .id("1")
                .name("Test List")
                .userId("1")
                .build();
        listEntityList.add(listEntity);

        ListDto listDto = new ListDto();
        listDto.setId("1");
        listDto.setName("Test List");

        Mockito.when(listRepository.findAllByUserId(any())).thenReturn(listEntityList);
        Mockito.when(modelMapper.map(any(ListEntity.class), eq(ListDto.class))).thenReturn(listDto);

        List<ListDto> result = listService.getAllLists("1");

        assertEquals(1, result.size());
        assertEquals("Test List", result.get(0).getName());
        verify(listRepository).findAllByUserId("1");
        verify(modelMapper).map(listEntity, ListDto.class);
    }

    @Test
    public void whenDeleteListCalledWithValidRequest_ItShouldDeleteList() {

        NewListRequest newListRequest = new NewListRequest();
        newListRequest.setName("Test List");

        ListEntity listEntity = ListEntity
                .builder()
                .id("1")
                .name("Test List")
                .userId("1")
                .build();

        Mockito.when(listValidateService.retrieveListAndValidate(eq("1"),eq("1"))).thenReturn(listEntity);
        doNothing().when(listRepository).deleteById(any());

        listService.deleteList("1","1");

        verify(listRepository, times(1)).deleteById(any());
    }

}
