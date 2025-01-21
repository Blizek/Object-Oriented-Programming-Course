package projekt.model.maps;


import projekt.model.*;
import projekt.model.random.RandomGrassPositionGenerator;
import projekt.model.util.MapVisualizer;

import java.util.*;


public class EarthMap {
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final UUID id = UUID.randomUUID();

    protected final HashMap<Vector2d, Grass> grassesMap;

    protected final Map<Vector2d, List<Animal>> animalsMap = new HashMap<>();

    private final Boundary mapBoundary;

    protected final Vector2d bottomLeftCorner = new Vector2d(0, 0);
    protected final Vector2d topRightCorner;

    private final Vector2d equatorLowerLeft;
    private final Vector2d equatorUpperRight;

    private final int grassGrowthCount;
    private final int grassEnergy;

    public EarthMap(int width, int height, int startGrassCount, int grassGrowthCount, int grassEnergy) {
        grassesMap = new HashMap<>();
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

    public Boundary getMapBoundary() {
        return mapBoundary;
    }

    public int getMapArea() {
        return topRightCorner.getX() * topRightCorner.getY();
    }

    public HashMap<Vector2d, Grass> getGrassesMap() {
        return new HashMap<>(grassesMap);
    }

    public Map<Vector2d, List<Animal>> getAnimalsMap() {
        return new HashMap<>(animalsMap);
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(bottomLeftCorner, topRightCorner);
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
        animal.moveEnergyUpdate(1.0f);

        animalsMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal);

        if (!previousAnimalDirection.equals(animal.getDirection())) {
            System.out.printf("Animal changed direction from %s to %s%n", previousAnimalDirection, animal.getDirection());
        }
        if (!previousAnimalPosition.equals(animal.getPosition())){
            System.out.printf("Animal moved from %s to %s%n", previousAnimalPosition, animal.getPosition());
        }
        System.out.println("Animal's energy: " + animal.getEnergy());
        System.out.println(mapVisualizer.draw(bottomLeftCorner, topRightCorner));
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
