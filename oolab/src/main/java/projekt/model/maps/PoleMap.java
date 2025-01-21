package projekt.model.maps;

import projekt.model.*;
import java.util.*;

public class PoleMap extends EarthMap {

    public PoleMap(int width, int height, int startGrassCount, int grassGrowthCount, int grassEnergy) {
        super(width, height, startGrassCount, grassGrowthCount, grassEnergy);
    }

    @Override
    public void move(Animal animal) {
        Vector2d previousAnimalPosition = animal.getPosition();
        Direction previousAnimalDirection = animal.getDirection();


        List<Animal> animalsAtPreviousPosition = super.animalsMap.get(previousAnimalPosition);
        if (animalsAtPreviousPosition != null) {
            animalsAtPreviousPosition.remove(animal);
            if (animalsAtPreviousPosition.isEmpty()) {
                super.animalsMap.remove(previousAnimalPosition);
            }
        }

        animal.move(super.topRightCorner);
        animal.moveEnergyUpdate(getPoleFatigueMultiplier(previousAnimalPosition));

        animalsMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal);

        if (!previousAnimalDirection.equals(animal.getDirection())) {
            System.out.printf("Animal changed direction from %s to %s%n", previousAnimalDirection, animal.getDirection());
        }
        if (!previousAnimalPosition.equals(animal.getPosition())){
            System.out.printf("Animal moved from %s to %s%n", previousAnimalPosition, animal.getPosition());
        }
        System.out.println("Animal's energy: " + animal.getEnergy());
        System.out.println(super.mapVisualizer.draw(bottomLeftCorner, topRightCorner));
    }

    private float getPoleFatigueMultiplier(Vector2d position) {
        float halfPoint = (float) super.getMapBoundary().upperRight().getY() / 2;
        float distanceFromEquator = Math.abs(position.getY() - halfPoint);
        return 1 + distanceFromEquator / halfPoint;
    }
}
