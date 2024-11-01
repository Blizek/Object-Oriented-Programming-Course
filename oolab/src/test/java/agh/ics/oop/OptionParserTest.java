package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionParserTest {
    /**
     * Test to check if OptionParser.parseStringToMoveDirections works properly (test covers when all data correct)
     */
    @Test
    void correctDataInStringsArray() {
        // given
        String[] correctData = {"f", "l", "f", "b", "r", "f"};

        // when
        MoveDirection[] parsedCorrectData = OptionParser.parseStringToMoveDirections(correctData);
        MoveDirection[] expectedCorrectData = {MoveDirection.FORWARD,
                                                MoveDirection.LEFT,
                                                MoveDirection.FORWARD,
                                                MoveDirection.BACKWARD,
                                                MoveDirection.RIGHT,
                                                MoveDirection.FORWARD};

        // then
        assertArrayEquals(parsedCorrectData, expectedCorrectData);
    }

    /**
     * Test to check if OptionParser.parseStringToMoveDirections works properly (test covers when array has
     * both correct and wrong data (not like 'f', 'r', 'b', 'l'))
     */
    @Test
    void correctAndWrongDataInStringArray() {
        // given
        String[] correctAndWrongData = {"f", "l", "test", "b", "x", "f", "z"};

        // when
        MoveDirection[] parsedCorrectAndWrongData = OptionParser.parseStringToMoveDirections(correctAndWrongData);
        MoveDirection[] expectedCorrectAndWrongData = {MoveDirection.FORWARD,
                                                        MoveDirection.LEFT,
                                                        MoveDirection.BACKWARD,
                                                        MoveDirection.FORWARD};

        // then
        assertArrayEquals(parsedCorrectAndWrongData, expectedCorrectAndWrongData);
    }

    /**
     * Test to check if OptionParser.parseStringToMoveDirections works properly (test covers when array has no elements)
     */
    @Test
    void stringArrayIsEmpty() {
        // given
        String[] emptyArray = {};

        // when
        MoveDirection[] parsedEmptyArray = OptionParser.parseStringToMoveDirections(emptyArray);
        MoveDirection[] expectedEmptyArray = {};

        // then
        assertArrayEquals(parsedEmptyArray, expectedEmptyArray);
    }

    /**
     * Test to check if OptionParser.parseStringToMoveDirections works properly (test covers when array has only wrong data inside)
     */
    @Test
    void onlyWrongDataInStringArray() {
        // given
        String[] onlyWrongData = {"pozdrowienia", "x", "podziękowania", "AGH", "e", "g"};

        // when
        MoveDirection[] parsedOnlyWrongData = OptionParser.parseStringToMoveDirections(onlyWrongData);
        MoveDirection[] expectedOnlyWrongData = {};

        // then
        assertArrayEquals(parsedOnlyWrongData, expectedOnlyWrongData);
    }
}