package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    /**
     * Test to test toString method
     */
    @Test
    void animalAsString() {
        // given
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3, 1));
        WorldMap map = new RectangularMap(5, 5);

        // when
        animal2.move(MoveDirection.LEFT, map);

        // then
        assertEquals("N", animal1.toString());
        assertEquals("W", animal2.toString());
    }

    /**
     * Test to check if animals are at positions
     */
    @Test
    void checkIfAnimalIsAtPosition() {
        // given
        Animal animal1 = new Animal(new Vector2d(3, 1));
        Animal animal2 = new Animal();
        Vector2d position1 = new Vector2d(4, 4);
        Vector2d position2 = new Vector2d(2, 2);

        // then
        assertTrue(animal2.isAt(position2));
        assertFalse(animal1.isAt(position1));
    }

    /**
     * Test for changing animals' direction
     */
    @Test
    void changeAnimalDirection() {
        // given
        Animal animal1 = new Animal();
        Animal animal2 = new Animal();
        WorldMap map1 = new RectangularMap(5, 5);
        WorldMap map2 = new RectangularMap(5, 5);

        // when
        animal1.move(MoveDirection.RIGHT, map1);
        animal2.move(MoveDirection.LEFT, map2);
        animal2.move(MoveDirection.LEFT, map2);

        // then
        assertEquals(MapDirection.EAST, animal1.getAnimalDirection());
        assertEquals(MapDirection.SOUTH, animal2.getAnimalDirection());
    }

    /**
     * Test for moving forward and backward
     */
    @Test
    void goForwardAndBackward() {
        // given
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(4, 3));
        WorldMap map1 = new RectangularMap(5, 5);
        WorldMap map2 = new RectangularMap(5, 5);

        // when
        animal1.move(MoveDirection.FORWARD, map1);
        animal2.move(MoveDirection.RIGHT, map2);
        animal2.move(MoveDirection.RIGHT, map2);
        animal2.move(MoveDirection.BACKWARD, map2);

        // then
        assertEquals(new Vector2d(2, 3), animal1.getAnimalPosition());
        assertEquals(new Vector2d(4, 4), animal2.getAnimalPosition());
    }

    /**
     * Test to check if using all possible MoveDirections animals will be at properly position and direction
     */
    @Test
    void changeDirectionAndMove() {
        // given
        Animal animal1 = new Animal();
        Animal animal2 = new Animal();
        WorldMap map1 = new RectangularMap(5, 5);
        WorldMap map2 = new RectangularMap(5, 5);

        // when
        animal1.move(MoveDirection.RIGHT, map1);
        animal1.move(MoveDirection.FORWARD, map1);
        animal2.move(MoveDirection.LEFT, map2);
        animal2.move(MoveDirection.BACKWARD, map2);
        animal2.move(MoveDirection.LEFT, map2);

        // then
        assertEquals(new Vector2d(3, 2), animal1.getAnimalPosition());
        assertEquals(MapDirection.EAST, animal1.getAnimalDirection());
        assertEquals(new Vector2d(3, 2), animal2.getAnimalPosition());
        assertEquals(MapDirection.SOUTH, animal2.getAnimalDirection());
    }

    /**
     * Test to check if animal won't move out of the map
     */
    @Test
    void animalOutOfMap() {
        // given
        Vector2d downLeft = new Vector2d(0, 0);
        Vector2d upRight = new Vector2d(4, 4);
        Animal animal1 = new Animal(downLeft);
        Animal animal2 = new Animal(downLeft);
        Animal animal3 = new Animal(upRight);
        Animal animal4 = new Animal(upRight);
        WorldMap map1 = new RectangularMap(5, 5);
        WorldMap map2 = new RectangularMap(5, 5);
        WorldMap map3 = new RectangularMap(5, 5);
        WorldMap map4 = new RectangularMap(5, 5);

        // when
        animal1.move(MoveDirection.BACKWARD, map1);
        animal2.move(MoveDirection.RIGHT, map2);
        animal2.move(MoveDirection.BACKWARD, map2);
        animal3.move(MoveDirection.FORWARD, map3);
        animal4.move(MoveDirection.RIGHT, map4);
        animal4.move(MoveDirection.FORWARD, map4);

        // then
        assertEquals(downLeft, animal1.getAnimalPosition());
        assertEquals(downLeft, animal2.getAnimalPosition());
        assertEquals(upRight, animal3.getAnimalPosition());
        assertEquals(upRight, animal4.getAnimalPosition());
    }
}