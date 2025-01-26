package projekt.model.maps;


import projekt.model.Animal;
import projekt.model.Vector2d;


public class EarthMap extends AbstractMap {
    public EarthMap(int width, int height, int startGrassCount, int grassGrowthCount, int grassEnergy) {
        super(width, height, startGrassCount, grassGrowthCount, grassEnergy);
    }

    @Override
    protected void subtractMoveEnergy(Animal animal, Vector2d previousAnimalPosition) {
        animal.moveEnergyUpdate(1.0f);
    }
}
