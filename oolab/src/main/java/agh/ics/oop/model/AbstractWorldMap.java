package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animalsMap = new HashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);

    @Override
    public boolean canMoveTo(Vector2d position){
        return !animalsMap.containsKey(position);
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())){
            animalsMap.put(animal.getPosition(), animal);
        } else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d animalStartPosition = animal.getPosition();
        animal.move(direction, this);
        animalsMap.remove(animalStartPosition);
        Vector2d animalEndPosition = animal.getPosition();
        animalsMap.put(animalEndPosition, animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    /**
     * Method to get animal which is on passed position
     * @param position The position of the animal.
     * @return Animal object if some animal is on the position, otherwise null
     */
    @Override
    public WorldElement objectAt(Vector2d position) {
        return animalsMap.get(position);
    }

    @Override
    public List<WorldElement> getElements() {
        return List.copyOf(animalsMap.values());
    }

    @Override
    public abstract Boundary getCurrentBounds();

    @Override
    public String toString() {
        Boundary mapBounds = getCurrentBounds();
        Vector2d lowerLeftBound = mapBounds.lowerLeft();
        Vector2d upperRightBound = mapBounds.upperRight();
        return mapVisualizer.draw(lowerLeftBound, upperRightBound);
    }
}
