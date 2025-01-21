package projekt;

import projekt.model.*;
import projekt.model.maps.EarthMap;
import projekt.model.maps.PoleMap;

import java.util.*;
import java.util.function.Function;

public class Simulation implements Runnable {
    private final List<Animal> animalsList = new ArrayList<>(); // list of all animals
    private final List<Animal> newDeadAnimalsList = new ArrayList<>(); // list of all dead that day animals
    private final List<Grass> newEatenGrass = new ArrayList<>(); // list of all eaten grasses that day
    private final HashMap<Vector2d, Animal> animalsMap;
    private long day = 0;

    private final EarthMap gameMap; // map of the game
    private final int grassGrownAmount;
    private final int minEnergyToFullAnimal;
    private final AnimalMaker animalMaker;

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
        boolean isSlightCorrection = builder.isSlightCorrection;

        animalsMap = new HashMap<>();
        animalMaker = new AnimalMaker(minMutationAmount, maxMutationAmount, animalGenomeLength, startAnimalEnergy, sexEnergyCost, isSlightCorrection);

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

        try {
            while (day < 20) {
                day++;
                System.out.println("New day");
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

                for (Animal animal : animalsList) {
                    gameMap.move(animal);
                    Thread.sleep(0);
                }

                for (Grass grass : gameMap.getGrassesMap().values()) {
                    List<Animal> animalsOnPosition = gameMap.animalAt(grass.getPosition());
                    if (animalsOnPosition != null) {
                        // Animal chosenAnimal = Collections.max(animalsOnPosition, Comparator.comparingInt(Animal::getEnergy));
                        Animal chosenAnimal = setAnimalToEat(animalsOnPosition);
                        if (chosenAnimal != null) {
                            chosenAnimal.eat(grass.getEnergy());
                            newEatenGrass.add(grass);
                        }
                    }
                }

                for (Grass grass: newEatenGrass) {
                    gameMap.removeGrass(grass.getPosition());
                }
                newEatenGrass.clear();

                for (List<Animal> animalsOnPosition: gameMap.getAnimalsMap().values()){
                    List<Animal> filteredAnimals = new ArrayList<>();
                    for (Animal animal : animalsOnPosition) {
                        if (animal.getEnergy() > minEnergyToFullAnimal) {
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

                 gameMap.addNewGrasses();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static class Builder {
        private EarthMap map;
        private int eatenGrassEnergy;
        private int grassGrownAmount;
        private int startAnimalsAmount;
        private int startAnimalEnergy;
        private int minEnergyToFullAnimal;
        private int sexEnergyCost;
        private int minMutationAmount;
        private int maxMutationAmount;
        private int animalGenomeLength;
        private boolean isSlightCorrection;

        public Builder setMap(EarthMap map) {
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

        public Builder setSlightCorrection(boolean isSlightCorrection) {
            this.isSlightCorrection = isSlightCorrection;
            return this;
        }

        public Simulation build() {
            return new Simulation(this);
        }
    }

    private Animal setAnimalToEat(List<Animal> animalsToEat) {
        List<Animal> animalsWithMaxEnergy = getAnimalsWithMaxValue(animalsToEat, Animal::getEnergy);
        for (Animal animal: animalsToEat) {
            System.out.println(animal.getEnergy());
        }
        if (animalsWithMaxEnergy.size() == 1) {
            return animalsWithMaxEnergy.getFirst();
        }
        List<Animal> animalsWithMaxAge = getAnimalsWithMaxValue(animalsWithMaxEnergy, Animal::getDaysLived);
        if (animalsWithMaxAge.size() == 1) {
            return animalsWithMaxAge.getFirst();
        }
        List<Animal> animalsWithMaxChildren = getAnimalsWithMaxValue(animalsWithMaxAge, Animal::getChildrenCount);
        if (animalsWithMaxChildren.size() == 1) {
            return animalsWithMaxChildren.getFirst();
        }
        Random random = new Random();
        return animalsWithMaxChildren.get(random.nextInt(animalsWithMaxChildren.size()));
    }



    private List<Animal> getAnimalsWithMaxValue(List<Animal> animals, Function<Animal, Integer> getter) {
        int maxAnimalValue = getter.apply(animals.getFirst());
        List<Animal> animalsWithMaxValue = new ArrayList<>();
        animalsWithMaxValue.add(animals.getFirst());

        for (int i = 1; i < animals.size(); i++) {
            Animal currentAnimal = animals.get(i);
            int currentValue = getter.apply(currentAnimal);
            if (currentValue > maxAnimalValue) {
                animalsWithMaxValue.clear();
                animalsWithMaxValue.add(currentAnimal);
                maxAnimalValue = currentValue;
            } else if (currentValue == maxAnimalValue) {
                animalsWithMaxValue.add(currentAnimal);
            }
        }

        return animalsWithMaxValue;
    }

}
