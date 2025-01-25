package projekt.model.util;

import projekt.model.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class AnimalUtils {
    public static Animal setDominantAnimal(List<Animal> animalsOnPosition) {
        List<Animal> animalsWithMaxEnergy = getAnimalsWithMaxValue(animalsOnPosition, Animal::getEnergy);
        for (Animal animal: animalsOnPosition) {
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
    private static List<Animal> getAnimalsWithMaxValue(List<Animal> animals, Function<Animal, Integer> getter) {
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
