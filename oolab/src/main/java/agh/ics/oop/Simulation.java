package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation<T, P> {
    private final List<MoveDirection> animalsMovesList; // list of all animals' moves
    private final List<T> animalsList; // list of all animals
    private final WorldMap<T, P> worldMap; // map of the game

    /**
     * Constructor for Simulation object
     * @param animalsStartPositionsList list of all animals' start positions
     * @param animalsMovesList list of all animals' moves
     * @param worldMap map
     */
    public Simulation(List<P> animalsStartPositionsList, List<MoveDirection> animalsMovesList, WorldMap<T, P> worldMap) {
        this.animalsMovesList = animalsMovesList;
        animalsList = worldMap.setObjectsOnMap(animalsStartPositionsList);
        this.worldMap = worldMap;
    }

    public List<T> getAnimalsList() {
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
            T actualAnimal = animalsList.get(actualAnimalIndex);
            worldMap.move(actualAnimal, animalsMovesList.get(i)); // move this animal on the map
            System.out.println(worldMap); // visualize the map
        }
    }
}
