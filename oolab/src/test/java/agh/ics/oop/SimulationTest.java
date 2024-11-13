package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    /**
     * Test to check if animals' positions and directions are correct
     */
    @Test
    void checkAnimalsDirectionAndPosition() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(1, 3), new Vector2d(0, 4));
        List<MoveDirection> animalsMovesDirectionsList = List.of(MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.FORWARD);
        WorldMap map = new RectangularMap(5, 5);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList, map);

        // when
        simulation.run();

        // then
        assertEquals(new Vector2d(1, 2), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(MapDirection.SOUTH, simulation.getAnimalsList().get(0).getAnimalDirection());
        assertEquals(new Vector2d(1, 4), simulation.getAnimalsList().get(1).getAnimalPosition());
        assertEquals(MapDirection.WEST, simulation.getAnimalsList().get(1).getAnimalDirection());
    }

    /**
     * Test to check if animal will try to go out of the map will be returned to previous position
     */
    @Test
    void checkIfAnimalIsNotOutOfTheMap() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(1, 3), new Vector2d(0, 4));
        List<MoveDirection> animalsMovesDirectionsList = List.of(MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.FORWARD);
        WorldMap map = new RectangularMap(5, 5);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList, map);

        // when
        simulation.run();

        // then
        assertEquals(new Vector2d(0, 3), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(new Vector2d(0, 4), simulation.getAnimalsList().get(1).getAnimalPosition());
    }

    /**
     * Test to check of directions parser works properly (as arguments has both wrong and correct data)
     */
    @Test
    void checkStringDirectionParser() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(2, 1), new Vector2d(3, 0));
        String[] stringDirections = {"f", "r", "x", "b", "r", "l", "g"};
        WorldMap map = new RectangularMap(5, 5);

        // when
        List<MoveDirection> animalsMovesDirectionsList = OptionsParser.parseStringToMoveDirections(stringDirections);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList, map);
        simulation.run();

        // then
        assertEquals(new Vector2d(2, 1), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(MapDirection.WEST, simulation.getAnimalsList().get(0).getAnimalDirection());
        assertEquals(new Vector2d(3, 0), simulation.getAnimalsList().get(1).getAnimalPosition());
        assertEquals(MapDirection.SOUTH, simulation.getAnimalsList().get(1).getAnimalDirection());
    }

    /**
     * Test to check if animals will stay at positions when MoveDirections list will be empty
     */
    @Test
    void checkIfNoAnimalsMoves() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(4, 3), new Vector2d(0, 0));
        String[] stringDirections = {};
        WorldMap map = new RectangularMap(5, 5);

        // when
        List<MoveDirection> animalsMovesDirectionsList = OptionsParser.parseStringToMoveDirections(stringDirections);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList, map);
        simulation.run();

        // then
        assertEquals(new Vector2d(4, 3), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(0).getAnimalDirection());
        assertEquals(new Vector2d(0, 0), simulation.getAnimalsList().get(1).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(1).getAnimalDirection());
    }

    /**
     * Test to check if animals won't move because all passed directions are wrong
     */
    @Test
    void checkIfOnlyWrongStringDirections() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(1, 1), new Vector2d(4, 2));
        String[] stringDirections = {"e", "n", "g", "p", "o", "u"};
        WorldMap map = new RectangularMap(5, 5);

        // when
        List<MoveDirection> animalsMovesDirectionsList = OptionsParser.parseStringToMoveDirections(stringDirections);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList, map);
        simulation.run();

        // then
        assertEquals(new Vector2d(1, 1), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(0).getAnimalDirection());
        assertEquals(new Vector2d(4, 2), simulation.getAnimalsList().get(1).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(1).getAnimalDirection());
    }

    @Test
    void tryToInitiallySetTwoAnimalsOnOnePosition() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(1, 1), new Vector2d(1, 1));
        String[] stringDirections = {};
        WorldMap map = new RectangularMap(5, 5);

        // when
        List<MoveDirection> animalsMovesDirectionsList = OptionsParser.parseStringToMoveDirections(stringDirections);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList, map);
        simulation.run();

        // then
        assertEquals(1, simulation.getAnimalsList().size());
    }

    @Test
    void tryToMoveAnimalOnOccupiedPosition() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(1, 1), new Vector2d(1, 2));
        String[] stringDirections = {"f"};
        WorldMap map = new RectangularMap(5, 5);

        // when
        List<MoveDirection> animalsMovesDirectionsList = OptionsParser.parseStringToMoveDirections(stringDirections);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList, map);
        simulation.run();

        // then
        assertEquals(new Vector2d(1, 1), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(0).getAnimalDirection());
        assertEquals(new Vector2d(1, 2), simulation.getAnimalsList().get(1).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(1).getAnimalDirection());
    }
}