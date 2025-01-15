package projekt.model;


import java.util.*;


public class GameMap {
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

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(topRightCorner, builder.startGrassCount);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grassesMap.put(grassPosition, new Grass(grassPosition,builder.grassEnergy));
        }
    }
    public void move(Animal animal) {
        Vector2d previousAnimalPosition = animal.getPosition();
        animal.move();
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

        public Builder setGrassGrowthCount(int grassGrowthCount){
            this.grassGrowthCount = grassGrowthCount;
            return this;
        }
        public Builder setGrassEnergy(int grassEnergy){
            this.grassEnergy = grassEnergy;
            return this;
        }

        public GameMap build(){
            return new GameMap(this);
        }
    }

}
