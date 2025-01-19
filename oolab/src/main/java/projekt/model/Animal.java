package projekt.model;

import java.util.List;
import java.util.ArrayList;

public class Animal implements WorldElement{
    private Vector2d animalPosition;
    private int animalEnergy;
    private Direction animalDirection = Direction.values()[(int)(Math.random() * 8)];
    private final List<Integer> genome;
    private int genomePlace;
    private Animal mother;
    private Animal father;
    private ArrayList<Animal> children = new ArrayList<>();
    private int descendants = 0;
    private int daysLived = 0;

    public Animal(Vector2d animalPosition, int animalEnergy, List<Integer> genome, Animal mother, Animal father) {
        this.animalPosition = animalPosition;
        this.animalEnergy = animalEnergy;
        this.genome = genome;
        this.mother = mother;
        this.father = father;
        this.genomePlace = (int)(Math.random() * genome.size());
    }

    @Override
    public Vector2d getPosition() {
        return animalPosition;
    }

    public void setAnimalPosition(int x, int y) {
        animalPosition = new Vector2d(x, y);
    }

    @Override
    public int getEnergy(){
        return animalEnergy;
    }

    public void postMoveEnergyUpdate() {
        this.animalEnergy -= 2;
    }

    public Animal getMother(){
        return mother;
    }

    public Animal getFather(){
        return father;
    }

    public void newDescendant(){
        descendants += 1;
    }

    public ArrayList<Animal> getChildren(){
        return children;
    }

    public void setChildren(Animal child){
        children.add(child);
    }

    public Direction getDirection() {
        return animalDirection;
    }

    public void loseEnergy(int energy){
        animalEnergy -= energy;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public void changeDirection(){
        int directionValue = animalDirection.ordinal();
        int newDirectionValue = (directionValue + genome.get(genomePlace)) % 8;
        animalDirection = Direction.values()[newDirectionValue];
        genomePlace = (genomePlace + 1) % genome.size();
    }

    public void move(Vector2d topRightCorner){
        changeDirection();
        Vector2d newAnimalPosition = animalPosition.add(animalDirection.toUnitVector());
        if (newAnimalPosition.getY() < 0 || newAnimalPosition.getY() > topRightCorner.getY()){
            animalDirection = Direction.getOppositeDirection(animalDirection);
        }
        else if (newAnimalPosition.getX() < 0) {
            animalPosition = animalPosition.add(new Vector2d(topRightCorner.getX(), 0));
        }
        else if (newAnimalPosition.getX() > topRightCorner.getX()) {
            animalPosition = animalPosition.subtract(new Vector2d(topRightCorner.getX(), 0));
        } else {
            animalPosition = newAnimalPosition;
        }
        daysLived += 1;
    }

    public void eat(int energy){
        System.out.println("Energia przed jedzeniem " + animalEnergy);
        animalEnergy += energy;
        System.out.println("Eenrgia po jedzeniu " + animalEnergy);
    }

    @Override
    public String toString() {
        return switch (animalDirection) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }
}
