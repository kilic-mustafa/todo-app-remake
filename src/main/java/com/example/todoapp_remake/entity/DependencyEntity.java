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

@Data
@Table
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DependencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "dependent_item_id")
    private String dependentItemId;

    public DependencyEntity(String itemId, String dependentItemId) {
        this.itemId = itemId;
        this.dependentItemId = dependentItemId;
    }
}
