package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

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


    public static final String SELECT_QUERY = """
             select a from Animal a where 
            (:animal is null or a.name in :animals)
            """;

    public static final String SELECT_QUERY_ONE_PARAM = """
             select a from Animal a where 
            (:animal is null or a.name in :animal)
            """;

    @Test
    void countDogsShouldFindOneDog() {
        var dogs = animalRepository.countDogs();
        assertEquals(6, dogs);
        dogs = animalRepository.countDogs();
        assertEquals(6, dogs);
    }

    @Test
    void countDogsWithListOf1DogIsSetAsParam() {
        var query1 = entityManager.createQuery(SELECT_QUERY_ONE_PARAM);
        query1.setParameter("animal", List.of("Dog"));
        var dogs = query1.getResultList();
        assertEquals(1L, dogs.size());
    }

    @Test
    void countDogsWithListOf2DogIsSetIn1Param() {
        var query1 = entityManager.createQuery(SELECT_QUERY_ONE_PARAM);
        query1.setParameter("animal", List.of("Dog", "Dog1"));
        var dogs = query1.getResultList();
        assertEquals(2L, dogs.size());
    }

    @Test
    void countDogsWithListOf2DogIsSetIn2Param() {
        var query1 = entityManager.createQuery(SELECT_QUERY);
        query1.setParameter("animals", List.of("Dog", "Dog1"));
        query1.setParameter("animal", List.of("Dog", "Dog1"));
        var dogs = query1.getResultList();
        assertEquals(2L, dogs.size());
    }

    @Test
    void countDogsWithListThorwAssertError() {

        List<String> sample = Arrays.asList("Dog", "Dog1", "Dog3");

        var query1 = entityManager.createQuery(SELECT_QUERY);
        query1.setParameter("animal", sample);
        var dogs = query1.getResultList();
        assertEquals(3L, dogs.size());
    }
}