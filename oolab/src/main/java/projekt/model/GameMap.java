package projekt.model;


import projekt.model.random.RandomGrassPositionGenerator;
import projekt.model.util.MapVisualizer;

import java.util.*;


public class GameMap {
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final UUID id = UUID.randomUUID();

    private final HashMap<Vector2d, Grass> grassesMap;

    private final Map<Vector2d, List<Animal>> animalsMap = new HashMap<>();

    private final Boundary mapBoundary;

    private final Vector2d bottomLeftCorner = new Vector2d(0, 0);
    private final Vector2d topRightCorner;

    private final Vector2d equatorLowerLeft;
    private final Vector2d equatorUpperRight;

    private final int grassGrowthCount;

    private GameMap(Builder builder){
        grassesMap = new HashMap<>();
        topRightCorner = builder.topRightCorner;
        mapBoundary = new Boundary(bottomLeftCorner, topRightCorner);
        grassGrowthCount = builder.grassGrowthCount;

        equatorLowerLeft = new Vector2d(0, Math.round((float) topRightCorner.getY() / 2 - (float) topRightCorner.getY() /10));
        equatorUpperRight = new Vector2d(topRightCorner.getX(), Math.round((float) topRightCorner.getY() / 2 + (float) topRightCorner.getY() /10));

        RandomGrassPositionGenerator grassRandomPositionGenerator = new RandomGrassPositionGenerator(topRightCorner, builder.startGrassCount, grassesMap, equatorLowerLeft, equatorUpperRight);
        for(Vector2d grassPosition : grassRandomPositionGenerator) {
            grassesMap.put(grassPosition, new Grass(grassPosition,builder.grassEnergy));
        }
    }

    public Boundary getMapBoundary() {
        return mapBoundary;
    }

    public HashMap<Vector2d, Grass> getGrassesMap() {
        return grassesMap;
    }

    public Map<Vector2d, List<Animal>> getAnimalsMap() {
        return animalsMap;
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(bottomLeftCorner, topRightCorner);
    }

    public static class Builder{
        private Vector2d topRightCorner;
        private int startGrassCount;
        private int grassGrowthCount;
        private int grassEnergy;

        public Builder setTopRightCorner(int width, int height){
            this.topRightCorner = new Vector2d(width, height);
            return this;
        }

        public Builder setStartGrassCount(int startGrassCount){
            this.startGrassCount = startGrassCount;
            return this;
        }

//        public Builder setGrassGrowthCount(int grassGrowthCount){
//            this.grassGrowthCount = grassGrowthCount;
//            return this;
//        }
//        public Builder setGrassEnergy(int grassEnergy){
//            this.grassEnergy = grassEnergy;
//            return this;
//        }

        public GameMap build(){
            return new GameMap(this);
        }
    }

    public void place(Animal animal) {
        animalsMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal);
    }

    public void removeGrass(Vector2d position) {
        grassesMap.remove(position);
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

        animal.move();
        animal.postMoveEnergyUpdate();

        // tutaj ustaw zwierzaka

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


}
