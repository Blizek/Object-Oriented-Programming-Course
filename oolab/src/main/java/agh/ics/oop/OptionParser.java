package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionParser {
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
