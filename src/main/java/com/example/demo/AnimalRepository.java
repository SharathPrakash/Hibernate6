package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AnimalRepository extends JpaRepository<Animal, Long>, AnimalRepositoryCustom {
    @Query("select count(animal) from Animal animal where type(animal) = Dog")
    long countDogs();

    @Query("select count(animal) from Animal animal where type(animal) = :animal")
    long count(Class<? extends Animal> animal);
}