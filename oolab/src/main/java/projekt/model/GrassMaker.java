package projekt.model;

public class GrassMaker implements ElementMaker {
    private final int grassEnergy;

    public GrassMaker(int grassEnergy) {
        this.grassEnergy = grassEnergy;
    }

    @Override
    public Grass makeAnimal(Vector2d position) {
        return new Grass(position, grassEnergy);
    }
}
