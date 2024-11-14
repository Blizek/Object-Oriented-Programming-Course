package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {
    /**
     * Test to cover case when want to set two animals on one position at the start of the simulation
     */
    @Test
    void checkSetTwoAnimalsOnOnePosition() {
        // given
        RectangularMap map = new RectangularMap(6, 10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal();

        // when
        boolean isAnimal1Set = map.place(animal1);
        boolean isAnimal2Set = map.place(animal2);

        // then
        assertTrue(isAnimal1Set);
        assertFalse(isAnimal2Set);
    }

    /**
     * Test to check if method 'move' works properly
     */
    @Test
    void moveAnimalsOnMap() {
        // given
        RectangularMap map = new RectangularMap(15, 5);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3, 4));

        // when
        map.place(animal1);
        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal1, MoveDirection.LEFT);
        map.move(animal1, MoveDirection.BACKWARD);
        map.place(animal2);
        map.move(animal2, MoveDirection.RIGHT);
        map.move(animal2, MoveDirection.BACKWARD);
        map.move(animal2, MoveDirection.BACKWARD);

        // then
        assertEquals(new Vector2d(3, 3), animal1.getAnimalPosition());
        assertEquals(MapDirection.WEST, animal1.getAnimalDirection());
        assertEquals(new Vector2d(1, 4), animal2.getAnimalPosition());
        assertEquals(MapDirection.EAST, animal2.getAnimalDirection());
    }

    /**
     * Test to check method 'isOccupied' works properly
     */
    @Test
    void checkIfPositionIsOccupied() {
        // given
        RectangularMap map = new RectangularMap(5, 15);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3, 4));
        map.place(animal1);
        map.place(animal2);

        // then
        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(3, 4)));
        assertFalse(map.isOccupied(new Vector2d(2, 0)));
    }

    /**
     * Test to check if method 'objectAt' works properly and returns correct Animal object on requested position
     */
    @Test
    void checkObjectOnPosition() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal();
        map.place(animal);

        // then
        assertNull(map.objectAt(new Vector2d(2, 3)));
        assertEquals(animal, map.objectAt(new Vector2d(2, 2)));
    }

    /**
     * Test to check if method 'canMove' works properly (this method is used by the 'move' method so to test it check
     * if animal moves correctly (method 'move' was tested earlier) (method 'canMoveTo' blocks moving to illegal positions))
     */
    @Test
    void checkIfAnimalCanMove() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(3, 4));
        Animal animal2 = new Animal();
        Animal animal3 = new Animal(new Vector2d(2, 1));
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        // when
        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal3, MoveDirection.FORWARD);
        map.move(animal2, MoveDirection.FORWARD);

        // then
        assertEquals(new Vector2d(3, 4), animal1.getAnimalPosition());
        assertEquals(new Vector2d(2, 3), animal2.getAnimalPosition());
        assertEquals(new Vector2d(2, 1), animal3.getAnimalPosition());
    }
}