package projekt.model.maps;

import projekt.model.Animal;
import projekt.model.Vector2d;

public class PoleMap extends AbstractMap {
    public PoleMap(int width, int height, int startGrassCount, int grassGrowthCount, int grassEnergy) {
        super(width, height, startGrassCount, grassGrowthCount, grassEnergy);
    }

    @Override
    protected void subtractMoveEnergy(Animal animal, Vector2d previousAnimalPosition) {
        animal.moveEnergyUpdate(getPoleFatigueMultiplier(previousAnimalPosition));
    }

    private float getPoleFatigueMultiplier(Vector2d position) {
        float halfPoint = (float) getMapBoundary().upperRight().getY() / 2;
        float distanceFromEquator = Math.abs(position.getY() - halfPoint);
        return 1 + distanceFromEquator / halfPoint;
    }
}
