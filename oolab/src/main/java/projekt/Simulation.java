package projekt;

import projekt.model.*;
import projekt.model.animalMakers.AbstractAnimalMaker;
import projekt.model.animalMakers.AnimalMakerFullRandom;
import projekt.model.animalMakers.AnimalMakerSlightCorrection;
import projekt.model.maps.AbstractMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static projekt.model.util.AnimalUtils.setDominantAnimal;

public class Simulation implements Runnable {
    private final List<Animal> animalsList = new ArrayList<>(); // list of all animals
    private final List<Animal> newDeadAnimalsList = new ArrayList<>(); // list of all dead that day animals
    private final List<Grass> newEatenGrass = new ArrayList<>(); // list of all eaten grasses that day
    private final HashMap<Vector2d, Animal> animalsMap;
    private final AbstractMap gameMap; // map of the game
    private final int grassGrownAmount;
    private final int minEnergyToFullAnimal;
    private final AbstractAnimalMaker animalMaker;
    private final int gameplaySpeed;
    private final boolean isLogged;
    private long day = 0;
    private boolean running = true;

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
        gameplaySpeed = builder.gameplaySpeed;
        isLogged = builder.isLogged;

        animalsMap = new HashMap<>();
        if (isSlightCorrection)
            animalMaker = new AnimalMakerSlightCorrection(minMutationAmount, maxMutationAmount, animalGenomeLength, startAnimalEnergy, sexEnergyCost, minEnergyToFullAnimal);
        else
            animalMaker = new AnimalMakerFullRandom(minMutationAmount, maxMutationAmount, animalGenomeLength, startAnimalEnergy, sexEnergyCost, minEnergyToFullAnimal);

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

    public boolean isRunning() {
        return running;
    }

    public long getDay() {
        return day;
    }

    /**
     * Method to run simulation for every animal on list
     */
    @Override
    public void run() {

        try {
            while (!animalsList.isEmpty()) {
                if (running) {
                    day++;
                    for (Animal animal : animalsList) {
                        if (animal.getEnergy() <= 0) {
                            newDeadAnimalsList.add(animal);
                            gameMap.removeDeadAnimal(animal);
                        }
                    }

                    for (Animal animal : newDeadAnimalsList) {
                        animalsList.remove(animal);
                    }
                    newDeadAnimalsList.clear();

                    for (Animal animal : animalsList) {
                        gameMap.move(animal);
                    }

                    for (Grass grass : gameMap.getGrassesMap().values()) {
                        List<Animal> animalsOnPosition = gameMap.animalAt(grass.getPosition());
                        if (animalsOnPosition != null) {
                            Animal chosenAnimal = setDominantAnimal(animalsOnPosition);
                            if (chosenAnimal != null) {
                                chosenAnimal.eat(grass.getEnergy());
                                newEatenGrass.add(grass);
                            }
                        }
                    }

                    for (Grass grass : newEatenGrass) {
                        gameMap.removeGrass(grass.getPosition());
                    }
                    newEatenGrass.clear();

                    for (List<Animal> animalsOnPosition : gameMap.getAnimalsMap().values()) {
                        List<Animal> filteredAnimals = new ArrayList<>();
                        for (Animal animal : animalsOnPosition) {
                            if (animal.getEnergy() > minEnergyToFullAnimal) {
                                filteredAnimals.add(animal);
                            }
                        }
                        if (filteredAnimals.size() < 2) {
                            continue;
                        }
                        Animal father = setDominantAnimal(filteredAnimals);
                        filteredAnimals.remove(father);
                        Animal mother = setDominantAnimal(filteredAnimals);
                        Animal child = animalMaker.makeAnimalFromParents(mother, father);
                        gameMap.place(child);
                        animalsList.add(child);
                    }

                    gameMap.addNewGrasses();
                    gameMap.listenerObserver();
                    writeToLog();
                    Thread.sleep(gameplaySpeed);
                } else {
                    break;
                }
            }

            killGame(Thread.currentThread());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToLog() {
        if (isLogged) {
            String fileName = "src/main/resources/savedLogs/simulation_statistics_" + gameMap.getMapUUID() + ".csv";
            File file = new File(fileName);
            boolean fileExists = file.exists() && file.length() > 0;

            try (FileWriter writer = new FileWriter(fileName, true)) {
                if (!fileExists) {
                    writer.append("Day,Animals,Grasses,FreeSpaces,MostPopularGenome,AverageEnergy,AverageLifetime,AverageChildren\n");
                }
                writer.append(day + ",");
                writer.append(Statistics.getAllAnimalsCount(animalsList) + ",");
                writer.append(Statistics.getAllGrassesCount(gameMap.getGrassesMap()) + ",");
                writer.append(Statistics.getAllFreeSpacesCount(gameMap) + ",");
                List<Integer> mostPopularGenome = Statistics.getMostPopularGenome(animalsList);
                writer.append(mostPopularGenome.isEmpty() ? "-" : mostPopularGenome.stream().map(String::valueOf).collect(Collectors.joining(" ")) + ",");
                writer.append(Statistics.getAverageEnergy(animalsList) + ",");
                writer.append(Statistics.getAverageDaysLived(animalsList) + ",");
                writer.append(Statistics.getAverageChildrenCount(animalsList) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void killGame(Thread thread) {
        running = false;
        gameMap.listenerObserver();
        thread.interrupt();
    }

    public void stopGame(Thread thread) throws InterruptedException {
        running = false;
        gameMap.listenerObserver();
        thread.join();
    }

    public void startGame(Thread thread) {
        running = true;
        thread.setDaemon(true);
        thread.start();
    }

    public List<Vector2d> getPositionsWithGenes(List<Integer> genes) {
        Set<Vector2d> positions = new HashSet<>();
        for (Animal animal : animalsList) {
            List<Integer> genome = animal.getGenome();
            for (Integer gene : genes) {
                if (genome.contains(gene)) {
                    positions.add(animal.getPosition());
                    break;
                }
            }
        }
        return new ArrayList<>(positions);
    }

    public static class Builder {
        private AbstractMap map;
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
        private int gameplaySpeed;
        private boolean isLogged;

        public Builder setMap(AbstractMap map) {
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

        public Builder setIsLogged(boolean isLogged) {
            this.isLogged = isLogged;
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

        public Builder setGameplaySpeed(int gameplaySpeed) {
            this.gameplaySpeed = gameplaySpeed;
            return this;
        }

        public Simulation build() {
            return new Simulation(this);
        }
    }


}
