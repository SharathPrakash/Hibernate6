package com.example.demo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DOG")
@NoArgsConstructor
public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
}
