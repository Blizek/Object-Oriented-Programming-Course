package projekt.model.animalMakers;

import org.junit.jupiter.api.Test;
import projekt.model.Animal;
import projekt.model.Vector2d;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractAnimalMakerTest {
    @Test
    void testIfParentsHaveOneMoreChild() {
        // given
        AbstractAnimalMaker maker = new AnimalMakerFullRandom(0, 8, 8, 10, 5, 10);
        Animal mother = new Animal(new Vector2d(0, 0), 100, List.of(1, 2, 3, 4, 5, 6, 7, 8), null, null, 10);
        Animal father = new Animal(new Vector2d(0, 0), 100, List.of(8, 7, 6, 5, 4, 3, 2, 1), null, null, 10);
        int motherChildrenCounter = mother.getChildrenCount();
        int fatherChildrenCounter = father.getChildrenCount();

        // when
        maker.makeAnimalFromParents(mother, father);

        // then
        assertEquals(1, mother.getChildrenCount() - motherChildrenCounter);
        assertEquals(1, father.getChildrenCount() - fatherChildrenCounter);
    }

    @Test
    void testIfParentsHaveOneMoreDescendant() {
        // given
        AbstractAnimalMaker maker = new AnimalMakerFullRandom(0, 8, 8, 10, 5, 10);
        Animal mother = new Animal(new Vector2d(0, 0), 100, List.of(1, 2, 3, 4, 5, 6, 7, 8), null, null, 10);
        Animal father = new Animal(new Vector2d(0, 0), 100, List.of(8, 7, 6, 5, 4, 3, 2, 1), null, null, 10);
        Animal animal = new Animal(new Vector2d(0, 0), 100, List.of(4, 5, 2, 6, 7, 1, 0, 3), null, null, 10);
        int motherDescendantCounter = mother.getDescendants();
        int fatherDescendantCounter = father.getDescendants();

        // when
        Animal child = maker.makeAnimalFromParents(mother, father);
        maker.makeAnimalFromParents(child, animal);

        // then
        assertEquals(2, mother.getDescendants() - motherDescendantCounter);
        assertEquals(2, father.getDescendants() - fatherDescendantCounter);
    }
}