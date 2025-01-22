package proj_lab2.model

import proj_lab2.randomFreePosition
import proj_lab2.randomPosition

class BouncyMap (private val width: Int, private val height: Int): WorldMap {

    private var animalsMap: MutableMap<Vector2d, Animal> = HashMap()
    private var topRightCorner = Vector2d(width, height)
    private var bottomLeftCorner = Vector2d(0, 0)

    override fun canMoveTo(position: Vector2d): Boolean =
        position.follows(bottomLeftCorner) && position.precedes(topRightCorner)

    override fun place(animal: Animal): Boolean {
        if (!canMoveTo(animal.getAnimalPosition())) return false
        if (isOccupied(animal.getAnimalPosition())){

            val possiblePosition = animalsMap.randomFreePosition(Vector2d(width, height))
            when(possiblePosition){
                null -> {
                    val newPosition = animalsMap.randomPosition()!!
                    animal.setAnimalPosition(newPosition)
                    animalsMap[animal.getAnimalPosition()] = animal
                }
                else -> {
                    animal.setAnimalPosition(possiblePosition)
                    animalsMap[animal.getAnimalPosition()] = animal
                }
            }
        }else {
            animalsMap[animal.getAnimalPosition()] = animal
        }
        return true
    }

    override fun move(animal: Animal, direction: MoveDirection) {
        if (animalsMap.containsValue(animal)) {
            animalsMap.remove(animal.getAnimalPosition())
            animal.move(this, direction)
            place(animal)
        }
    }

    override fun isOccupied(position: Vector2d): Boolean = animalsMap.containsKey(position)

    override fun objectAt(position: Vector2d): Animal? {
        return animalsMap[position]
    }
}