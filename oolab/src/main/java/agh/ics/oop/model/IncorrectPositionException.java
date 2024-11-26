package agh.ics.oop.model;

public class IncorrectPositionException extends Exception {
    private final Vector2d position;

    public IncorrectPositionException(Vector2d position) {
        super("Position " + position + " is not correct");
        this.position = position;
    }
}
