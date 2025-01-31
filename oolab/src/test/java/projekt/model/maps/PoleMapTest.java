package projekt.model.maps;

import org.junit.jupiter.api.Test;
import projekt.model.Animal;
import projekt.model.Direction;
import projekt.model.Vector2d;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PoleMapTest {
    @Test
    void checkIfAnimalLoseMoreEnergyNearPole() {
        // given
        AbstractMap map = new PoleMap(10, 10, 5, 2, 5);
        Animal animal = new Animal(new Vector2d(2, 3), 0, List.of(0), null, null, 10);
        int startEnergy = animal.getEnergy();
        map.place(animal);
        animal.setDirection(Direction.SOUTH);

        // when
        map.move(animal);

        // then
        assertTrue(10 < startEnergy - animal.getEnergy());
    }
}