package projekt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {
    @Test
    void testToString() {
        // then
        assertEquals("N", Direction.NORTH.toString());
        assertEquals("NE", Direction.NORTHEAST.toString());
        assertEquals("E", Direction.EAST.toString());
        assertEquals("SE", Direction.SOUTHEAST.toString());
        assertEquals("S", Direction.SOUTH.toString());
        assertEquals("SW", Direction.SOUTHWEST.toString());
        assertEquals("W", Direction.WEST.toString());
        assertEquals("NW", Direction.NORTHWEST.toString());
    }

    @Test
    void testOppositeDirection() {
        // then
        assertEquals(Direction.SOUTH, Direction.getOppositeDirection(Direction.NORTH));
        assertEquals(Direction.SOUTHWEST, Direction.getOppositeDirection(Direction.NORTHEAST));
        assertEquals(Direction.WEST, Direction.getOppositeDirection(Direction.EAST));
        assertEquals(Direction.NORTHWEST, Direction.getOppositeDirection(Direction.SOUTHEAST));
        assertEquals(Direction.NORTH, Direction.getOppositeDirection(Direction.SOUTH));
        assertEquals(Direction.NORTHEAST, Direction.getOppositeDirection(Direction.SOUTHWEST));
        assertEquals(Direction.EAST, Direction.getOppositeDirection(Direction.WEST));
        assertEquals(Direction.SOUTHEAST, Direction.getOppositeDirection(Direction.NORTHWEST));
    }

    @Test
    void toUnitVectorTest() {
        // then
        assertEquals(new Vector2d(0, 1), Direction.NORTH.toUnitVector());
        assertEquals(new Vector2d(1, 1), Direction.NORTHEAST.toUnitVector());
        assertEquals(new Vector2d(1, 0), Direction.EAST.toUnitVector());
        assertEquals(new Vector2d(1, -1), Direction.SOUTHEAST.toUnitVector());
        assertEquals(new Vector2d(0, -1), Direction.SOUTH.toUnitVector());
        assertEquals(new Vector2d(-1, -1), Direction.SOUTHWEST.toUnitVector());
        assertEquals(new Vector2d(-1, 0), Direction.WEST.toUnitVector());
        assertEquals(new Vector2d(-1, 1), Direction.NORTHWEST.toUnitVector());
    }
}