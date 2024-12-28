package projekt.model;

import java.util.HashMap;

public class GrassMaker implements ElementMaker<Grass> {
    private final int grassEnergy;
    public GrassMaker(int grassEnergy){
        this.grassEnergy = grassEnergy;
    }
    @Override
    public Grass make(Vector2d position) {
        // Implement the method logic here
        return null;
    }
}
