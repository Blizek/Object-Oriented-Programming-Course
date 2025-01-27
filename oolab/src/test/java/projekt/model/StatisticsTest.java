package projekt.model;

import org.junit.jupiter.api.Test;
import projekt.model.maps.AbstractMap;
import projekt.model.maps.EarthMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {
    @Test
    void testGetAnimalsCounter() {
        // given
        List<Animal> animalList1 = new ArrayList<>();
        List<Animal> animalList2 = new ArrayList<>();
        animalList1.add(new Animal(new Vector2d(2, 4), 30, List.of(1, 3, 4, 6), null, null, 10));
        animalList2.add(new Animal(new Vector2d(1, 6), 10, List.of(4, 6, 1, 7, 7), null, null, 1));
        animalList1.add(new Animal(new Vector2d(10, 3), 20, List.of(4, 0, 6, 1, 0, 3), null, null, 50));
        animalList1.add(new Animal(new Vector2d(2, 9), 10, List.of(6, 3, 2, 7, 3), null, null, 11));
        animalList2.add(new Animal(new Vector2d(5, 10), 15, List.of(1, 1, 1, 1, 6), null, null, 26));
        animalList2.add(new Animal(new Vector2d(5, 8), 90, List.of(1, 0), null, null, 52));
        animalList2.add(new Animal(new Vector2d(8, 1), 40, List.of(4, 8, 7, 2, 4, 6, 0, 1), null, null, 4));
        animalList2.add(new Animal(new Vector2d(3, 7), 77, List.of(4, 5, 2, 7, 1), null, null, 8));

        // then
        assertEquals(3, Statistics.getAllAnimalsCount(animalList1));
        assertEquals(5, Statistics.getAllAnimalsCount(animalList2));
    }

    @Test
    void testGetAllGrassesCount() {
        // given
        HashMap<Vector2d, Grass> grasses = new HashMap<>();
        grasses.put(new Vector2d(1, 1), new Grass(new Vector2d(1, 1), 20));
        grasses.put(new Vector2d(2, 2), new Grass(new Vector2d(2, 2), 30));
        grasses.put(new Vector2d(7, 8), new Grass(new Vector2d(7, 8), 10));
        grasses.put(new Vector2d(7, 3), new Grass(new Vector2d(7, 3), 45));

        // then
        assertEquals(4, Statistics.getAllGrassesCount(grasses));
    }

    @Test
    void testGetMostPopularGenome() {
        // given
        List<Animal> animals1 = new ArrayList<>();
        animals1.add(new Animal(new Vector2d(1, 1), 10, List.of(1, 2, 3, 7, 3, 4, 6), null, null, 5));
        animals1.add(new Animal(new Vector2d(2, 2), 20, List.of(0, 2, 3, 0, 0, 5, 3), null, null, 10));
        animals1.add(new Animal(new Vector2d(3, 3), 30, List.of(4, 5, 6, 2, 5, 5, 1), null, null, 15));
        List<Animal> animals2 = new ArrayList<>();
        animals2.add(new Animal(new Vector2d(1, 1), 10, List.of(4, 4, 6, 2, 0), null, null, 5));
        animals2.add(new Animal(new Vector2d(2, 2), 20, List.of(7, 4, 5, 4, 7), null, null, 10));
        animals2.add(new Animal(new Vector2d(3, 3), 30, List.of(7, 7, 2, 1, 4), null, null, 15));

        // then
        assertEquals(List.of(3, 5), Statistics.getMostPopularGenome(animals1));
        assertEquals(List.of(4), Statistics.getMostPopularGenome(animals2));
    }

    @Test
    void testGetAverageEnergy() {
        // given
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(new Vector2d(1, 1), 10, List.of(1, 2, 3, 7, 3, 4, 6), null, null, 5));
        animals.add(new Animal(new Vector2d(2, 2), 20, List.of(0, 2, 3, 0, 0, 5, 3), null, null, 10));
        animals.add(new Animal(new Vector2d(3, 3), 30, List.of(4, 5, 6, 2, 5, 5, 1), null, null, 15));

        // then
        assertEquals(20.0, Statistics.getAverageEnergy(animals));
    }

    @Test
    void testGetAverageDaysLived() {
        // given
        Animal animal1 = new Animal(new Vector2d(1, 1), 10, List.of(1, 2, 3, 7, 3, 4, 6), null, null, 5);
        Animal animal2 = new Animal(new Vector2d(2, 2), 20, List.of(0, 2, 3, 0, 0, 5, 3), null, null, 10);
        Animal animal3 = new Animal(new Vector2d(3, 3), 30, List.of(4, 5, 6, 2, 5, 5, 1), null, null, 15);
        List<Animal> animals = List.of(animal1, animal2, animal3);
        Vector2d topRightCorner = new Vector2d(5, 5);

        // when
        for (int i = 0; i < 3; i++) {
            animal1.move(topRightCorner);
        }
        for (int i = 0; i < 5; i++) {
            animal2.move(topRightCorner);
        }
        for (int i = 0; i < 9; i++) {
            animal3.move(topRightCorner);
        }

        // then
        assertEquals(5.67f, Statistics.getAverageDaysLived(animals));
    }

    @Test
    void testGetAverageChildrenCount() {
        // given
        Animal animal1 = new Animal(new Vector2d(1, 1), 10, List.of(1, 2, 3, 7, 3, 4, 6), null, null, 5);
        Animal animal2 = new Animal(new Vector2d(2, 2), 20, List.of(0, 2, 3, 0, 0, 5, 3), null, null, 10);
        Animal animal3 = new Animal(new Vector2d(3, 3), 30, List.of(4, 5, 6, 2, 5, 5, 1), null, null, 15);
        List<Animal> animals = List.of(animal1, animal2, animal3);

        // when
        Vector2d position = new Vector2d(3, 3);
        for (int i = 0; i < 4; i++) {
            animal1.setChildren(new Animal(position, 20, List.of(2, 5, 6, 2, 7), null, null, 50));
        }
        position = new Vector2d(2, 2);
        for (int i = 0; i < 12; i++) {
            animal2.setChildren(new Animal(position, 20, List.of(2, 5, 6, 2, 7), null, null, 50));
        }
        position = new Vector2d(5, 5);
        for (int i = 0; i < 2; i++) {
            animal3.setChildren(new Animal(position, 20, List.of(2, 5, 6, 2, 7), null, null, 50));
        }

        // then
        assertEquals(6.0f, Statistics.getAverageChildrenCount(animals));
    }
}