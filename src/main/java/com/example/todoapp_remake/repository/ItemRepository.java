package com.example.todoapp_remake.repository;

import com.example.todoapp_remake.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, String> {

    Optional<ItemEntity> findByNameAndListId(String name, String listId);

    List<ItemEntity> findByListId(String listId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update item set status = ?1 where id = ?2")
    void updateItemStatusByItemId(boolean status, String itemId);
}
