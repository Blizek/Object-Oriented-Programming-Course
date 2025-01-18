package projekt;

import projekt.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Simulation implements Runnable {
    private final List<Animal> animalsList = new ArrayList<>(); // list of all animals
    private final List<Animal> newDeadAnimalsList = new ArrayList<>(); // list of all animals
    private final HashMap<Vector2d, Animal> animalsMap;

    private final GameMap gameMap; // map of the game
    private final int eatenGrassEnergy;
    private final int grassGrownAmount;
    private final int startAnimalsAmount;
    private final int startAnimalEnergy;
    private final int minEnergyToFullAnimal;
    private final int sexEnergyCost;
    private final int minMutationAmount;
    private final int maxMutationAmount;
    private final int animalGenomeLength;

    private Simulation(Builder builder) {
        gameMap = builder.map;
        eatenGrassEnergy = builder.eatenGrassEnergy;
        grassGrownAmount = builder.grassGrownAmount;
        startAnimalsAmount = builder.startAnimalsAmount;
        startAnimalEnergy = builder.startAnimalEnergy;
        minEnergyToFullAnimal = builder.minEnergyToFullAnimal;
        sexEnergyCost = builder.sexEnergyCost;
        minMutationAmount = builder.minMutationAmount;
        maxMutationAmount = builder.maxMutationAmount;
        animalGenomeLength = builder.animalGenomeLength;

        animalsMap = new HashMap<>();

        Boundary mapBoundary = gameMap.getMapBoundary();
        Vector2d bottomLeftCorner = mapBoundary.lowerLeft();
        Vector2d topRightCorner = mapBoundary.upperRight();

        RandomPositionGenerator animalsRandomPositionGenerator = new RandomPositionGenerator(topRightCorner, startAnimalsAmount);
        for(Vector2d animalPosition : animalsRandomPositionGenerator) {
            Animal newAnimal = new AnimalMaker(minMutationAmount, maxMutationAmount, animalGenomeLength, startAnimalEnergy).make(animalPosition);
            animalsMap.put(animalPosition, newAnimal);
            gameMap.place(newAnimal);
            animalsList.add(newAnimal);
        }
    }

    public List<Animal> getAnimalsList() {
        return List.copyOf(animalsList);
    }

    /**
     * Method to run simulation for every animal on list
     */
    @Override
    public void run(){
//        if (animalsList.isEmpty()) {
//            return;
//        }
//
//        int animalsCount = animalsList.size();
//        try {
//            for (Animal actualAnimal: animalsList) {
//                gameMap.move(actualAnimal); // move this animal on the map
//                Thread.sleep(1000);
//            }
//        } catch (InterruptedException e) {
//            System.out.println(e.getMessage());
//        }
        int actualAnimalsCount = animalsList.size();

        try {
            while (actualAnimalsCount > 0) {
                System.out.println("New day");
                for (Animal animal : animalsList) {
                    gameMap.move(animal);
                    Thread.sleep(0);
                }

                for (Animal animal: animalsList) {
                    if (animal.getEnergy() <= 0) {
                        newDeadAnimalsList.add(animal);
                        gameMap.removeDeadAnimal(animal);
                    }
                }

                for (Animal animal: newDeadAnimalsList) {
                    animalsList.remove(animal);
                }
                newDeadAnimalsList.clear();

                actualAnimalsCount = animalsList.size();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static class Builder {
        private GameMap map;
        private int eatenGrassEnergy;
        private int grassGrownAmount;
        private int startAnimalsAmount;
        private int startAnimalEnergy;
        private int minEnergyToFullAnimal;
        private int sexEnergyCost;
        private int minMutationAmount;
        private int maxMutationAmount;
        private int animalGenomeLength;

        public Builder setMap(GameMap map) {
            this.map = map;
            return this;
        }

        public Builder setEatenGrassEnergy(int eatenGrassEnergy) {
            this.eatenGrassEnergy = eatenGrassEnergy;
            return this;
        }

        public Builder setGrassGrownAmount(int grassGrownAmount) {
            this.grassGrownAmount = grassGrownAmount;
            return this;
        }

        public Builder setStartAnimalsAmount(int startAnimalsAmount) {
            this.startAnimalsAmount = startAnimalsAmount;
            return this;
        }

        public Builder setStartAnimalEnergy(int startAnimalEnergy) {
            this.startAnimalEnergy = startAnimalEnergy;
            return this;
        }

        public Builder setMinEnergyToFullAnimal(int minEnergyToFullAnimal) {
            this.minEnergyToFullAnimal = minEnergyToFullAnimal;
            return this;
        }

        public Builder setSexEnergyCost(int sexEnergyCost) {
            this.sexEnergyCost = sexEnergyCost;
            return this;
        }

        public Builder setMinMutationAmount(int minMutationAmount) {
            this.minMutationAmount = minMutationAmount;
            return this;
        }

        public Builder setMaxMutationAmount(int maxMutationAmount) {
            this.maxMutationAmount = maxMutationAmount;
            return this;
        }

        public Builder setAnimalGenomeLength(int animalGenomeLength) {
            this.animalGenomeLength = animalGenomeLength;
            return this;
        }

        public Simulation build() {
            return new Simulation(this);
        }
    }
}
