package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final int grassCount;
    private final List<Vector2d> allPositions = new ArrayList<>();
    private final Random random = new Random();

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.grassCount = grassCount;

        for (int i = 0; i < this.maxWidth; i++) {
            for (int j = 0; j < this.maxHeight; j++) {
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
