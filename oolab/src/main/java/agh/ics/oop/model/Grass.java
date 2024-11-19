package agh.ics.oop.model;

public class Grass {
    private Vector2d grassPosition;

    public Grass(Vector2d grassPosition) {
        this.grassPosition = grassPosition;
    }

    public Vector2d getPosition() {
        return grassPosition;
    }

    @Override
    public String toString() {
        return "*";
    }
}
