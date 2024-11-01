package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {
    /**
     * Test to check if MapDirection.next works properly (test covers all directions)
     */
    @Test
    void allGeographicalDirectionsNextDirection() {
        // given
        MapDirection north = MapDirection.NORTH;
        MapDirection east = MapDirection.EAST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection west = MapDirection.WEST;

        //when
        MapDirection northNext = north.next();
        MapDirection eastNext = east.next();
        MapDirection southNext = south.next();
        MapDirection westNext = west.next();

        // then
        assertEquals(northNext, MapDirection.EAST);
        assertEquals(eastNext, MapDirection.SOUTH);
        assertEquals(southNext, MapDirection.WEST);
        assertEquals(westNext, MapDirection.NORTH);
    }

    /**
     * Test to check if MapDirection.previous works properly (test covers all directions)
     */
    @Test
    void allGeographicalDirectionsPreviousDirection() {
        // given
        MapDirection north = MapDirection.NORTH;
        MapDirection east = MapDirection.EAST;
        MapDirection south = MapDirection.SOUTH;
        MapDirection west = MapDirection.WEST;

        //when
        MapDirection northPrevious = north.previous();
        MapDirection eastPrevious = east.previous();
        MapDirection southPrevious = south.previous();
        MapDirection westPrevious = west.previous();

        // then
        assertEquals(northPrevious, MapDirection.WEST);
        assertEquals(eastPrevious, MapDirection.NORTH);
        assertEquals(southPrevious, MapDirection.EAST);
        assertEquals(westPrevious, MapDirection.SOUTH);
    }

}