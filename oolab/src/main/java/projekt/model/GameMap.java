package projekt.model;


import agh.ics.oop.model.IncorrectPositionException;
import projekt.model.util.MapVisualizer;

import java.util.*;


public class GameMap {
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final UUID id = UUID.randomUUID();

    private final HashMap<Vector2d, Grass> grassesMap;

    private final Map<Vector2d, Animal> animalsMap = new HashMap<>();

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

        RandomPositionGenerator grassRandomPositionGenerator = new RandomPositionGenerator(topRightCorner, builder.startGrassCount);
        for(Vector2d grassPosition : grassRandomPositionGenerator) {
            grassesMap.put(grassPosition, new Grass(grassPosition,builder.grassEnergy));
        }
    }

    public void place(Animal animal) {
        animalsMap.put(animal.getPosition(), animal);
    }

    public void removeDeadAnimal(Animal animal) {
        animalsMap.remove(animal.getPosition(), animal);
    }


    public void move(Animal animal) {
        Vector2d previousAnimalPosition = animal.getPosition();
        Direction previousAnimalDirection = animal.getDirection();
        animalsMap.remove(previousAnimalPosition, animal);
        animal.move();
        animal.postMoveEnergyUpdate();
        animalsMap.put(animal.getPosition(), animal);
        if (!previousAnimalDirection.equals(animal.getDirection())) {
            System.out.println("Animal changed direction from %s to %s".formatted(previousAnimalDirection, animal.getDirection()));
        }
        if (!previousAnimalPosition.equals(animal.getPosition())){
            System.out.println("Animal moved from %s to %s".formatted(previousAnimalPosition, animal.getPosition()));
        }
        System.out.println("Animal's energy: " + animal.getEnergy());
        System.out.println(mapVisualizer.draw(bottomLeftCorner, topRightCorner));
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public WorldElement objectAt(Vector2d position) {
        WorldElement animalAtPosition = animalsMap.get(position);
        Grass grassAtPosition = grassesMap.get(position);
        if (animalAtPosition != null) {
            return animalAtPosition;
        }
        return grassAtPosition;
    }

    public Boundary getMapBoundary() {
        return new Boundary(bottomLeftCorner, topRightCorner);
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

}
