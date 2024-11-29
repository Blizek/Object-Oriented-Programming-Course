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

        assertDoesNotThrow(() -> map.place(animal1));
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));
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
        assertDoesNotThrow(() -> map.place(animal1));
        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal1, MoveDirection.LEFT);
        map.move(animal1, MoveDirection.BACKWARD);
        assertDoesNotThrow(() -> map.place(animal2));
        map.move(animal2, MoveDirection.RIGHT);
        map.move(animal2, MoveDirection.BACKWARD);
        map.move(animal2, MoveDirection.BACKWARD);

        // then
        assertEquals(new Vector2d(3, 3), animal1.getPosition());
        assertEquals(MapDirection.WEST, animal1.getAnimalDirection());
        assertEquals(new Vector2d(1, 4), animal2.getPosition());
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
        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));

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
        assertDoesNotThrow(() -> map.place(animal));

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
        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));
        assertDoesNotThrow(() -> map.place(animal3));

        // when
        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal3, MoveDirection.FORWARD);
        map.move(animal2, MoveDirection.FORWARD);

        // then
        assertEquals(new Vector2d(3, 4), animal1.getPosition());
        assertEquals(new Vector2d(2, 3), animal2.getPosition());
        assertEquals(new Vector2d(2, 1), animal3.getPosition());
    }

    /**
     * Test to check if all animals move correctly
     */
    @Test
    void checkAnimalsMovement() {
        // given
        RectangularMap map = new RectangularMap(6, 9);
        Animal animal1 = new Animal(new Vector2d(3, 4));
        Animal animal2 = new Animal();
        Animal animal3 = new Animal(new Vector2d(2, 1));
        Animal animal4 = new Animal(new Vector2d(5, 2));
        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));
        assertDoesNotThrow(() -> map.place(animal3));
        assertDoesNotThrow(() -> map.place(animal4));

        // when
        map.move(animal4, MoveDirection.FORWARD);
        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal2, MoveDirection.RIGHT);
        map.move(animal4, MoveDirection.BACKWARD);
        map.move(animal3, MoveDirection.LEFT);
        map.move(animal1, MoveDirection.LEFT);
        map.move(animal3, MoveDirection.RIGHT);
        map.move(animal3, MoveDirection.FORWARD);
        map.move(animal1, MoveDirection.FORWARD);
        map.move(animal4, MoveDirection.BACKWARD);
        map.move(animal2, MoveDirection.BACKWARD);

        // then
        assertEquals(new Vector2d(2, 5), animal1.getPosition());
        assertEquals(MapDirection.WEST, animal1.getAnimalDirection());
        assertEquals(new Vector2d(1, 2), animal2.getPosition());
        assertEquals(MapDirection.EAST, animal2.getAnimalDirection());
        assertEquals(new Vector2d(2, 1), animal3.getPosition());
        assertEquals(MapDirection.NORTH, animal3.getAnimalDirection());
        assertEquals(new Vector2d(5, 1), animal4.getPosition());
        assertEquals(MapDirection.NORTH, animal4.getAnimalDirection());
    }
}