package agh.ics.oop.model;

public class Animal {
    private MapDirection animalDirection;
    private Vector2d animalPosition;

    public Animal() {
        animalDirection = MapDirection.NORTH;
        animalPosition = new Vector2d(2, 2);
    }

    public Animal(Vector2d startPosition) {
        animalDirection = MapDirection.NORTH;
        animalPosition = startPosition;
    }

    public MapDirection getAnimalDirection() {
        return animalDirection;
    }

    public void setAnimalDirection(MapDirection animalDirection) {
        this.animalDirection = animalDirection;
    }

    public Vector2d getAnimalPosition() {
        return animalPosition;
    }

    public void setAnimalPosition(Vector2d animalPosition) {
        this.animalPosition = animalPosition;
    }

    @Override
    public String toString() {
        return "Położenie zwierzaka: (%d, %d) z orientacją %s".formatted(animalPosition.getX(), animalPosition.getY(), animalDirection);
    }

    public boolean isAt(Vector2d position) {
        return animalPosition.equals(position);
    }

    public void move(MoveDirection direction) {
        Vector2d newAnimalPosition = animalPosition;
        switch (direction) {
            case RIGHT -> animalDirection = animalDirection.next();
            case LEFT -> animalDirection = animalDirection.previous();
            case FORWARD -> newAnimalPosition = animalPosition.add(animalDirection.toUnitVector());
            case BACKWARD -> newAnimalPosition = animalPosition.subtract(animalDirection.toUnitVector());
        }

        if (newAnimalPosition.getX() < 0 || newAnimalPosition.getY() < 0) {
            System.out.println("Zwierzak wychodzi poza mapę");
            newAnimalPosition = animalPosition;
        } else if (newAnimalPosition.getX() > 4 || newAnimalPosition.getY() > 4) {
            System.out.println("Zwierzak wychodzi poza mapę");
            newAnimalPosition = animalPosition;
        }

        animalPosition = newAnimalPosition;
    }
}
