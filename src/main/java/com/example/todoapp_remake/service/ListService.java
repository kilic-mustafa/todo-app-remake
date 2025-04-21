package com.example.todoapp_remake.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.todoapp_remake.entity.ListEntity;
import com.example.todoapp_remake.dto.model.ListDto;
import com.example.todoapp_remake.repository.ListRepository;
import com.example.todoapp_remake.dto.request.NewListRequest;
import com.example.todoapp_remake.exception.ListAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class ListService {

    private final ModelMapper modelMapper;
    private final ListRepository listRepository;
    private final ListValidateService listValidateService;

    public ListDto getListByIdAndUserId(String listId, String userId) {
        ListEntity list = listValidateService.retrieveListAndValidate(listId, userId);
        return modelMapper.map(list, ListDto.class);
    }

    public ListDto addNewList(NewListRequest newListRequest, String userId) {
        validateListNotExists(newListRequest, userId);

        ListEntity listEntity = ListEntity
                .builder()
                .name(newListRequest.getName())
                .userId(userId)
                .build();

        ListEntity savedList = listRepository.save(listEntity);

        return modelMapper.map(savedList, ListDto.class);
    }

    private void validateListNotExists(NewListRequest newListRequest, String userId) {
        Optional<ListEntity> optionalList = listRepository.findByNameAndUserId(newListRequest.getName(), userId);

        if(optionalList.isPresent()) {
            throw new ListAlreadyExistsException(newListRequest.getName());
        }
    }

    public List<ListDto> getAllLists(String userId) {
        List<ListEntity> allLists = listRepository.findAllByUserId(userId);

        return allLists.stream()
                .map(listEntity -> modelMapper.map(listEntity, ListDto.class))
                .toList();
    }

    public void deleteList(String listId, String userId) {
        listValidateService.retrieveListAndValidate(listId, userId);

        listRepository.deleteById(listId);
    }
}
