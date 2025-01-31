package projekt.model.maps;

import projekt.model.*;
import projekt.model.random.RandomGrassPositionGenerator;

import java.util.*;

import static projekt.model.util.AnimalUtils.setDominantAnimal;

public abstract class AbstractMap {
    protected final UUID uuid = UUID.randomUUID();
    protected final HashMap<Vector2d, Grass> grassesMap = new HashMap<>();
    protected final Map<Vector2d, List<Animal>> animalsMap = new HashMap<>();
    protected final Vector2d bottomLeftCorner = new Vector2d(0, 0);
    protected final Vector2d topRightCorner;
    protected final Boundary mapBoundary;
    protected final Boundary equatorMapBoundary; // map?
    protected final Vector2d equatorLowerLeft;
    protected final Vector2d equatorUpperRight;
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected final int grassGrowthCount;
    protected final int grassEnergy;

    public AbstractMap(int width, int height, int startGrassCount, int grassGrowthCount, int grassEnergy) {
        topRightCorner = new Vector2d(width - 1, height - 1);
        mapBoundary = new Boundary(bottomLeftCorner, topRightCorner);
        this.grassGrowthCount = grassGrowthCount;
        this.grassEnergy = grassEnergy;

        equatorLowerLeft = new Vector2d(0, Math.round((float) topRightCorner.getY() / 2 - (float) topRightCorner.getY() / 10));
        equatorUpperRight = new Vector2d(topRightCorner.getX(), Math.round((float) topRightCorner.getY() / 2 + (float) topRightCorner.getY() / 10));
        equatorMapBoundary = new Boundary(equatorLowerLeft, equatorUpperRight);

        RandomGrassPositionGenerator grassRandomPositionGenerator = new RandomGrassPositionGenerator(topRightCorner, startGrassCount, grassesMap, equatorLowerLeft, equatorUpperRight);
        for (Vector2d grassPosition : grassRandomPositionGenerator) {
            grassesMap.put(grassPosition, new Grass(grassPosition, grassEnergy));
        }
    }

    public UUID getMapUUID() {
        return uuid;
    }

    public Boundary getMapBoundary() {
        return mapBoundary;
    }

    public int getMapArea() {
        return (topRightCorner.getX() + 1) * (topRightCorner.getY() + 1);
    }

    public Boundary getEquatorBoundary() {
        return equatorMapBoundary;
    }

    public HashMap<Vector2d, Grass> getGrassesMap() {
        return new HashMap<>(grassesMap);
    }

    public Map<Vector2d, List<Animal>> getAnimalsMap() {
        return new HashMap<>(animalsMap);
    }

    public List<WorldElement> getElements() {
        ArrayList<WorldElement> elements = new ArrayList<>();
        Map<Vector2d, Animal> dominantAnimalsMap = new HashMap<>();

        for (Map.Entry<Vector2d, List<Animal>> entry : animalsMap.entrySet()) {
            Vector2d position = entry.getKey();
            List<Animal> animalsAtPosition = entry.getValue();
            Animal dominantAnimal = setDominantAnimal(animalsAtPosition);
            dominantAnimalsMap.put(position, dominantAnimal);
        }

        elements.addAll(dominantAnimalsMap.values());
        elements.addAll(grassesMap.values());
        return elements;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    public void listenerObserver() { // ? listener i observer to to samo; public?
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this);
        }
    }

    public void place(Animal animal) {
        animalsMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal);
    }

    public void removeGrass(Vector2d position) {
        grassesMap.remove(position);
    }

    public void addNewGrasses() {
        RandomGrassPositionGenerator grassRandomPositionGenerator = new RandomGrassPositionGenerator(topRightCorner, grassGrowthCount, grassesMap, equatorLowerLeft, equatorUpperRight);
        for (Vector2d grassPosition : grassRandomPositionGenerator) {
            grassesMap.put(grassPosition, new Grass(grassPosition, grassEnergy));
        }
    }

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
        animal.move(topRightCorner);
        subtractMoveEnergy(animal, previousAnimalPosition);

        animalsMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal);
    }

    protected abstract void subtractMoveEnergy(Animal animal, Vector2d previousAnimalPosition);

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

    public Animal dominantAnimalAtPosition(Vector2d position) {
        List<Animal> animalsAtPosition = animalsMap.get(position);
        if (animalsAtPosition != null) {
            return setDominantAnimal(animalsAtPosition);
        }
        return null;
    }

    public List<Animal> animalAt(Vector2d position) {
        return animalsMap.get(position);
    }
}
