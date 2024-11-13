package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>(List.of("Ala", "ma", "sowoniedźwiedzia"));
        TextMap textMap = new TextMap(words);
        List<MoveDirection> directions = OptionsParser.parseStringToMoveDirections(args);

        Simulation<String, Integer> stringSimulation = new Simulation<>(List.of(), directions, textMap);

        stringSimulation.run();
    }
}
