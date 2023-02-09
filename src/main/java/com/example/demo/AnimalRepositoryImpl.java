package com.example.demo;

import jakarta.persistence.EntityManager;

public class AnimalRepositoryImpl implements AnimalRepositoryCustom {
    private final EntityManager entityManager;

    public AnimalRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long countWithJpa(Class<? extends Animal> animal) {
        var query = entityManager.createQuery("select count(animal) from Animal animal where type(animal) = ?1", Long.class);
        query.setParameter(1, Dog.class);
        return query.getSingleResult();
    }
}