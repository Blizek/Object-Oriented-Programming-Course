package projekt.model.maps;

import projekt.model.*;
import projekt.model.random.RandomGrassPositionGenerator;

import java.util.*;

public class PoleMap extends AbstractMap {
    private final Boundary mapBoundary;

    private final Vector2d topRightCorner;

    private final Vector2d equatorLowerLeft;
    private final Vector2d equatorUpperRight;

    private final int grassGrowthCount;
    private final int grassEnergy;

    public PoleMap(int width, int height, int startGrassCount, int grassGrowthCount, int grassEnergy) {
        topRightCorner = new Vector2d(width, height);;
        mapBoundary = new Boundary(bottomLeftCorner, topRightCorner);
        this.grassGrowthCount = grassGrowthCount;
        this.grassEnergy = grassEnergy;

        equatorLowerLeft = new Vector2d(0, Math.round((float) topRightCorner.getY() / 2 - (float) topRightCorner.getY() /10));
        equatorUpperRight = new Vector2d(topRightCorner.getX(), Math.round((float) topRightCorner.getY() / 2 + (float) topRightCorner.getY() /10));

        RandomGrassPositionGenerator grassRandomPositionGenerator = new RandomGrassPositionGenerator(topRightCorner, startGrassCount, grassesMap, equatorLowerLeft, equatorUpperRight);
        for(Vector2d grassPosition : grassRandomPositionGenerator) {
            grassesMap.put(grassPosition, new Grass(grassPosition, grassEnergy));
        }
    }

    public Boundary getMapBoundary() {
        return mapBoundary;
    }

    public int getMapArea() {
        return topRightCorner.getX() * topRightCorner.getY();
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(bottomLeftCorner, topRightCorner);
    }


    @Override
    public void addNewGrasses() {
        RandomGrassPositionGenerator grassRandomPositionGenerator = new RandomGrassPositionGenerator(topRightCorner, grassGrowthCount, grassesMap, equatorLowerLeft, equatorUpperRight);
        for(Vector2d grassPosition : grassRandomPositionGenerator) {
            grassesMap.put(grassPosition, new Grass(grassPosition, grassEnergy));
        }
    }


    public void move(Animal animal) {
        Vector2d previousAnimalPosition = animal.getPosition();
        Direction previousAnimalDirection = animal.getDirection();
        super.move(animal);

        animal.move(topRightCorner);
        animal.moveEnergyUpdate(getPoleFatigueMultiplier(previousAnimalPosition));

        animalsMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal);

        if (!previousAnimalDirection.equals(animal.getDirection())) {
            System.out.printf("Animal changed direction from %s to %s%n", previousAnimalDirection, animal.getDirection());
        }
        if (!previousAnimalPosition.equals(animal.getPosition())){
            System.out.printf("Animal moved from %s to %s%n", previousAnimalPosition, animal.getPosition());
        }
        System.out.println("Animal's energy: " + animal.getEnergy());
        System.out.println(mapVisualizer.draw(bottomLeftCorner, topRightCorner));
    }

    private float getPoleFatigueMultiplier(Vector2d position) {
        float halfPoint = (float) getMapBoundary().upperRight().getY() / 2;
        float distanceFromEquator = Math.abs(position.getY() - halfPoint);
        return 1 + distanceFromEquator / halfPoint;
    }
}
