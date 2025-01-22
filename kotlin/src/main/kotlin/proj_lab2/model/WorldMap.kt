package proj_lab2.model

/**
 * The interface responsible for interacting with the map of the world.
 */
interface WorldMap : MoveValidator {

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     * @throws IncorrectPositionException if the position is invalid for placement.
     */
    fun place(animal: Animal)

    /**
     * Moves an animal (if it is present on the map) according to the specified direction.
     */
    fun move(animal: Animal, direction: MoveDirection)

    /**
     * Checks if a given position on the map is occupied.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    fun isOccupied(position: Vector2d): Boolean

    /**
     * Return an animal at a given position or null if it is not present.
     *
     * @param position The position of the animal.
     * @return animal or null.
     */
    fun objectAt(position: Vector2d): Animal?
}
