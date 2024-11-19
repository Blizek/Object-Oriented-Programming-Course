package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final HashMap<Vector2d, Grass> grassesMap;

    public GrassField(int grassCount) {
        grassesMap = new HashMap<>();

        int maxWidth = (int) Math.sqrt(grassCount * 10) + 1;
        int maxHeight = (int) Math.sqrt(grassCount * 10) + 1;
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidth, maxHeight, grassCount);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grassesMap.put(grassPosition, new Grass(grassPosition));
        }
    }

    public int getGrassAmount() {
        return grassesMap.size();
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        Animal animalAtPosition = (Animal) super.objectAt(position);
        Grass grassAtPosition = grassesMap.get(position);
        if (animalAtPosition != null) {
            return animalAtPosition;
        }
        return grassAtPosition;
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

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> fullElementsList = super.getElements();
        fullElementsList.addAll(grassesMap.values());
        return List.copyOf(fullElementsList);
    }
}
