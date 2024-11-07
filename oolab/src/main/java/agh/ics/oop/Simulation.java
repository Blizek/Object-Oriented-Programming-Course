package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<MoveDirection> animalsMovesList; // list of all animals' moves
    private final List<Animal> animalsList = new ArrayList<>(); // list of all animals

    /**
     * Constructor for Simulation object
     * @param animalsStartPositionsList list of all animals' start positions
     * @param animalsMovesList list of all animals' moves
     */
    public Simulation(List<Vector2d> animalsStartPositionsList, List<MoveDirection> animalsMovesList) {
        this.animalsMovesList = animalsMovesList;
        for (Vector2d animalStartPosition: animalsStartPositionsList) {
            animalsList.add(new Animal(animalStartPosition));
        }
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
            actualAnimal.move(animalsMovesList.get(i));
            System.out.println("Zwierzę %d: (%d, %d), kierunek: %s".formatted(actualAnimalIndex,
                                                                                actualAnimal.getAnimalPosition().getX(),
                                                                                actualAnimal.getAnimalPosition().getY(),
                                                                                actualAnimal.getAnimalDirection()));
        }
    }
}
