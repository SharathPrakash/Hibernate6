package com.example.demo;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        animalRepository.deleteAll();

        animalRepository.save(new Dog("Dog"));
        animalRepository.save(new Dog("Dog1"));
        animalRepository.save(new Dog("Dog2"));
        animalRepository.save(new Dog("Dog3"));
        animalRepository.save(new Dog("Dog4"));
        animalRepository.save(new Dog("Dog5"));
    }

    private static final String NATIVE_SELECT_QUERY = """
             select * from Animal a where (:animals is null or a.name in :animals)
            """;

    private static final String SELECT_QUERY = """
             select a from Animal a where (:animals is null or a.name in :animals)
            """;

    @Test
    void nativeQueryExample() {
        var query1 = entityManager.createNativeQuery(NATIVE_SELECT_QUERY);
        query1.setParameter("animals", List.of("Dog", "Dog1"));
        var dogs = query1.getResultList();
        assertEquals(2L, dogs.size());
    }

    @Test
    void queryExample() {
        var query1 = entityManager.createQuery(SELECT_QUERY);
        query1.setParameter("animals", List.of("Dog", "Dog1"));
        var dogs = query1.getResultList();
        assertEquals(2L, dogs.size());
    }
}