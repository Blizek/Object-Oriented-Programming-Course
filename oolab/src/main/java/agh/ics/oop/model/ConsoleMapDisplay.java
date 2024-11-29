package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int updatesCounter = 0;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        updatesCounter++;
        System.out.println(message);
        System.out.println(worldMap);
    }
}
