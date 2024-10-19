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
                case "f" -> parsedMoveDirections[directionIndex] = MoveDirection.FORWARD;
                case "b" -> parsedMoveDirections[directionIndex] = MoveDirection.BACKWARD;
                case "r" -> parsedMoveDirections[directionIndex] = MoveDirection.RIGHT;
                case "l" -> parsedMoveDirections[directionIndex] = MoveDirection.LEFT;
                default -> directionIndex--;
            }
            directionIndex++;
        }

        return parsedMoveDirections;
    }
}
