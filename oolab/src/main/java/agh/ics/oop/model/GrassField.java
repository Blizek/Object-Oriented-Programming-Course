package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class GrassField implements WorldMap {
    private final HashMap<Vector2d, Grass> grassesMap;
    private final HashMap<Vector2d, Animal> animalsMap = new HashMap<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    public GrassField(int grassCount) {
        grassesMap = new HashMap<>();

        Random random = new Random();
        int generatedGrassCounter = 0;
        while (generatedGrassCounter < grassCount) {
            int x = random.nextInt((int) Math.sqrt(10 * grassCount));
            int y = random.nextInt((int) Math.sqrt(10 * grassCount));

            boolean isGrassPositionFree = true;
            Vector2d actualGrassPosition = new Vector2d(x, y);
            for (Vector2d grassPosition : grassesMap.keySet()) {
                if (Objects.equals(grassPosition, actualGrassPosition)) {
                    isGrassPositionFree = false;
                    break;
                }
            }

            if (isGrassPositionFree) {
                grassesMap.put(actualGrassPosition, new Grass(actualGrassPosition));
                generatedGrassCounter++;
            }
        }
    }

    public int getGrassAmount() {
        return grassesMap.size();
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            animalsMap.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d animalStartPosition = animal.getPosition();
        animal.move(direction, this);
        animalsMap.remove(animalStartPosition);
        Vector2d animalEndPosition = animal.getPosition();
        animalsMap.put(animalEndPosition, animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        Animal animalAtPosition = animalsMap.get(position);
        Grass grassAtPosition = grassesMap.get(position);
        if (animalAtPosition != null) {
            return animalAtPosition;
        }
        return grassAtPosition;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animalsMap.containsKey(position);
    }

    private Vector2d[] getMinAndMaxUsedPositions() {
        Vector2d[] minAndMaxUsedPositions = new Vector2d[2]; // index 0 - min value, index 1 - max value

        Set<Vector2d> allPositions = new HashSet<>();
        allPositions.addAll(animalsMap.keySet());
        allPositions.addAll(grassesMap.keySet());

        for (Vector2d position : allPositions) {
            if (minAndMaxUsedPositions[0] == null) {
                minAndMaxUsedPositions[0] = position;
                minAndMaxUsedPositions[1] = position;
            } else {
                minAndMaxUsedPositions[0] = position.lowerLeft(minAndMaxUsedPositions[0]);
                minAndMaxUsedPositions[1] = position.upperRight(minAndMaxUsedPositions[1]);
            }
        }

        return minAndMaxUsedPositions;
    }

    @Override
    public String toString() {
        Vector2d[] mapBounds = getMinAndMaxUsedPositions();
        Vector2d lowerLeftBound = mapBounds[0];
        Vector2d upperRightBound = mapBounds[1];
        return mapVisualizer.draw(lowerLeftBound, upperRightBound);
    }
}
