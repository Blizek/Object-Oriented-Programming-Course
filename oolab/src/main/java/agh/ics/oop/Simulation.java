package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private List<MoveDirection> animalsMovesList;
    private List<Animal> animalsList = new ArrayList<>();

    public Simulation(List<Vector2d> animalsStartPositionsList, List<MoveDirection> animalsMovesList) {
        this.animalsMovesList = animalsMovesList;
        for (Vector2d animalStartPosition: animalsStartPositionsList) {
            animalsList.add(new Animal(animalStartPosition));
        }
    }

    public List<Animal> getAnimalsList() {
        return animalsList;
    }

    public void setAnimalsList(List<Animal> animalsList) {
        this.animalsList = animalsList;
    }

    public List<MoveDirection> getAnimalsMovesList() {
        return animalsMovesList;
    }

    public void setAnimalsMovesList(List<MoveDirection> animalsMovesList) {
        this.animalsMovesList = animalsMovesList;
    }

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
