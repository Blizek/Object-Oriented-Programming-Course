package proj_lab2.model

import proj_lab2.randomFreePosition
import proj_lab2.randomPosition

class BouncyMap (private val width: Int, private val height: Int): WorldMap {

    private var animalsMap: MutableMap<Vector2d, Animal> = HashMap()
    private var topRightCorner = Vector2d(width - 1, height - 1) // ponieważ dolny lewy róg to (0, 0) to aby były odpowiednie wymiary należy odjąć od obu 1
    private var bottomLeftCorner = Vector2d(0, 0)

    override fun canMoveTo(position: Vector2d): Boolean =
        position.follows(bottomLeftCorner) && position.precedes(topRightCorner)

    override fun place(animal: Animal) {
        if (!canMoveTo(animal.position)) return
        if (isOccupied(animal.position)){

            val possiblePosition = animalsMap.randomFreePosition(Vector2d(width, height))
            when(possiblePosition){
                null -> {
                    val newPosition = animalsMap.randomPosition()!!
                    animal.position = newPosition
                    animalsMap[animal.position] = animal
                }
                else -> {
                    animal.position = possiblePosition
                    animalsMap[animal.position] = animal
                }
            }
        }else {
            animalsMap[animal.position] = animal
        }
    }

    override fun move(animal: Animal, direction: MoveDirection) {
        if (animalsMap.containsValue(animal)) {
            animalsMap.remove(animal.position)
            animal.move(this, direction)
            place(animal)
        }
    }

    override fun isOccupied(position: Vector2d): Boolean = animalsMap.containsKey(position)

    override fun objectAt(position: Vector2d): Animal? {
        return animalsMap[position]
    }
}