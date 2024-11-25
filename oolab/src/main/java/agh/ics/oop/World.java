package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(2, 2);
        Vector2d position2 = new Vector2d(1, 2);
        WorldMap map = new GrassField(10);

        String[] animalsDirectionsArgs = {"f", "b", "r", "l", "f", "f", "r", "f"};

        List<MoveDirection> animalsDirections = OptionsParser.parseStringToMoveDirections(animalsDirectionsArgs);
        List<Vector2d> animalsStartPositions = new ArrayList<>();
        animalsStartPositions.add(position1);
        animalsStartPositions.add(position2);

        Simulation simulation = new Simulation(animalsStartPositions, animalsDirections, map);
        simulation.run();
    }
}
