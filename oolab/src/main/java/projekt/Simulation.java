package projekt;

import projekt.model.*;

import java.util.*;

public class Simulation implements Runnable {
    private final List<Animal> animalsList = new ArrayList<>(); // list of all animals
    private final List<Animal> newDeadAnimalsList = new ArrayList<>(); // list of all animals
    private final HashMap<Vector2d, Animal> animalsMap;

    private final GameMap gameMap; // map of the game
    private final int grassGrownAmount;
    private final int minEnergyToFullAnimal;
    private final AnimalMaker animalMaker;
    private final GrassMaker grassMaker;

    private Simulation(Builder builder) {
        gameMap = builder.map;
        int eatenGrassEnergy = builder.eatenGrassEnergy;
        grassGrownAmount = builder.grassGrownAmount;
        int startAnimalsAmount = builder.startAnimalsAmount;
        int startAnimalEnergy = builder.startAnimalEnergy;
        minEnergyToFullAnimal = builder.minEnergyToFullAnimal;
        int sexEnergyCost = builder.sexEnergyCost;
        int minMutationAmount = builder.minMutationAmount;
        int maxMutationAmount = builder.maxMutationAmount;
        int animalGenomeLength = builder.animalGenomeLength;

        animalsMap = new HashMap<>();
        animalMaker = new AnimalMaker(minMutationAmount, maxMutationAmount, animalGenomeLength, startAnimalEnergy, sexEnergyCost);
        grassMaker = new GrassMaker(eatenGrassEnergy);

        Boundary mapBoundary = gameMap.getMapBoundary();
        Vector2d topRightCorner = mapBoundary.upperRight();

        Random random = new Random();
        for (int i = 0; i < startAnimalsAmount; i++) {
            int randomwidth = random.nextInt(0, topRightCorner.getX());
            int randomheight = random.nextInt(0, topRightCorner.getY());
            Vector2d animalPosition = new Vector2d(randomwidth, randomheight);
            Animal newAnimal = animalMaker.makeAnimal(animalPosition);
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

                for (Grass grass : gameMap.getGrassesMap().values()) {
                    if (gameMap.isOccupied(grass.getPosition())) {
                        List<Animal> animalsOnPosition = gameMap.animalAt(grass.getPosition());
//                        Animal chosenAnimal = Collections.max(animalsOnPosition, Comparator.comparingInt(Animal::getEnergy));
                        Animal chosenAnimal = animalsOnPosition == null ? null : animalsOnPosition.get(0);
                        if (chosenAnimal != null) {
                            chosenAnimal.eat(grass.getEnergy());
                            gameMap.removeGrass(grass.getPosition());
                        }

                    }
                }

                for (List<Animal> animalsOnPosition: gameMap.getAnimalsMap().values()){
                    List<Animal> filteredAnimals = new ArrayList<>();
                    for (Animal animal : animalsList) {
                        if (animal.getEnergy() < minEnergyToFullAnimal) {
                            filteredAnimals.add(animal);
                        }
                    }
                    for (int i = 1; i < filteredAnimals.size(); i += 2) {
                        Animal mother = filteredAnimals.get(i);
                        Animal father = filteredAnimals.get(i - 1);
                        Animal child = animalMaker.makeAnimalFromParents(mother, father);
                        gameMap.place(child);
                        animalsList.add(child);
                    }
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
