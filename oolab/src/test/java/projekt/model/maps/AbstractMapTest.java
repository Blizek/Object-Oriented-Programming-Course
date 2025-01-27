package projekt.model.maps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projekt.model.Animal;
import projekt.model.Direction;
import projekt.model.Grass;
import projekt.model.Vector2d;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractMapTest {

    @Test
    void testPlaceAnimal() {
        // given
        AbstractMap map = new EarthMap(10, 10, 5, 2, 5);
        Animal animal = new Animal(new Vector2d(2, 3),50, List.of(0, 1, 2, 3, 4, 5, 6, 7), null, null, 10);

        // when
        map.place(animal);

        // then
        assertTrue(map.objectAt(new Vector2d(2, 3)).contains(animal));
    }

    @Test
    void testPlaceGrass() {
        // given
        AbstractMap map = new EarthMap(10, 10, 5, 2, 5);

        // when
        map.addNewGrasses();

        // then
        assertEquals(7, map.getGrassesMap().size());
    }

    @Test
    void testRemoveAnimal() {
        // given
        AbstractMap map = new EarthMap(10, 10, 5, 2, 5);
        Animal animal = new Animal(new Vector2d(2, 3),0, List.of(0, 1, 2, 3, 4, 5, 6, 7), null, null, 10);
        map.place(animal);

        // when
        map.removeDeadAnimal(animal);

        // then
        assertFalse(map.objectAt(new Vector2d(2, 3)).contains(animal));
    }

    @Test
    void testRemoveGrass() {
        // given
        AbstractMap map = new EarthMap(10, 10, 5, 2, 5);
        map.addNewGrasses();

        // when
        HashMap<Vector2d, Grass> grassesMap = map.getGrassesMap();
        for (Vector2d position : grassesMap.keySet()) {
            map.removeGrass(position);
        }

        // then
        assertEquals(0, map.getGrassesMap().size());
    }

    @Test
    void testMoveAnimal() {
        // given
        AbstractMap map = new EarthMap(10, 10, 5, 2, 5);
        Animal animal = new Animal(new Vector2d(2, 3),0, List.of(0, 0, 0, 0, 0), null, null, 10);
        map.place(animal);

        // when
        map.move(animal);

        // then
        switch (animal.getDirection()) {
            case NORTH -> assertEquals(new Vector2d(2, 4), animal.getPosition());
            case NORTHEAST -> assertEquals(new Vector2d(3, 4), animal.getPosition());
            case EAST -> assertEquals(new Vector2d(3, 3), animal.getPosition());
            case SOUTHEAST -> assertEquals(new Vector2d(3, 2), animal.getPosition());
            case SOUTH -> assertEquals(new Vector2d(2, 2), animal.getPosition());
            case SOUTHWEST -> assertEquals(new Vector2d(1, 2), animal.getPosition());
            case WEST -> assertEquals(new Vector2d(1, 3), animal.getPosition());
            case NORTHWEST -> assertEquals(new Vector2d(1, 4), animal.getPosition());
        }
    }

    @Test
    void testGetAnimals() {
        // given
        AbstractMap map = new EarthMap(10, 10, 5, 2, 5);
        Animal animal = new Animal(new Vector2d(2, 3),0, List.of(0, 0, 0, 0, 0), null, null, 10);

        // when
        map.place(animal);

        // then
        assertTrue(map.getAnimalsMap().containsKey(new Vector2d(2, 3)));
    }

    @Test
    void testEquatorBoundaries() {
        // given
        AbstractMap map1 = new EarthMap(10, 10, 5, 2, 5);
        AbstractMap map2 = new EarthMap(10, 20, 5, 2, 5);
        AbstractMap map3 = new EarthMap(15, 15, 5, 2, 5);

        // when
        assertEquals(new Vector2d(0, 4), map1.getEquatorBoundary().lowerLeft());
        assertEquals(new Vector2d(9, 5), map1.getEquatorBoundary().upperRight());
        assertEquals(new Vector2d(0, 8), map2.getEquatorBoundary().lowerLeft());
        assertEquals(new Vector2d(9, 11), map2.getEquatorBoundary().upperRight());
        assertEquals(new Vector2d(0, 6), map3.getEquatorBoundary().lowerLeft());
        assertEquals(new Vector2d(14, 8), map3.getEquatorBoundary().upperRight());
    }

    @Test
    void testEquatorGrassDistribution() {
        // given
        AbstractMap map = new EarthMap(10, 10, 15, 2, 5);
        Vector2d equatorLowerLeft = map.equatorMapBoundary.lowerLeft();
        Vector2d equatorUpperRight = map.equatorMapBoundary.upperRight();
        HashMap<Vector2d, Grass> grassesMap = map.getGrassesMap();

        // then
        int equatorPlantCount = 0;
        int outsideEquatorPlantCount = 0;
        for (Vector2d position : grassesMap.keySet()) {
            if (position.follows(equatorLowerLeft) && position.precedes(equatorUpperRight)) equatorPlantCount++;
            else outsideEquatorPlantCount++;
        }

        assertTrue(equatorPlantCount > outsideEquatorPlantCount);
    }

    @Test
    void testMovingRightOutOfBounds() {
        // given
        AbstractMap map = new EarthMap(10, 10, 15, 2, 5);
        Animal animal1 = new Animal(new Vector2d(9, 4),0, List.of(0), null, null, 10);
        Animal animal2 = new Animal(new Vector2d(9, 4),0, List.of(0), null, null, 10);
        Animal animal3 = new Animal(new Vector2d(9, 4),0, List.of(0), null, null, 10);
        animal1.setDirection(Direction.EAST);
        animal2.setDirection(Direction.NORTHEAST);
        animal3.setDirection(Direction.SOUTHEAST);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        // when
        map.move(animal1);
        map.move(animal2);
        map.move(animal3);

        // then
        assertEquals(new Vector2d(0, 4), animal1.getPosition());
        assertEquals(Direction.EAST, animal1.getDirection());
        assertEquals(new Vector2d(0, 5), animal2.getPosition());
        assertEquals(Direction.NORTHEAST, animal2.getDirection());
        assertEquals(new Vector2d(0, 3), animal3.getPosition());
        assertEquals(Direction.SOUTHEAST, animal3.getDirection());
    }

    @Test
    void testMovingLeftOutOfBounds() {
        // given
        AbstractMap map = new EarthMap(10, 10, 15, 2, 5);
        Animal animal1 = new Animal(new Vector2d(0, 4),0, List.of(0), null, null, 10);
        Animal animal2 = new Animal(new Vector2d(0, 4),0, List.of(0), null, null, 10);
        Animal animal3 = new Animal(new Vector2d(0, 4),0, List.of(0), null, null, 10);
        animal1.setDirection(Direction.WEST);
        animal2.setDirection(Direction.NORTHWEST);
        animal3.setDirection(Direction.SOUTHWEST);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        // when
        map.move(animal1);
        map.move(animal2);
        map.move(animal3);

        // then
        assertEquals(new Vector2d(9, 4), animal1.getPosition());
        assertEquals(Direction.WEST, animal1.getDirection());
        assertEquals(new Vector2d(9, 5), animal2.getPosition());
        assertEquals(Direction.NORTHWEST, animal2.getDirection());
        assertEquals(new Vector2d(9, 3), animal3.getPosition());
        assertEquals(Direction.SOUTHWEST, animal3.getDirection());
    }

    @Test
    void testMovingUpOutOfBounds() {
        // given
        AbstractMap map = new EarthMap(10, 10, 15, 2, 5);
        Animal animal1 = new Animal(new Vector2d(4, 9),0, List.of(0), null, null, 10);
        Animal animal2 = new Animal(new Vector2d(4, 9),0, List.of(0), null, null, 10);
        Animal animal3 = new Animal(new Vector2d(4, 9),0, List.of(0), null, null, 10);
        animal1.setDirection(Direction.NORTH);
        animal2.setDirection(Direction.NORTHEAST);
        animal3.setDirection(Direction.NORTHWEST);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        // when
        map.move(animal1);
        map.move(animal2);
        map.move(animal3);

        // then
        assertEquals(new Vector2d(4, 9), animal1.getPosition());
        assertEquals(Direction.SOUTH, animal1.getDirection());
        assertEquals(new Vector2d(4, 9), animal2.getPosition());
        assertEquals(Direction.SOUTHWEST, animal2.getDirection());
        assertEquals(new Vector2d(4, 9), animal3.getPosition());
        assertEquals(Direction.SOUTHEAST, animal3.getDirection());
    }

    @Test
    void testMovingDownOutOfBounds() {
        // given
        AbstractMap map = new EarthMap(10, 10, 15, 2, 5);
        Animal animal1 = new Animal(new Vector2d(4, 0),0, List.of(0), null, null, 10);
        Animal animal2 = new Animal(new Vector2d(4, 0),0, List.of(0), null, null, 10);
        Animal animal3 = new Animal(new Vector2d(4, 0),0, List.of(0), null, null, 10);
        animal1.setDirection(Direction.SOUTH);
        animal2.setDirection(Direction.SOUTHWEST);
        animal3.setDirection(Direction.SOUTHEAST);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        // when
        map.move(animal1);
        map.move(animal2);
        map.move(animal3);

        // then
        assertEquals(new Vector2d(4, 0), animal1.getPosition());
        assertEquals(Direction.NORTH, animal1.getDirection());
        assertEquals(new Vector2d(4, 0), animal2.getPosition());
        assertEquals(Direction.NORTHEAST, animal2.getDirection());
        assertEquals(new Vector2d(4, 0), animal3.getPosition());
        assertEquals(Direction.NORTHWEST, animal3.getDirection());
    }

    @Test
    void testAnimalsSortedByEnergy() {
        // given
        AbstractMap map = new EarthMap(10, 10, 15, 2, 5);
        Vector2d position = new Vector2d(5, 5);
        Animal animal1 = new Animal(position,10, List.of(0), null, null, 10);
        Animal animal2 = new Animal(position,20, List.of(0), null, null, 10);
        Animal animal3 = new Animal(position,30, List.of(0), null, null, 10);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        // then
        assertEquals(animal3, map.dominantAnimalAtPosition(position));
    }

    @Test
    void testAnimalsSortedByDayLife() {
        // given
        AbstractMap map = new EarthMap(10, 10, 15, 2, 5);
        Vector2d position = new Vector2d(5, 5);
        Animal animal1 = new Animal(position,10, List.of(0), null, null, 10);
        Animal animal2 = new Animal(position,10, List.of(0), null, null, 10);
        Animal animal3 = new Animal(position,10, List.of(0), null, null, 10);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        // when
        for (int i = 0; i < 10; i++) animal2.move(map.topRightCorner);
        for (int i = 0; i < 8; i++) animal3.move(map.topRightCorner);

        // then
        assertEquals(animal2, map.dominantAnimalAtPosition(position));
    }

    @Test
    void testAnimalsSortedByChildren() {
        // given
        AbstractMap map = new EarthMap(10, 10, 15, 2, 5);
        Vector2d position = new Vector2d(5, 5);
        Animal animal1 = new Animal(position,10, List.of(0), null, null, 10);
        Animal animal2 = new Animal(position,10, List.of(0), null, null, 10);
        Animal animal3 = new Animal(position,10, List.of(0), null, null, 10);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        // when
        animal1.setChildren(animal2);
        animal1.setChildren(animal3);
        animal2.setChildren(animal3);

        // then
        assertEquals(animal1, map.dominantAnimalAtPosition(position));
    }
}