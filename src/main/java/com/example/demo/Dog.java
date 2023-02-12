package com.example.demo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DOG")
@NoArgsConstructor
public class Dog extends Animal {
    public Dog(final String name) {
        super(name);
    }

    public Dog(final String name, final String name1) {
        super(name, name1);
    }
}
