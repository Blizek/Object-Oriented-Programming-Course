package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d topRightCorner;
    private final Vector2d bottomLeftCorner;
    private final Boundary mapBoundary;

    public RectangularMap(int mapWidth, int mapHeight) {
        // map has x values from 0 to mapWidth - 1 and y values from 0 to mapHeight - 1
        topRightCorner = new Vector2d(mapWidth - 1, mapHeight - 1);
        bottomLeftCorner = new Vector2d(0, 0);
        mapBoundary = new Boundary(bottomLeftCorner, topRightCorner);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && position.follows(bottomLeftCorner) && position.precedes(topRightCorner);
    }

    @Override
    public Boundary getCurrentBounds() {
        return mapBoundary;
    }
}
