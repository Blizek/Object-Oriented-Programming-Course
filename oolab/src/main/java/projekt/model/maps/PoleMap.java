package projekt.model.maps;

import projekt.model.*;
import projekt.model.random.RandomGrassPositionGenerator;

import java.util.*;

public class PoleMap extends AbstractMap {
    public PoleMap(int width, int height, int startGrassCount, int grassGrowthCount, int grassEnergy) {
        super(width, height, startGrassCount, grassGrowthCount, grassEnergy);
    }

    @Override
    protected void preMove(Animal animal, Vector2d previousAnimalPosition) {
        super.preMove(animal, previousAnimalPosition);
        animal.moveEnergyUpdate(getPoleFatigueMultiplier(previousAnimalPosition));
    }

    private float getPoleFatigueMultiplier(Vector2d position) {
        float halfPoint = (float) getMapBoundary().upperRight().getY() / 2;
        float distanceFromEquator = Math.abs(position.getY() - halfPoint);
        return 1 + distanceFromEquator / halfPoint;
    }
}
