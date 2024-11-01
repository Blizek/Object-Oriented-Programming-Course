package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

/**
 * Util class that has method to parse directions as Strings (example: "f", "r" etc.) to enum MoveDirection object
 */
public class OptionsParser {
    /**
     * Method to convert list of directions as Strings to list of directions but as enum MoveDirection objects
     * @param stringMoveDirections Array of directions as Strings
     * @return Array of directions as enum MoveDirection objects
     */
    public static MoveDirection[] parseStringToMoveDirections(String[] stringMoveDirections) {
        int correctDirections = 0;
        for (String stringMoveDirection : stringMoveDirections) {
            switch (stringMoveDirection) {
                case "f", "b", "r", "l" -> correctDirections++;
            }
        }
        
        int directionIndex = 0; 
        MoveDirection[] parsedMoveDirections = new MoveDirection[correctDirections];
        for (String stringMoveDirection : stringMoveDirections) {
            switch (stringMoveDirection) {
                case "f": {
                    parsedMoveDirections[directionIndex] = MoveDirection.FORWARD;
                    directionIndex++;
                    break;
                }
                case "b": {
                    parsedMoveDirections[directionIndex] = MoveDirection.BACKWARD;
                    directionIndex++;
                    break;
                }
                case "r": {
                    parsedMoveDirections[directionIndex] = MoveDirection.RIGHT;
                    directionIndex++;
                    break;
                }
                case "l": {
                    parsedMoveDirections[directionIndex] = MoveDirection.LEFT;
                    directionIndex++;
                    break;
                }
            }
        }

        return parsedMoveDirections;
    }
}
