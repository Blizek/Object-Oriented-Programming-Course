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

        // when
        animal2.move(MoveDirection.LEFT);

        // then
        assertEquals("Położenie zwierzaka: (2, 2) z orientacją Północ", animal1.toString());
        assertEquals("Położenie zwierzaka: (3, 1) z orientacją Zachód", animal2.toString());
    }

    /**
     * Test to check if if animals are at positions
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

        // when
        animal1.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.LEFT);

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

        // when
        animal1.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.BACKWARD);

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

        // when
        animal1.move(MoveDirection.RIGHT);
        animal1.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.BACKWARD);
        animal2.move(MoveDirection.LEFT);

        // then
        assertEquals("Położenie zwierzaka: (3, 2) z orientacją Wschód", animal1.toString());
        assertEquals("Położenie zwierzaka: (3, 2) z orientacją Południe", animal2.toString());
        assertEquals(new Vector2d(3, 2), animal1.getAnimalPosition());
        assertEquals(MapDirection.EAST, animal2.getAnimalDirection());
        assertEquals(new Vector2d(3, 2), animal1.getAnimalPosition());
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

        // when
        animal1.move(MoveDirection.BACKWARD);
        animal2.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.BACKWARD);
        animal3.move(MoveDirection.FORWARD);
        animal4.move(MoveDirection.RIGHT);
        animal4.move(MoveDirection.FORWARD);

        // then
        assertEquals(downLeft, animal1.getAnimalPosition());
        assertEquals(downLeft, animal2.getAnimalPosition());
        assertEquals(upRight, animal3.getAnimalPosition());
        assertEquals(upRight, animal4.getAnimalPosition());
    }
}