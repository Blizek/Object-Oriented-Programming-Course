package projekt.model.maps;

import org.junit.jupiter.api.Test;
import projekt.model.Animal;
import projekt.model.Vector2d;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EarthMapTest {
    @Test
    void testIfTakesOnly10Energy() {
        // given
        AbstractMap map = new EarthMap(10, 10, 5, 2, 5);
        Animal animal = new Animal(new Vector2d(2, 3), 0, List.of(0, 1, 2, 3, 4, 5, 6, 7), null, null, 10);
        int startEnergy = animal.getEnergy();
        map.place(animal);

        // when
        map.move(animal);

        //then
        assertEquals(10, startEnergy - animal.getEnergy());
    }
}