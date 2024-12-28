package projekt.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final Vector2d topRightCorner;
    private final int grassCount;
    private final List<Vector2d> allPositions = new ArrayList<>();
    private final Random random = new Random();

    public RandomPositionGenerator(Vector2d topRightCorner, int grassCount) {
        this.topRightCorner = topRightCorner;
        this.grassCount = grassCount;

        for (int i = 0; i < this.topRightCorner.getX(); i++) {
            for (int j = 0; j < this.topRightCorner.getY(); j++) {
                allPositions.add(new Vector2d(i, j));
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
                int randomIndex = random.nextInt(allPositions.size());
                Vector2d tempLastVector2d = allPositions.getLast();
                Vector2d randomPosition = allPositions.get(randomIndex);
                allPositions.set(allPositions.size() - 1, randomPosition);
                allPositions.set(randomIndex, tempLastVector2d);
                currentGrassCounter++;
                return allPositions.removeLast();
            }
        };
    }
}
