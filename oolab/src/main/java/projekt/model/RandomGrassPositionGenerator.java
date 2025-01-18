package projekt.model;

import java.util.*;

public class RandomGrassPositionGenerator implements Iterable<Vector2d> {
    private final Vector2d topRightCorner;
    private final int grassCount;
    private final List<Vector2d> outsideJungleFreePositions = new ArrayList<>();
    private final Random random = new Random();
    private final Vector2d equatorLowerLeft;
    private final Vector2d equatorUpperRight;
    private final List<Vector2d> inJungleFreePositions = new ArrayList<>();

    public RandomGrassPositionGenerator(Vector2d topRightCorner, int grassCount, HashMap<Vector2d, Grass> occupiedPositions, Vector2d equatorLowerLeft, Vector2d equatorUpperRight) {
        this.topRightCorner = topRightCorner;
        this.grassCount = grassCount;
        this.equatorLowerLeft = equatorLowerLeft;
        this.equatorUpperRight = equatorUpperRight;

        for (int i = 0; i < this.topRightCorner.getX(); i++) {
            for (int j = 0; j < this.topRightCorner.getY(); j++) {
                Vector2d currentPosition = new Vector2d(i, j);
                if (!occupiedPositions.containsKey(currentPosition)) {
                    if (isPositionInJungle(currentPosition)) {
                        inJungleFreePositions.add(currentPosition);
                    } else {
                        outsideJungleFreePositions.add(currentPosition);
                    }
                }
            }
        }
    }

    public Iterator<Vector2d> iterator() {
        return new Iterator<Vector2d>() {
            private int currentGrassCounter = 0;
            @Override
            public boolean hasNext() {
                return currentGrassCounter < grassCount;
            }

            @Override
            public Vector2d next() {
                int randomGrownPlace = random.nextInt(10);
                List<List<Vector2d>> freePositions = new ArrayList<>();
                freePositions.add(outsideJungleFreePositions);
                freePositions.add(inJungleFreePositions);

                int listIndex;
                listIndex = (randomGrownPlace <= 1) ? 0 : 1;

                List<Vector2d> grownPlaces = freePositions.get(listIndex);
                int randomIndex = random.nextInt(grownPlaces.size());
                Vector2d tempLastVector2d = grownPlaces.getLast();
                Vector2d randomPosition = grownPlaces.get(randomIndex);
                grownPlaces.set(freePositions.size() - 1, randomPosition);
                grownPlaces.set(randomIndex, tempLastVector2d);
                currentGrassCounter++;
                return outsideJungleFreePositions.removeLast();
            }
        };
    }

    private boolean isPositionInJungle(Vector2d position) {
        return position.follows(equatorLowerLeft) && position.precedes(equatorUpperRight);
    }
}
