package projekt;

import projekt.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final List<Animal> animalsList = new ArrayList<>(); // list of all animals
    private final GameMap gameMap = new GameMap(); // map of the game

    private Simulation(Builder builder) {

    }

    public List<Animal> getAnimalsList() {
        return List.copyOf(animalsList);
    }

    /**
     * Method to run simulation for every animal on list
     */
    public void run(){
        if (animalsList.isEmpty()) {
            return;
        }

        int animalsCount = animalsList.size();
        try {
            for (Animal actualAnimal: animalsList) {
                gameMap.move(actualAnimal); // move this animal on the map
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static class Builder {
        private int mapHeight;
        private int mapWidth;
        private int animalStartEnergy;
        private int grassEatenEnergy;
        private int mapAnimationInterval;
        private int startAnimalCount;
        private int satedEnergyRequired;
        private int sexEnergyCost;

        public Builder setMapHeight(int mapHeight) {
            this.mapHeight = mapHeight;
            return this;
        }

        public Builder setMapWidth(int mapWidth) {
            this.mapWidth = mapWidth;
            return this;
        }

        public Builder setAnimalStartEnergy(int animalStartEnergy) {
            this.animalStartEnergy = animalStartEnergy;
            return this;
        }

        public Builder setGrassEatenEnergy(int grassEatenEnergy) {
            this.grassEatenEnergy = grassEatenEnergy;
            return this;
        }

        public Builder setMapAnimationInterval(int mapAnimationInterval) {
            this.mapAnimationInterval = mapAnimationInterval;
            return this;
        }

        public Builder setStartAnimalCount(int startAnimalCount) {
            this.startAnimalCount = startAnimalCount;
            return this;
        }

        public Builder setSatedEnergyRequired(int satedEnergyRequired) {
            this.satedEnergyRequired = satedEnergyRequired;
            return this;
        }

        public Builder setSexEnergyCost(int sexEnergyCost) {
            this.sexEnergyCost = sexEnergyCost;
            return this;
        }

        public Simulation build() {
            return new Simulation(this);
        }
    }
}
