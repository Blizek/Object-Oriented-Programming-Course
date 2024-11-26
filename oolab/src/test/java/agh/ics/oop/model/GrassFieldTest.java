package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {
    /**
     * Test to check if amount of grass on the field is correct
     */
    @Test
    void checkGrassAmount() {
        // given
        GrassField field = new GrassField(6);
        
        // then
        assertEquals(6, field.getGrassAmount());
    }
    
    /**
     * Test to cover case when want to set two animals on one position at the start of the simulation
     */
    @Test
    void checkSetTwoAnimalsOnOnePosition() {
        // given
        GrassField field = new GrassField(10);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal();

        try {
            // when
            boolean isAnimal1Set = field.place(animal1);

            // then
            assertTrue(isAnimal1Set);
        } catch (IncorrectPositionException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        assertThrows(IncorrectPositionException.class, () -> field.place(animal2));
        assertEquals(10, field.getGrassAmount());
    }

    /**
     * Test to check if method 'move' works properly
     */
    @Test
    void moveAnimalsOnMap() {
        // given
        GrassField field = new GrassField(15);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3, 4));

        try {
            // when
            field.place(animal1);
            field.move(animal1, MoveDirection.FORWARD);
            field.move(animal1, MoveDirection.LEFT);
            field.move(animal1, MoveDirection.BACKWARD);
            field.place(animal2);
            field.move(animal2, MoveDirection.RIGHT);
            field.move(animal2, MoveDirection.BACKWARD);
            field.move(animal2, MoveDirection.BACKWARD);

            // then
            assertEquals(new Vector2d(3, 3), animal1.getPosition());
            assertEquals(MapDirection.WEST, animal1.getAnimalDirection());
            assertEquals(new Vector2d(1, 4), animal2.getPosition());
            assertEquals(MapDirection.EAST, animal2.getAnimalDirection());
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertEquals(15, field.getGrassAmount());
    }

    /**
     * Test to check method 'isOccupied' works properly
     */
    @Test
    void checkIfPositionIsOccupied() {
        // given
        GrassField field = new GrassField(3);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3, 4));

        try {
            field.place(animal1);
            field.place(animal2);

            // then
            assertTrue(field.isOccupied(new Vector2d(2, 2)));
            assertTrue(field.isOccupied(new Vector2d(3, 4)));
            assertFalse(field.isOccupied(new Vector2d(2, 0)));
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertEquals(3, field.getGrassAmount());
    }

    /**
     * Test to check if method 'objectAt' works properly and returns correct Animal object on requested position
     */
    @Test
    void checkObjectOnPosition() {
        // given
        GrassField field = new GrassField(9);
        Animal animal = new Animal();

        try {
            field.place(animal);

            // then
            assertNull(field.objectAt(new Vector2d(2, 3)));
            assertEquals(animal, field.objectAt(new Vector2d(2, 2)));
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertEquals(9, field.getGrassAmount());
    }

    /**
     * Test to check if method 'canMove' works properly (this method is used by the 'move' method so to test it check
     * if animal moves correctly (method 'move' was tested earlier) (method 'canMoveTo' blocks moving to illegal positions))
     */
    @Test
    void checkIfAnimalCanMove() {
        // given
        GrassField field = new GrassField(7);
        Animal animal1 = new Animal(new Vector2d(3, 4));
        Animal animal2 = new Animal();
        Animal animal3 = new Animal(new Vector2d(2, 1));

        try {
            field.place(animal1);
            field.place(animal2);
            field.place(animal3);

            // when
            field.move(animal1, MoveDirection.FORWARD);
            field.move(animal3, MoveDirection.FORWARD);
            field.move(animal2, MoveDirection.FORWARD);

            // then
            assertEquals(new Vector2d(3, 5), animal1.getPosition());
            assertEquals(new Vector2d(2, 3), animal2.getPosition());
            assertEquals(new Vector2d(2, 1), animal3.getPosition());
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertEquals(7, field.getGrassAmount());
    }

    /**
     * Test to check if all animals move correctly
     */
    @Test
    void checkAnimalsMovement() {
        // given
        GrassField field = new GrassField(12);
        Animal animal1 = new Animal(new Vector2d(3, 4));
        Animal animal2 = new Animal();
        Animal animal3 = new Animal(new Vector2d(2, 1));
        Animal animal4 = new Animal(new Vector2d(5, 2));

        try {
            field.place(animal1);
            field.place(animal2);
            field.place(animal3);
            field.place(animal4);

            // when
            field.move(animal4, MoveDirection.FORWARD);
            field.move(animal1, MoveDirection.FORWARD);
            field.move(animal2, MoveDirection.RIGHT);
            field.move(animal4, MoveDirection.BACKWARD);
            field.move(animal3, MoveDirection.LEFT);
            field.move(animal1, MoveDirection.LEFT);
            field.move(animal3, MoveDirection.RIGHT);
            field.move(animal3, MoveDirection.FORWARD);
            field.move(animal1, MoveDirection.FORWARD);
            field.move(animal4, MoveDirection.BACKWARD);
            field.move(animal2, MoveDirection.BACKWARD);

            // then
            assertEquals(new Vector2d(2, 5), animal1.getPosition());
            assertEquals(MapDirection.WEST, animal1.getAnimalDirection());
            assertEquals(new Vector2d(1, 2), animal2.getPosition());
            assertEquals(MapDirection.EAST, animal2.getAnimalDirection());
            assertEquals(new Vector2d(2, 1), animal3.getPosition());
            assertEquals(MapDirection.NORTH, animal3.getAnimalDirection());
            assertEquals(new Vector2d(5, 1), animal4.getPosition());
            assertEquals(MapDirection.NORTH, animal4.getAnimalDirection());
        } catch (IncorrectPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertEquals(12, field.getGrassAmount());
    }
}