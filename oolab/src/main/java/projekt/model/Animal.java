package projekt.model;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement {
    private final List<Integer> genome;
    private final Animal mother;
    private final Animal father;
    private final ArrayList<Animal> children = new ArrayList<>();
    private final int minEnergyToFullAnimal;
    private Vector2d animalPosition;
    private int animalEnergy;
    private Direction animalDirection = Direction.values()[(int) (Math.random() * 8)];
    private int genomePlace;
    private int descendants = 0;
    private int daysLived = 0;
    private int eatingCounter = 0;

    public Animal(Vector2d animalPosition, int animalEnergy, List<Integer> genome, Animal mother, Animal father, int minEnergyToFullAnimal) {
        this.animalPosition = animalPosition;
        this.animalEnergy = animalEnergy;
        this.genome = genome;
        this.mother = mother;
        this.father = father;
        this.genomePlace = (int) (Math.random() * genome.size());
        this.minEnergyToFullAnimal = minEnergyToFullAnimal;
    }

    public int getDaysLived() {
        return daysLived;
    }

    @Override
    public Vector2d getPosition() {
        return animalPosition;
    }

    @Override
    public int getEnergy() {
        return animalEnergy;
    }

    @Override
    public String getImageName() {
        if (animalEnergy > 1000 * minEnergyToFullAnimal) {
            return "god-tier-ultra-saiyan-monkey.png"; // anty-copyright
        } else if (animalEnergy > 20 * minEnergyToFullAnimal) {
            return "ultra-saiyan-monkey.png";
        } else if (animalEnergy > 10 * minEnergyToFullAnimal) {
            return "strong-great-monkey.png";
        } else if (animalEnergy > 5 * minEnergyToFullAnimal) {
            return "strong-monkey.png";
        } else if (animalEnergy > minEnergyToFullAnimal) {
            return "full-monkey.png";
        } else if (animalEnergy > 0.5 * minEnergyToFullAnimal) {
            return "little-tired-monkey.png";
        } else if (animalEnergy > 0) {
            return "tired-monkey.png";
        } else {
            return "dead-monkey.png";
        }
    }

    public int getChildrenCount() {
        return children.size();
    }

    public Animal getMother() {
        return mother;
    }

    public Animal getFather() {
        return father;
    }

    public ArrayList<Animal> getChildren() {
        return children;
    }

    public void setChildren(Animal child) {
        children.add(child);
    }

    public Direction getDirection() {
        return animalDirection;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public int getActualGenome() {
        return genome.get(genomePlace);
    }

    public List<Integer> getAnimalFullGenome() {
        return List.copyOf(genome);
    }

    public int getEatingCounter() {
        return eatingCounter;
    }

    public int getDescendants() {
        return descendants;
    }

    public void setAnimalPosition(int x, int y) {
        animalPosition = new Vector2d(x, y);
    }

    public void moveEnergyUpdate(float energyLossMultiplier) {
        this.animalEnergy -= (int) (10 * energyLossMultiplier);
    }

    public void newDescendant() {
        descendants += 1;
    }

    public void loseEnergy(int energy) {
        animalEnergy -= energy;
    }

    public void move(Vector2d topRightCorner) {
        changeDirection();
        Vector2d newAnimalPosition = animalPosition.add(animalDirection.toUnitVector());
        if (newAnimalPosition.getY() < 0 || newAnimalPosition.getY() > topRightCorner.getY()) {
            animalDirection = Direction.getOppositeDirection(animalDirection);
        } else if (newAnimalPosition.getX() < 0) {
            animalPosition = animalPosition.add(new Vector2d(topRightCorner.getX(), animalDirection.toUnitVector().getY()));
        } else if (newAnimalPosition.getX() > topRightCorner.getX()) {
            animalPosition = animalPosition.subtract(new Vector2d(topRightCorner.getX(), -animalDirection.toUnitVector().getY()));
        } else {
            animalPosition = newAnimalPosition;
        }
        daysLived += 1;
    }

    public void changeDirection() {
        int directionValue = animalDirection.ordinal();
        int newDirectionValue = (directionValue + genome.get(genomePlace)) % 8;
        animalDirection = Direction.values()[newDirectionValue];
        genomePlace = (genomePlace + 1) % genome.size();
    }

    public void eat(int energy) {
        eatingCounter++;
        animalEnergy += energy;
    }

    public void setDirection(Direction direction) {
        this.animalDirection = direction;
    }
}
