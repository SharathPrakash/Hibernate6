package com.example.demo;

public interface AnimalRepositoryCustom {
    Long countWithJpa(Class<? extends Animal> animal);
}