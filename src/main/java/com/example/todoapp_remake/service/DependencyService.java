package com.example.todoapp_remake.service;

import com.example.todoapp_remake.dto.model.DependencyDto;
import com.example.todoapp_remake.dto.request.NewDependencyRequest;
import com.example.todoapp_remake.entity.DependencyEntity;
import com.example.todoapp_remake.entity.ItemEntity;
import com.example.todoapp_remake.exception.CannotCreateDependencyBetweenUncommonItemsException;
import com.example.todoapp_remake.exception.DependencyLoopException;
import com.example.todoapp_remake.exception.DependencyNotFoundException;
import com.example.todoapp_remake.repository.DependencyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class
DependencyService {

    private final ModelMapper modelMapper;
    private final ItemValidateService itemValidateService;
    private final ListValidateService listValidateService;
    private final DependencyRepository dependencyRepository;

    public DependencyDto createDependency(String itemId, NewDependencyRequest newDependencyRequest, String id) {

        ItemEntity item = itemValidateService.retrieveItemAndValidate(itemId);
        ItemEntity dependentItem = itemValidateService.retrieveItemAndValidate(newDependencyRequest.getDependentItemId());

        validateBothItemsAreInTheSameList(item, dependentItem);

        validateInverseDependencyNotExists(itemId, newDependencyRequest);

        DependencyEntity savedDependency = saveNewDependency(itemId, newDependencyRequest);

        return modelMapper.map(savedDependency, DependencyDto.class);
    }

    private DependencyEntity saveNewDependency(String itemId, NewDependencyRequest newDependencyRequest) {
        DependencyEntity dependency = DependencyEntity.builder()
                .itemId(itemId)
                .dependentItemId(newDependencyRequest.getDependentItemId())
                .build();

        return dependencyRepository.save(dependency);
    }

    private void validateInverseDependencyNotExists(String itemId, NewDependencyRequest newDependencyRequest) {
        Optional<DependencyEntity> inverseDependency = dependencyRepository.findByItemIdAndDependentItemId(newDependencyRequest.getDependentItemId(), itemId);

        if(inverseDependency.isPresent()) {
            throw new DependencyLoopException();
        }
    }

    private static void validateBothItemsAreInTheSameList(ItemEntity item, ItemEntity dependentItem) {
        if(!item.isInTheSameListWith(dependentItem)) {
            throw new CannotCreateDependencyBetweenUncommonItemsException();
        }
    }

    public DependencyDto getDependencyById(String dependencyId, String userId) {
        DependencyEntity dependencyEntity = retrieveDependencyAndValidate(dependencyId, userId);

        return modelMapper.map(dependencyEntity, DependencyDto.class);
    }

    private DependencyEntity retrieveDependencyAndValidate(String dependencyId, String userId) {
        DependencyEntity dependencyEntity = dependencyRepository
                .findById(dependencyId)
                .orElseThrow(() -> new DependencyNotFoundException(dependencyId));

        ItemEntity item = itemValidateService.retrieveItemAndValidate(dependencyEntity.getItemId());
        listValidateService.retrieveListAndValidate(item.getListId(), userId);
        return dependencyEntity;
    }

    public List<DependencyEntity> findByItemId(String itemId) {
        return dependencyRepository.findByItemId(itemId);
    }
}
