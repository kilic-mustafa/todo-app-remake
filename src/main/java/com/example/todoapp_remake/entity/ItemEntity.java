package com.example.todoapp_remake.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class ItemEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "list_id")
    private String listId;

    public ItemEntity(String name, Boolean status, LocalDateTime deadline, LocalDateTime createDate, String listId) {
        this.name = name;
        this.status = status;
        this.deadline = deadline;
        this.createDate = createDate;
        this.listId = listId;
    }

    public boolean isNotMarked() {
        return !this.status;
    }

    public boolean isInTheSameListWith(ItemEntity otherItem) {
        return this.getListId().equals(otherItem.getListId());
    }
}
