package projekt.model;

public enum Direction {
    NORTH(new Vector2d(0, 1), "N"),
    NORTHEAST(new Vector2d(1, 1), "NE"),
    EAST(new Vector2d(1, 0), "E"),
    SOUTHEAST(new Vector2d(1, -1), "SE"),
    SOUTH(new Vector2d(0, -1), "S"),
    SOUTHWEST(new Vector2d(-1, -1), "SW"),
    WEST(new Vector2d(-1, 0), "W"),
    NORTHWEST(new Vector2d(-1, 1), "NW");

    private final Vector2d unitVector;
    private final String directionName;

    Direction(Vector2d unitVector, String directionName) {
        this.unitVector = unitVector;
        this.directionName = directionName;
    }

    public Vector2d toUnitVector() {
        return unitVector;
    }
    public String toString() {
        return directionName;
    }
}
