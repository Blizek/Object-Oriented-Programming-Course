package proj_lab2.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class BouncyMapBehaviorTest: StringSpec({
    "checking canMoveTo function" {
        // given
        val bouncyMap = BouncyMap(height = 5, width = 5)
        val animal = Animal(Vector2d(3, 3))

        // when
        bouncyMap.place(animal)

        // then
        bouncyMap.canMoveTo(Vector2d(2, 3)) shouldBe true
        bouncyMap.canMoveTo(Vector2d(6, 3)) shouldBe false
    }

    "correct adding animals to the map" {
        // given
        val bouncyMap = BouncyMap(height = 5, width = 5)
        val animal = Animal(Vector2d(2, 3))

        // then
        bouncyMap.place(animal) shouldBe true
        bouncyMap.objectAt(Vector2d(2, 3)) shouldBe animal
    }

    "incorrect adding animals to the map" {
        // given
        val bouncyMap = BouncyMap(height = 5, width = 5)
        val animal = Animal(Vector2d(6, 9))

        // then
        bouncyMap.place(animal) shouldBe false
        bouncyMap.objectAt(Vector2d(2, 3)) shouldBe null
    }

    "moving animals on the map" {
        // given
        val bouncyMap = BouncyMap(height = 5, width = 5)
        val animal = Animal(Vector2d(2, 1))

        // when
        bouncyMap.place(animal)
        bouncyMap.move(animal, MoveDirection.FORWARD)
        bouncyMap.move(animal, MoveDirection.FORWARD)
        bouncyMap.move(animal, MoveDirection.RIGHT)
        bouncyMap.move(animal, MoveDirection.FORWARD)
        bouncyMap.move(animal, MoveDirection.FORWARD)
        bouncyMap.move(animal, MoveDirection.LEFT)
        bouncyMap.move(animal, MoveDirection.BACKWARD)

        // then
        animal.getAnimalPosition() shouldBe Vector2d(4, 2)
    }

    "checking if isOccupied works" {
        // given
        val bouncyMap = BouncyMap(height = 5, width = 5)
        val animal = Animal(Vector2d(2, 1))

        // when
        bouncyMap.place(animal)
        bouncyMap.move(animal, MoveDirection.FORWARD)
        bouncyMap.move(animal, MoveDirection.FORWARD)
        bouncyMap.move(animal, MoveDirection.RIGHT)
        bouncyMap.move(animal, MoveDirection.FORWARD)

        // then
        bouncyMap.isOccupied(Vector2d(3, 3)) shouldBe true
        bouncyMap.isOccupied(Vector2d(2, 2)) shouldBe false
    }

    "checking objectAt" {
        // given
        val bouncyMap = BouncyMap(height = 5, width = 5)
        val animal1 = Animal(Vector2d(2, 3))
        val animal2 = Animal(Vector2d(2, 1))
        val animal3 = Animal(Vector2d(0, 0))

        // when
        bouncyMap.place(animal1)
        bouncyMap.place(animal2)
        bouncyMap.place(animal3)
        bouncyMap.move(animal3, MoveDirection.FORWARD)
        bouncyMap.move(animal3, MoveDirection.FORWARD)
        bouncyMap.move(animal2, MoveDirection.LEFT)
        bouncyMap.move(animal2, MoveDirection.BACKWARD)

        // then
        bouncyMap.objectAt(Vector2d(2, 3)) shouldBe animal1
        bouncyMap.objectAt(Vector2d(3, 1)) shouldBe animal2
        bouncyMap.objectAt(Vector2d(0, 2)) shouldBe animal3
        bouncyMap.objectAt(Vector2d(0, 1)) shouldNotBe animal3
    }

    "finding place for animal which wants to get on position where is some animal" {
        // given
        val bouncyMap = BouncyMap(height = 5, width = 5)
        val animal1 = Animal(Vector2d(2, 3))
        val animal2 = Animal(Vector2d(2, 3))

        // when
        bouncyMap.place(animal1)
        bouncyMap.place(animal2)

        // then
        animal1.getAnimalPosition() shouldNotBe animal2.getAnimalPosition()
    }
})