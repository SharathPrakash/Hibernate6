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

        animalRepository.save(new Dog("Dog","CAT"));
        animalRepository.save(new Dog("Dog1","CAT1"));
        animalRepository.save(new Dog("Dog2","CAT2"));
        animalRepository.save(new Dog("Dog3","CAT3"));
        animalRepository.save(new Dog("Dog4","CAT4"));
        animalRepository.save(new Dog("Dog5","CAT5"));
        animalRepository.save(new Dog("Dog6"));
        animalRepository.save(new Dog("Dog7",""));
    }

    private static final String NATIVE_SELECT_QUERY = """
             select * from Animal a where (:animals is null or a.name in :animals)
            """;
    private static final String NATIVE_SELECT_QUERY1 = """
             select * from Animal a where (:animals is null or a.name in :animals) and  (:animalc is null or a.name1 in :animalc) 
            """;

    private static final String SELECT_QUERY = """
             select a from Animal a where (:animals is null or a.name in :animals)
            """;

    private static final String SELECT_QUERY_1 = """
             select a from Animal a where (:animalsk is null or a.name in :animals) and (:animalck is null or a.name1 in :animalc)
            """;

    @Test
    void nativeQueryExample() {
        var query1 = entityManager.createNativeQuery(NATIVE_SELECT_QUERY);
        query1.setParameter("animals", List.of("Dog", "Dog1"));
        var dogs = query1.getResultList();
        assertEquals(2L, dogs.size());
    }

    @Test
    void nativeQueryExample1() {
        var query1 = entityManager.createNativeQuery(NATIVE_SELECT_QUERY);
        query1.setParameter("animals", List.of("Dog", ""));
        var dogs = query1.getResultList();
        assertEquals(1L, dogs.size());
    }

    @Test
    void nativeQueryExample2() {
        var query1 = entityManager.createNativeQuery(NATIVE_SELECT_QUERY1);
        query1.setParameter("animals", List.of("Dog"));
        query1.setParameter("animalc", List.of( "CAT"));
        var dogs = query1.getResultList();
        assertEquals(1L, dogs.size());
    }

    @Test
    void nativeQueryExample3() {
        var query1 = entityManager.createNativeQuery(NATIVE_SELECT_QUERY1);
        query1.setParameter("animals", List.of("Dog6"));
        query1.setParameter("animalc", List.of( ""));
        var dogs = query1.getResultList();
        assertEquals(1L, dogs.size());
    }
    @Test
    void nativeQueryExample4() {
        var query1 = entityManager.createNativeQuery(NATIVE_SELECT_QUERY1);
        query1.setParameter("animals", List.of("Dog7"));
        query1.setParameter("animalc", List.of( ""));
        var dogs = query1.getResultList();
        assertEquals(1L, dogs.size());
    }
    @Test
    void nativeQueryExample5() {
        var query1 = entityManager.createNativeQuery(NATIVE_SELECT_QUERY1);
        query1.setParameter("animals", List.of("Dog5"));
        query1.setParameter("animalc", null);
        var dogs = query1.getResultList();
        assertEquals(1L, dogs.size());
    }



    @Test
    void queryExample() {
        var query1 = entityManager.createQuery(SELECT_QUERY);
        query1.setParameter("animals", List.of("Dog", "Dog1"));
        var dogs = query1.getResultList();
        assertEquals(2L, dogs.size());
    }

    @Test
    void queryExample1() {
        var query1 = entityManager.createQuery(SELECT_QUERY_1);
        query1.setParameter("animalsk", List.of("Dog5"));
        query1.setParameter("animals", List.of( "Dog5"));

        query1.setParameter("animalck", null);
        query1.setParameter("animalc", null);

        var dogs = query1.getResultList();
        assertEquals(1L, dogs.size());
    }
}