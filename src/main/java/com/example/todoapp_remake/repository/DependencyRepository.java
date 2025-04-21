package com.example.todoapp_remake.repository;

import com.example.todoapp_remake.entity.DependencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DependencyRepository extends JpaRepository<DependencyEntity, String> {

    List<DependencyEntity> findByItemId(String itemId);

    Optional<DependencyEntity> findByItemIdAndDependentItemId(String itemId, String dependentItemId);

}
