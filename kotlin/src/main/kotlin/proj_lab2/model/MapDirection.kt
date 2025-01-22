package proj_lab2.model

enum class MapDirection(private val unitVector: Vector2d, private val directionName: String) {
    NORTH(Vector2d(0, 1), "Północ"),
    SOUTH(Vector2d(0, -1), "Południe"),
    WEST(Vector2d(-1, 0), "Zachód"),
    EAST(Vector2d(1, 0), "Wschód");

    override fun toString() = directionName

    fun getUnitVector(direction: MapDirection) = direction.unitVector

    fun next(): MapDirection = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    fun previous(): MapDirection = when (this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }
}