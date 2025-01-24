package projekt.model.maps;

import projekt.model.*;
import projekt.model.random.RandomGrassPositionGenerator;
import projekt.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractMap {
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final UUID id = UUID.randomUUID();

    protected final HashMap<Vector2d, Grass> grassesMap = new HashMap<>();

    protected final Map<Vector2d, List<Animal>> animalsMap = new HashMap<>();

    protected final Vector2d bottomLeftCorner = new Vector2d(0, 0);

    public abstract Boundary getMapBoundary();

    public abstract int getMapArea();

    public HashMap<Vector2d, Grass> getGrassesMap() {
        return new HashMap<>(grassesMap);
    }

    public Map<Vector2d, List<Animal>> getAnimalsMap() {
        return new HashMap<>(animalsMap);
    }

    public void place(Animal animal) {
        animalsMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal);
    }

    public void removeGrass(Vector2d position) {
        grassesMap.remove(position);
    }

    public abstract void addNewGrasses();

    public void removeDeadAnimal(Animal animal) {
        List<Animal> animals = animalsMap.get(animal.getPosition());
        if (animals != null) {
            animals.remove(animal);
            if (animals.isEmpty()) {
                animalsMap.remove(animal.getPosition());
            }
        }
    }


    public void move(Animal animal) {
        Vector2d previousAnimalPosition = animal.getPosition();

        List<Animal> animalsAtPreviousPosition = animalsMap.get(previousAnimalPosition);
        if (animalsAtPreviousPosition != null) {
            animalsAtPreviousPosition.remove(animal);
            if (animalsAtPreviousPosition.isEmpty()) {
                animalsMap.remove(previousAnimalPosition);
            }
        }
    }


    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }


    public List<WorldElement> objectAt(Vector2d position) {
        List<WorldElement> elementsAtPosition = new ArrayList<>();
        List<Animal> animalsAtPosition = animalsMap.get(position);
        if (animalsAtPosition != null) {
            elementsAtPosition.addAll(animalsAtPosition);
        }
        Grass grassAtPosition = grassesMap.get(position);
        if (grassAtPosition != null) {
            elementsAtPosition.add(grassAtPosition);
        }
        return elementsAtPosition;
    }

    public List<Animal> animalAt(Vector2d position) {
        return animalsMap.get(position);
    }


    public UUID getId() {
        return id;
    }
}
