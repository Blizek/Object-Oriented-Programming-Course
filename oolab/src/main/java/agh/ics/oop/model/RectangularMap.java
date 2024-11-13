package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RectangularMap implements WorldMap<Animal, Vector2d> {
    private final Map<Vector2d, Animal> animals = new HashMap<>(); // map of all animals and its positions
    private final Vector2d topRightCorner;
    private final Vector2d bottomLeftCorner;
    private final MapVisualizer mapVisualizer = new MapVisualizer(this); // visualizer for game

    public RectangularMap(int mapWidth, int mapHeight) {
        // map has x values from 0 to mapWidth - 1 and y values from 0 to mapHeight - 1
        topRightCorner = new Vector2d(mapWidth - 1, mapHeight - 1);
        bottomLeftCorner = new Vector2d(0, 0);
    }


    /**
     * Method to set animal's place on the map (if it's possible)
     * @param animal The animal to place on the map.
     * @return true if animal was set and false if not
     */
    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getAnimalPosition())) {
            animals.put(animal.getAnimalPosition(), animal);
            return true;
        }
        return false;
    }

    /**
     * Method to move animal on the map and update the map
     * @param animal animal that we move
     * @param direction direction in which animal moves
     */
    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d animalStartPosition = animal.getAnimalPosition();
        animal.move(direction, this);
        animals.remove(animalStartPosition);
        Vector2d animalEndPosition = animal.getAnimalPosition();
        animals.put(animalEndPosition, animal);
    }

    /**
     * Method to check if any animal is on the position
     * @param position Position to check.
     * @return true if on the position is any animal, otherwise false
     */
    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    /**
     * Method to get animal which is on passed position
     * @param position The position of the animal.
     * @return Animal object if some animal is on the position, otherwise null
     */
    @Override
    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public List<Animal> setObjectsOnMap(List<Vector2d> positions) {
        List<Animal> animalsList = new ArrayList<>();
        for (Vector2d animalStartPosition: positions) {
            Animal newAnimal = new Animal(animalStartPosition);
            if (place(newAnimal)) { // if position on the map is free set there new animal
                animalsList.add(newAnimal);
            }
        }
        return animalsList;
    }

    /**
     * Method to check if animal can move to passed position. Can do it if isn't going out of the map and not to position
     * that another animal is right now
     * @param position The position checked for the movement possibility.
     * @return true if it's possible else false
     */
    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!(position.precedes(topRightCorner) && position.follows(bottomLeftCorner))) {
            return false;
        }
        if (isOccupied(position)) {
            return false;
        }

        return true;
    }

    /**
     * Method to draw the map
     * @return drawn map by method draw in mapVisualizer class
     */
    @Override
    public String toString() {
        return mapVisualizer.draw(bottomLeftCorner, topRightCorner);
    }
}
