package projekt.model;

public class Grass implements WorldElement {
    private Vector2d grassPosition;
    private final int grassEnergy;

    public Grass(Vector2d grassPosition, int grassEnergy) {
        this.grassPosition = grassPosition;
        this.grassEnergy = grassEnergy;
    }

    @Override
    public Vector2d getPosition() {
        return grassPosition;
    }

    @Override
    public String toString() {
        return "*";
    }
    @Override
    public int getEnergy(){
        return grassEnergy;
    }
}
