package agh.ics.oop.model;

public class Animal {
    private MapDirection animalDirection; // animal's geographic direction
    private Vector2d animalPosition; // animal's position on the map

    public static final Vector2d DEFAULT_ANIMAL_POSITION = new Vector2d(2, 2); // default animal's start position
    public static final MapDirection DEFAULT_ANIMAL_DIRECTION = MapDirection.NORTH; // default animal's start direction
    public static final Vector2d BOTTOM_LEFT_CORNER = new Vector2d(0, 0); // bottom left corner of the map
    public static final Vector2d TOP_RIGHT_CORNER = new Vector2d(4, 4); // top right corner of the map

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

    public Vector2d getAnimalPosition() {
        return animalPosition;
    }

    @Override
    public String toString() {
        return "Położenie zwierzaka: (%d, %d) z orientacją %s".formatted(animalPosition.getX(), animalPosition.getY(), animalDirection);
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
    public void move(MoveDirection direction) {
        Vector2d newAnimalPosition = animalPosition;

        // all possible moves
        switch (direction) {
            case RIGHT -> animalDirection = animalDirection.next(); // turn right around your own axis
            case LEFT -> animalDirection = animalDirection.previous(); // turn left around your own axis
            case FORWARD -> newAnimalPosition = animalPosition.add(animalDirection.toUnitVector()); // move forward
            case BACKWARD -> newAnimalPosition = animalPosition.subtract(animalDirection.toUnitVector()); // move backward
        }

        // check if animal is out of map (if is move back to previous position)
        if (!newAnimalPosition.precedes(TOP_RIGHT_CORNER) || !newAnimalPosition.follows(BOTTOM_LEFT_CORNER)) {
            newAnimalPosition = animalPosition;
        }

        // set animal position
        animalPosition = newAnimalPosition;
    }
}
