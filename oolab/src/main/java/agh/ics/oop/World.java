package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(2, 2);
        Vector2d position2 = new Vector2d(1, 2);

        GrassField grassFieldMap = new GrassField(10);
        grassFieldMap.addObserver(new ConsoleMapDisplay());

        String[] grassFieldDirectionsArgs = {"f", "b", "r", "l", "f", "f", "r", "f", "b", "l", "f", "r"};
        List<MoveDirection> grassFieldDirections = OptionsParser.parseStringToMoveDirections(grassFieldDirectionsArgs);
        List<Vector2d> grassFieldStartPositions = new ArrayList<>();
        grassFieldStartPositions.add(position1);
        grassFieldStartPositions.add(position2);

        Simulation grassFieldSimulation = new Simulation(grassFieldStartPositions, grassFieldDirections, grassFieldMap);
        grassFieldSimulation.run();
        
        RectangularMap rectangularMap = new RectangularMap(5, 5);
        rectangularMap.addObserver(new ConsoleMapDisplay());

        String[] rectangularMapDirectionsArgs = {"f", "b", "r", "l", "f", "f", "r", "f", "b", "l", "f", "r"};
        List<MoveDirection> rectangularMapDirections = OptionsParser.parseStringToMoveDirections(rectangularMapDirectionsArgs);
        List<Vector2d> rectangularMapStartPositions = new ArrayList<>();
        rectangularMapStartPositions.add(position1);
        rectangularMapStartPositions.add(position2);

        Simulation rectangularMapSimulation = new Simulation(rectangularMapStartPositions, rectangularMapDirections, rectangularMap);
        rectangularMapSimulation.run();
    }
}