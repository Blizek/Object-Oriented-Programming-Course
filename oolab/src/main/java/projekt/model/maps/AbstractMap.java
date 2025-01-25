package projekt.model.maps;

import projekt.model.*;
import projekt.model.random.RandomGrassPositionGenerator;
import projekt.model.util.MapVisualizer;

import javax.swing.text.Position;
import java.util.*;

import static projekt.model.util.AnimalUtils.setDominantAnimal;

public abstract class AbstractMap {
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected final UUID id = UUID.randomUUID();
    protected final HashMap<Vector2d, Grass> grassesMap = new HashMap<>();
    protected final Map<Vector2d, List<Animal>> animalsMap = new HashMap<>();
    protected final Vector2d bottomLeftCorner = new Vector2d(0, 0);
    protected final Vector2d topRightCorner;
    protected final Boundary mapBoundary;
    protected final Vector2d equatorLowerLeft;
    protected final Vector2d equatorUpperRight;
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected final int grassGrowthCount;
    protected final int grassEnergy;

    public AbstractMap(int width, int height, int startGrassCount, int grassGrowthCount, int grassEnergy) {
        topRightCorner = new Vector2d(width, height);;
        mapBoundary = new Boundary(bottomLeftCorner, topRightCorner);
        this.grassGrowthCount = grassGrowthCount;
        this.grassEnergy = grassEnergy;

        equatorLowerLeft = new Vector2d(0, Math.round((float) topRightCorner.getY() / 2 - (float) topRightCorner.getY() /10));
        equatorUpperRight = new Vector2d(topRightCorner.getX(), Math.round((float) topRightCorner.getY() / 2 + (float) topRightCorner.getY() /10));

        RandomGrassPositionGenerator grassRandomPositionGenerator = new RandomGrassPositionGenerator(topRightCorner, startGrassCount, grassesMap, equatorLowerLeft, equatorUpperRight);
        for(Vector2d grassPosition : grassRandomPositionGenerator) {
            grassesMap.put(grassPosition, new Grass(grassPosition, grassEnergy));
        }
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    public void listenerObserver() {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this);
        }
    }

    public Boundary getMapBoundary() {
        return mapBoundary;
    }

    public int getMapArea() {
        return (topRightCorner.getX() + 1) * (topRightCorner.getY() + 1);
    }

    public HashMap<Vector2d, Grass> getGrassesMap() {
        return new HashMap<>(grassesMap);
    }

    public Map<Vector2d, List<Animal>> getAnimalsMap() {
        return new HashMap<>(animalsMap);
    }

    public List<WorldElement> getElements(){
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

    public void place(Animal animal) {
        animalsMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal);
    }

    public void removeGrass(Vector2d position) {
        grassesMap.remove(position);
    }

    public void addNewGrasses() {
        RandomGrassPositionGenerator grassRandomPositionGenerator = new RandomGrassPositionGenerator(topRightCorner, grassGrowthCount, grassesMap, equatorLowerLeft, equatorUpperRight);
        for(Vector2d grassPosition : grassRandomPositionGenerator) {
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
        Direction previousAnimalDirection = animal.getDirection();
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

//        if (!previousAnimalDirection.equals(animal.getDirection())) {
//            System.out.printf("Animal changed direction from %s to %s%n", previousAnimalDirection, animal.getDirection());
//        }
//        if (!previousAnimalPosition.equals(animal.getPosition())){
//            System.out.printf("Animal moved from %s to %s%n", previousAnimalPosition, animal.getPosition());
//        }
//        System.out.println("Animal's energy: " + animal.getEnergy());
        // System.out.println(mapVisualizer.draw(bottomLeftCorner, topRightCorner));
    }

    protected abstract void subtractMoveEnergy(Animal animal, Vector2d previousAnimalPosition);

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
