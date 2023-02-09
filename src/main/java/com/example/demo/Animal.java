package com.example.demo;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
public abstract class Animal {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Animal(String name) {
        this.name = name;
    }
}
