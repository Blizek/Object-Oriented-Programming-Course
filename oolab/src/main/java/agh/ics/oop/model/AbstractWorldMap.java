package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    private final UUID id = UUID.randomUUID();
    protected final Map<Vector2d, Animal> animalsMap = new HashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected final List<MapChangeListener> observers = new ArrayList<>();

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void listenerObserver(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return !animalsMap.containsKey(position);
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())){
            animalsMap.put(animal.getPosition(), animal);
            listenerObserver("Animal was placed at " + animal.getPosition());
        } else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d animalStartPosition = animal.getPosition();
        MapDirection animalStartDirection = animal.getAnimalDirection();
        animal.move(direction, this);
        animalsMap.remove(animalStartPosition);
        Vector2d animalEndPosition = animal.getPosition();
        MapDirection animalEndDirection = animal.getAnimalDirection();
        animalsMap.put(animalEndPosition, animal);

        if (!animalStartDirection.equals(animalEndDirection)) {
            listenerObserver("Animal changed direction from %s to %s".formatted(animalStartDirection, animalEndDirection));
        } else if (!animalStartPosition.equals(animalEndPosition)){
            listenerObserver("Animal moved from %s to %s".formatted(animalStartPosition, animalEndPosition));
        }
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
    public List<WorldElement> getElements(){
        return new ArrayList<>(animalsMap.values());
    }

    @Override
    public abstract Boundary getCurrentBounds();

    @Override
    public String toString() {
        Boundary mapBounds = getCurrentBounds();
        return mapVisualizer.draw(mapBounds.lowerLeft(), mapBounds.upperRight());
    }

    @Override
    public UUID getId() {
        return id;
    }
}
