package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    @Test
    void checkAnimalsDirection() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(3, 2), new Vector2d(1, 1));
        List<MoveDirection> animalsMovesDirectionsList = List.of(MoveDirection.RIGHT, MoveDirection.LEFT);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList);

        // when
        simulation.run();

        // then
        assertEquals(MapDirection.EAST, simulation.getAnimalsList().get(0).getAnimalDirection());
        assertEquals(MapDirection.WEST, simulation.getAnimalsList().get(1).getAnimalDirection());
    }

    @Test
    void checkAnimalsPosition() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(1, 3), new Vector2d(0, 4));
        List<MoveDirection> animalsMovesDirectionsList = List.of(MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.FORWARD);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList);

        // when
        simulation.run();

        // then
        assertEquals(new Vector2d(1, 2), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(new Vector2d(1, 4), simulation.getAnimalsList().get(1).getAnimalPosition());
    }

    @Test
    void checkIfAnimalIsNotOutOfTheMap() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(1, 3), new Vector2d(0, 4));
        List<MoveDirection> animalsMovesDirectionsList = List.of(MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.FORWARD);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList);

        // when
        simulation.run();

        // then
        assertEquals(new Vector2d(0, 3), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(new Vector2d(0, 3), simulation.getAnimalsList().get(1).getAnimalPosition());
    }

    @Test
    void checkStringDirectionParser() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(2, 1), new Vector2d(3, 0));
        String[] stringDirections = {"f", "r", "x", "b", "r", "l", "g"};

        // when
        List<MoveDirection> animalsMovesDirectionsList = OptionsParser.parseStringToMoveDirections(stringDirections);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList);
        simulation.run();

        // then
        assertEquals(new Vector2d(2, 1), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(MapDirection.WEST, simulation.getAnimalsList().get(0).getAnimalDirection());
        assertEquals(new Vector2d(3, 0), simulation.getAnimalsList().get(1).getAnimalPosition());
        assertEquals(MapDirection.SOUTH, simulation.getAnimalsList().get(1).getAnimalDirection());
    }

    @Test
    void checkIfNoAnimalsMoves() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(4, 3), new Vector2d(0, 0));
        String[] stringDirections = {};

        // when
        List<MoveDirection> animalsMovesDirectionsList = OptionsParser.parseStringToMoveDirections(stringDirections);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList);
        simulation.run();

        // then
        assertEquals(new Vector2d(4, 3), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(0).getAnimalDirection());
        assertEquals(new Vector2d(0, 0), simulation.getAnimalsList().get(1).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(1).getAnimalDirection());
    }

    @Test
    void checkIfOnlyWrongStringDirections() {
        // given
        List<Vector2d> animalsStartPositionsList = List.of(new Vector2d(1, 1), new Vector2d(4, 2));
        String[] stringDirections = {"e", "n", "g", "p", "o", "u"};

        // when
        List<MoveDirection> animalsMovesDirectionsList = OptionsParser.parseStringToMoveDirections(stringDirections);
        Simulation simulation = new Simulation(animalsStartPositionsList, animalsMovesDirectionsList);
        simulation.run();

        // then
        assertEquals(new Vector2d(1, 1), simulation.getAnimalsList().get(0).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(0).getAnimalDirection());
        assertEquals(new Vector2d(4, 2), simulation.getAnimalsList().get(1).getAnimalPosition());
        assertEquals(MapDirection.NORTH, simulation.getAnimalsList().get(1).getAnimalDirection());
    }
}