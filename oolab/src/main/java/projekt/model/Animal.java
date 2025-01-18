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

    @Override
    public int getEnergy(){
        return animalEnergy;
    }

    public void postMoveEnergyUpdate() {
        this.animalEnergy -= 10;
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

    public List<Integer> getGenome() {
        return genome;
    }

    public void changeDirection(){
        int directionValue = animalDirection.ordinal();
        int newDirectionValue = (directionValue + genome.get(genomePlace)) % 8;
        animalDirection = Direction.values()[newDirectionValue];
        genomePlace = (genomePlace + 1) % genome.size();
    }

    public void move(){
        changeDirection();
        animalPosition = animalPosition.add(animalDirection.toUnitVector());
        daysLived += 1;
    }

    public void eat(int energy){
        animalEnergy += energy;
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
