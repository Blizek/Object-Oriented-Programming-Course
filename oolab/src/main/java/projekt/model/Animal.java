package projekt.model;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement{
    private Vector2d animalPosition;
    private int animalEnergy;
    private Direction animalDirection = Direction.NORTH;
    private final List<Integer> genome;
    private int genomePlace = 0;

    public Animal(Vector2d animalPosition, int animalEnergy, List<Integer> genome) {
        this.animalPosition = animalPosition;
        this.animalEnergy = animalEnergy;
        this.genome = genome;
    }

    @Override
    public Vector2d getPosition() {
        return animalPosition;
    }
    @Override
    public int getEnergy(){
        return animalEnergy;
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
    }

    public void eat(int energy){
        animalEnergy += energy;
    }
}
