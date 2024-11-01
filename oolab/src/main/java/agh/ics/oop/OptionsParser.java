package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class that has method to parse directions as Strings (example: "f", "r" etc.) to enum MoveDirection object
 */
public class OptionsParser {
    /**
     * Method to convert list of directions as Strings to array of directions but as enum MoveDirection objects
     * @param stringMoveDirections Array of directions as Strings
     * @return List of directions as enum MoveDirection objects
     */
    public static List<MoveDirection> parseStringToMoveDirections(String[] stringMoveDirections) {
        List<MoveDirection> parsedMoveDirections = new ArrayList<>();
        for (String stringMoveDirection : stringMoveDirections) {
            switch (stringMoveDirection) {
                case "f" -> parsedMoveDirections.add(MoveDirection.FORWARD);
                case "b" -> parsedMoveDirections.add(MoveDirection.BACKWARD);
                case "l" -> parsedMoveDirections.add(MoveDirection.LEFT);
                case "r" -> parsedMoveDirections.add(MoveDirection.RIGHT);
            }
        }

        return parsedMoveDirections;
    }
}
