package agh.ics.oop.model;

/**
 * Enum class that describes all geographical directions
 */
public enum MapDirection {
    NORTH(new Vector2d(0, 1), "Północ"),
    SOUTH(new Vector2d(0, -1), "Południe"),
    WEST(new Vector2d(-1, 0), "Zachód"),
    EAST(new Vector2d(1, 0), "Wschód");

    private final Vector2d unitVector;
    private final String directionName;

    MapDirection(Vector2d unitVector, String directionName) {
        this.unitVector = unitVector;
        this.directionName = directionName;
    }


    public String toString() {
        return directionName;
    }

    /**
     * Method to return next geographical direction clockwise
     * @return MapDirection that is next geographical direction clockwise
     */
    public MapDirection next() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    /**
     * Method to return next geographical direction counterclockwise
     * @return MapDirection that is next geographical direction counterclockwise
     */
    public MapDirection previous() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }

    /**
     * Method to return unit vector od direction
     * @return Vector2d object that is unit vector of direction
     */
    public Vector2d toUnitVector() {
        return unitVector;
    }
}
