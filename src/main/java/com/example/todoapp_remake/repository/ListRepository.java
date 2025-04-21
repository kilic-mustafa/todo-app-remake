package com.example.todoapp_remake.repository;

import com.example.todoapp_remake.entity.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ListRepository extends JpaRepository<ListEntity, String> {

    Optional<ListEntity> findByNameAndUserId(String name, String userId);

    List<ListEntity> findAllByUserId(String userId);

    Optional<ListEntity> findByIdAndUserId(String id, String userId);

}
