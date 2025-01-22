package proj_lab2

import proj_lab2.model.Vector2d

fun <V> Map<Vector2d, V>.randomPosition(): Vector2d? = this.keys.randomOrNull()

fun <V> Map<Vector2d, V>.randomFreePosition(mapSize: Vector2d): Vector2d?{
    val maxSize = mapSize.getX() * mapSize.getY()

    if (this.size == maxSize)
        return null

    val freePositions = HashSet<Vector2d>()
    for (i in 0 .. mapSize.getX()) {
        for (j in 0 .. mapSize.getY()) {
            val currentPosition = Vector2d(i, j)
            if (!this.containsKey(currentPosition))
                freePositions.add(currentPosition)
        }
    }
    return freePositions.random()
}