package agh.ics.oop.model;

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
        WorldElement animalAtPosition = super.objectAt(position);
        Grass grassAtPosition = grassesMap.get(position);
        if (animalAtPosition != null) {
            return animalAtPosition;
        }
        return grassAtPosition;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> fullElementsList = super.getElements();
        fullElementsList.addAll(grassesMap.values());
        return fullElementsList;
    }

    @Override
    public Boundary getCurrentBounds() {
        Set<Vector2d> allPositions = new HashSet<>();
        allPositions.addAll(animalsMap.keySet());
        allPositions.addAll(grassesMap.keySet());

        Vector2d lowerLeft = null;
        Vector2d upperRight = null;

        for (Vector2d position: allPositions) {
            if (lowerLeft == null) {
                lowerLeft = position;
                upperRight = position;
            } else {
                lowerLeft = position.lowerLeft(lowerLeft);
                upperRight = position.upperRight(upperRight);
            }
        }

        return new Boundary(lowerLeft, upperRight);
    }
}
