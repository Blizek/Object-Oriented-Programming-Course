package agh.ics.oop.model;

/**
 * Enum class that describes all geographical directions
 */
public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public String toString() {
        return switch (this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
        };
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
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
        };
    }
}
