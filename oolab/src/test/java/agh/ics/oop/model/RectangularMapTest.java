package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {
    @Test
    void checkSetTwoAnimalsOnOnePosition() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal();

        // when
        boolean isAnimal1Set = map.place(animal1);
        boolean isAnimal2Set = map.place(animal2);

        // then
        assertTrue(isAnimal1Set);
        assertFalse(isAnimal2Set);
    }

    @Test
    void moveAnimalsOnMap() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
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

    @Test
    void checkIfPositionIsOccupied() {
        // given
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3, 4));

        // when
        map.place(animal1);
        map.place(animal2);

        // then
        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(3, 4)));
        assertFalse(map.isOccupied(new Vector2d(2, 0)));
    }
}