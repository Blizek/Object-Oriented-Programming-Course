package projekt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    // given
    private Animal animal;
    private Animal mother;
    private Animal father;
    private Vector2d topRightCorner;

    @BeforeEach
    void setUp() {
        mother = new Animal(new Vector2d(0, 0), 100, List.of(1, 2, 3, 4, 5, 6, 7, 8), null, null, 10);
        father = new Animal(new Vector2d(1, 1), 100, List.of(8, 7, 6, 5, 4, 3, 2, 1), null, null, 10);
        animal = new Animal(new Vector2d(2, 2), 50, List.of(1, 2, 3, 4, 5, 6, 7, 8), mother, father, 10);
        topRightCorner = new Vector2d(5, 5);
    }

    @Test
    void testGetDaysLived() {
        // when
        for (int i = 0; i < 8; i++) animal.move(topRightCorner);
        for (int i = 0; i < 5; i++) mother.move(topRightCorner);
        for (int i = 0; i < 6; i++) father.move(topRightCorner);

        // then
        assertEquals(8, animal.getDaysLived());
        assertEquals(5, mother.getDaysLived());
        assertEquals(6, father.getDaysLived());
    }

    @Test
    void testGetPosition() {
        // then
        assertEquals(new Vector2d(2, 2), animal.getPosition());
        assertEquals(new Vector2d(0, 0), mother.getPosition());
        assertEquals(new Vector2d(1, 1), father.getPosition());
    }

    @Test
    void testGetEnergy() {
        // when
        animal.loseEnergy(30);
        father.eat(20000);

        // then
        assertEquals(20, animal.getEnergy());
        assertEquals(100, mother.getEnergy());
        assertEquals(20100, father.getEnergy());
    }

    @Test
    void testGetImageName() {
        // when
        animal.loseEnergy(30);
        father.eat(20000);

        assertEquals("full-monkey.png", animal.getImageName());
        assertEquals("strong-monkey.png", mother.getImageName());
        assertEquals("god-tier-ultra-saiyan-monkey.png", father.getImageName());
    }

    @Test
    void testGetChildrenCount() {
        // when
        mother.setChildren(animal);
        father.setChildren(animal);

        // then
        assertEquals(0, animal.getChildrenCount());
        assertEquals(1, mother.getChildrenCount());
        assertEquals(1, father.getChildrenCount());
    }

    @Test
    void testGetChildren() {
        // when
        mother.setChildren(animal);
        father.setChildren(animal);

        // then
        assertTrue(animal.getChildren().isEmpty());
        assertTrue(mother.getChildren().contains(animal));
        assertTrue(father.getChildren().contains(animal));
    }

    @Test
    void testSetChildren() {
        // given
        Animal child = new Animal(new Vector2d(3, 3), 30, List.of(1, 2, 3, 4, 5, 6, 7, 8), animal, null, 10);

        // when
        animal.setChildren(child);

        // then
        assertEquals(1, animal.getChildrenCount());
        assertEquals(child, animal.getChildren().getFirst());
    }

    @Test
    void testGetGenome() {
        // then
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8), animal.getGenome());
        assertEquals(List.of(8, 7, 6, 5, 4, 3, 2, 1), father.getGenome());
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8), mother.getGenome());
    }

    @Test
    void testGetEatingCounter() {
        // when
        for (int i = 0; i < 5; i++) animal.eat(10);
        for (int i = 0; i < 6; i++) mother.eat(10);

        // then
        assertEquals(5, animal.getEatingCounter());
        assertEquals(6, mother.getEatingCounter());
        assertEquals(0, father.getEatingCounter());
    }

    @Test
    void testGetDescendants() {
        // given
        Animal child = new Animal(new Vector2d(3, 3), 30, List.of(1, 2, 3, 4, 5, 6, 7, 8), animal, null, 10);

        // when
        animal.setChildren(child);
        animal.newDescendant();

        // then
        assertEquals(1, animal.getDescendants());
    }

    @Test
    void testMoveEnergyUpdate() {
        // when
        animal.moveEnergyUpdate(1.0f);
        father.moveEnergyUpdate(2.5f);

        // then
        assertEquals(40, animal.getEnergy());
        assertEquals(75, father.getEnergy());
    }

    @Test
    void testNewDescendant() {
        // when
        father.newDescendant();

        // then
        assertEquals(1, father.getDescendants());
    }

    @Test
    void testLoseEnergy() {
        // when
        animal.loseEnergy(10);

        // then
        assertEquals(40, animal.getEnergy());
    }

    @Test
    void testMove() {
        // when
        animal.move(topRightCorner);

        // then
        assertNotEquals(new Vector2d(2, 2), animal.getPosition());
    }

    @Test
    void testChangeDirection() {
        // given
        Direction initialDirection = animal.getDirection();

        // when
        animal.changeDirection();

        // then
        if (animal.getActualGenome() != 0) assertNotEquals(initialDirection, animal.getDirection());
        else assertEquals(initialDirection, animal.getDirection());
    }

    @Test
    void testEat() {
        // when
        animal.eat(20);

        // then
        assertEquals(70, animal.getEnergy());
        assertEquals(1, animal.getEatingCounter());
    }
}