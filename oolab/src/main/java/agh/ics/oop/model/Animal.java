package agh.ics.oop.model;

public class Animal implements WorldElement{
    private MapDirection animalDirection; // animal's geographic direction
    private Vector2d animalPosition; // animal's position on the map

    public static final Vector2d DEFAULT_ANIMAL_POSITION = new Vector2d(2, 2); // default animal's start position
    public static final MapDirection DEFAULT_ANIMAL_DIRECTION = MapDirection.NORTH; // default animal's start direction

    /**
     * Default constructor for animal class
     */
    public Animal() {
        this(DEFAULT_ANIMAL_POSITION);
    }

    /**
     * Constructor with setting start position
     * @param startPosition animal position start
     */
    public Animal(Vector2d startPosition) {
        animalDirection = DEFAULT_ANIMAL_DIRECTION;
        animalPosition = startPosition;
    }

    public MapDirection getAnimalDirection() {
        return animalDirection;
    }

    @Override
    public Vector2d getPosition() {
        return animalPosition;
    }

    @Override
    public String toString() {
        return switch (animalDirection) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }

    /**
     * Method to check if animal is at given position
     * @param position Vector2d object with which animal's position is compared
     * @return true/false value if animal's position is same as given
     */
    public boolean isAt(Vector2d position) {
        return animalPosition.equals(position);
    }

    /**
     * Method to move animal on map
     * @param direction in which direction animal should move
     */
    public void move(MoveDirection direction, MoveValidator validator) {
        Vector2d newAnimalPosition = animalPosition;

        // all possible moves
        switch (direction) {
            case RIGHT -> animalDirection = animalDirection.next(); // turn right around your own axis
            case LEFT -> animalDirection = animalDirection.previous(); // turn left around your own axis
            case FORWARD -> newAnimalPosition = animalPosition.add(animalDirection.toUnitVector()); // set new possible animal's position moving forward
            case BACKWARD -> newAnimalPosition = animalPosition.subtract(animalDirection.toUnitVector()); // set new possible animal's position moving backward
        }

        if (validator.canMoveTo(newAnimalPosition)) { // check if it's possible to move
            animalPosition = newAnimalPosition; // if is move there
        }
    }
}
