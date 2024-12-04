package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        try {
            Vector2d position1 = new Vector2d(2, 2);
            Vector2d position2 = new Vector2d(1, 2);

            RectangularMap rectangularMap = new RectangularMap(10, 10);
            GrassField grassField = new GrassField(10);

            ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
            rectangularMap.addObserver(consoleMapDisplay);
            grassField.addObserver(consoleMapDisplay);

            String[] animalsDirectionsArgs = {"f", "r", "f", "l", "b", "b", "r", "l", "f", "f", "r", "b", "l", "f"};
            List<MoveDirection> animalsDirections = OptionsParser.parseStringToMoveDirections(animalsDirectionsArgs);

            List<Vector2d> animalsStartPositions = new ArrayList<>();
            animalsStartPositions.add(position1);
            animalsStartPositions.add(position2);

            Simulation simulation1 = new Simulation(animalsStartPositions, animalsDirections, rectangularMap);
            Simulation simulation2 = new Simulation(animalsStartPositions, animalsDirections, grassField);

            List<Simulation> simulations = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                RectangularMap asyncRectangularMap = new RectangularMap(10, 10);
                asyncRectangularMap.addObserver(consoleMapDisplay);
                simulations.add(new Simulation(animalsStartPositions, animalsDirections, asyncRectangularMap));
            }
            simulations.add(simulation1);
            simulations.add(simulation2);

            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsyncInThreadPool();
            engine.awaitSimulationEnd();
            System.out.println("System zakończył działanie");
        } catch (IllegalArgumentException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}