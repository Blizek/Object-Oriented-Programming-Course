package projekt.model;

import java.util.ArrayList;
import java.util.List;

public class Grass implements WorldElement {
    private Vector2d grassPosition;
    private final int grassEnergy;

    public Grass(Vector2d grassPosition, int grassEnergy) {
        this.grassPosition = grassPosition;
        this.grassEnergy = grassEnergy;
    }

    @Override
    public Vector2d getPosition() {
        return grassPosition;
    }

    @Override
    public String toString() {
        return "*";
    }
    @Override
    public int getEnergy(){
        return grassEnergy;
    }

    public Animal setAnimalToEat(List<Animal> animalsToEat) {
        List<Animal> animalsWithMaxEnergy = getAnimalsWithMaxEnergy(animalsToEat);
        System.out.println("Zwierzęta do jedzenia:");
        for (Animal animal: animalsToEat) {
            System.out.println(animal.getEnergy());
        }
        System.out.println("Zwrócono listę zwierząt o energii: " + animalsWithMaxEnergy.getFirst().getEnergy());
        if (animalsWithMaxEnergy.size() != 1) {
            return animalsWithMaxEnergy.getFirst();
        }
        return animalsWithMaxEnergy.getFirst();
    }

    private List<Animal> getAnimalsWithMaxEnergy(List<Animal> animals) {
        int maxAnimalEnergy = animals.getFirst().getEnergy();
        List<Animal> animalsWithMaxEnergy = new ArrayList<>();
        animalsWithMaxEnergy.add(animals.getFirst());

        Animal currentAnimal;
        for (int i = 1; i < animals.size(); i++) {
            currentAnimal = animals.get(i);
            if (currentAnimal.getEnergy() > maxAnimalEnergy) {
                animalsWithMaxEnergy.clear();
                animalsWithMaxEnergy.add(currentAnimal);
                maxAnimalEnergy = currentAnimal.getEnergy();
            } else if (currentAnimal.getEnergy() == maxAnimalEnergy) {
                animalsWithMaxEnergy.add(currentAnimal);
            }
        }

        return animalsWithMaxEnergy;
    }
}
