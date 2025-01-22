package proj_lab2.model

data class Animal (var position: Vector2d = Vector2d(2, 2)) {
    private var animalDirection = MapDirection.NORTH
    private var animalPosition = Vector2d(2, 2)

    fun getAnimalDirection() = animalDirection

    fun getAnimalPosition() = animalPosition

    override fun toString() = when (animalDirection) {
        MapDirection.NORTH -> "N"
        MapDirection.EAST -> "E"
        MapDirection.SOUTH -> "S"
        MapDirection.WEST -> "W"
    }

    fun isAt(position: Vector2d): Boolean = this.position == position

    fun move(validator: MoveValidator, direction: MoveDirection) {
        var newAnimalPosition = position
        when (direction) {
            MoveDirection.LEFT -> this.animalDirection = this.animalDirection.previous()
            MoveDirection.RIGHT -> this.animalDirection = this.animalDirection.next()
            MoveDirection.FORWARD -> newAnimalPosition +=  this.animalDirection.toUnitVector()
            MoveDirection.BACKWARD -> newAnimalPosition -=  this.animalDirection.toUnitVector()
        }
        if (validator.canMoveTo(newAnimalPosition)) {
            position = newAnimalPosition
        }
    }
}