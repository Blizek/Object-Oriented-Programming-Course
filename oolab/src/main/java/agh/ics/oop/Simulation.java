package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<MoveDirection> animalsMovesList; // list of all animals' moves
    private final List<Animal> animalsList = new ArrayList<>(); // list of all animals
    private final WorldMap worldMap; // map of the game

    /**
     * Constructor for Simulation object
     * @param animalsStartPositionsList list of all animals' start positions
     * @param animalsMovesList list of all animals' moves
     * @param worldMap map
     */
    public Simulation(List<Vector2d> animalsStartPositionsList, List<MoveDirection> animalsMovesList, WorldMap worldMap) {
        this.animalsMovesList = animalsMovesList;
        for (Vector2d animalStartPosition: animalsStartPositionsList) {
            try {
                Animal newAnimal = new Animal(animalStartPosition);
                worldMap.place(newAnimal); // if position on the map is free set there new animal
                animalsList.add(newAnimal);
            } catch (IncorrectPositionException e) {
                System.out.println("Waring: " + e.getMessage());
            }
        }
        this.worldMap = worldMap;
    }

    public List<Animal> getAnimalsList() {
        return List.copyOf(animalsList);
    }

    /**
     * Method to run simulation for every animal on list
     */
    public void run(){
        if (animalsMovesList.isEmpty()) {
            return;
        }

        int animalsCount = animalsList.size();
        for (int i = 0; i < animalsMovesList.size(); i++) {
            int actualAnimalIndex = i % animalsCount;
            Animal actualAnimal = animalsList.get(actualAnimalIndex);
            worldMap.move(actualAnimal, animalsMovesList.get(i)); // move this animal on the map
            System.out.println("Zwierzę %d: (%d, %d), kierunek: %s".formatted(actualAnimalIndex,
                                                                                actualAnimal.getPosition().getX(),
                                                                                actualAnimal.getPosition().getY(),
                                                                                actualAnimal.getAnimalDirection()));
            System.out.println(worldMap); // visualize the map
        }
    }
}
