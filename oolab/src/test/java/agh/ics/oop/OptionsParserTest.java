package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {
    /**
     * Test to check if OptionParser.parseStringToMoveDirections works properly (test covers when all data correct)
     */
    @Test
    void correctDataInStringsArray() {
        // given
        String[] correctData = {"f", "l", "f", "b", "r", "f"};

        // when
        List<MoveDirection> parsedCorrectData = OptionsParser.parseStringToMoveDirections(correctData);
        List<MoveDirection> expectedCorrectData = List.of(MoveDirection.FORWARD,
                                                MoveDirection.LEFT,
                                                MoveDirection.FORWARD,
                                                MoveDirection.BACKWARD,
                                                MoveDirection.RIGHT,
                                                MoveDirection.FORWARD);

        // then
        assertEquals(parsedCorrectData, expectedCorrectData);
    }

    /**
     * Test to check if OptionParser.parseStringToMoveDirections works properly (test covers when array has
     * both correct and wrong data (not like 'f', 'r', 'b', 'l'))
     */
    @Test
    void correctAndWrongDataInStringArray() {
        // given
        String[] correctAndWrongData = {"f", "l", "test", "b", "x", "f", "z"};

        // then
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parseStringToMoveDirections(correctAndWrongData));
    }

    /**
     * Test to check if OptionParser.parseStringToMoveDirections works properly (test covers when array has no elements)
     */
    @Test
    void stringArrayIsEmpty() {
        // given
        String[] emptyArray = {};

        // when
        List<MoveDirection> parsedEmptyArray = OptionsParser.parseStringToMoveDirections(emptyArray);
        List<MoveDirection> expectedEmptyArray = List.of();

        // then
        assertEquals(parsedEmptyArray, expectedEmptyArray);
    }

    /**
     * Test to check if OptionParser.parseStringToMoveDirections works properly (test covers when array has only wrong data inside)
     */
    @Test
    void onlyWrongDataInStringArray() {
        // given
        String[] onlyWrongData = {"pozdrowienia", "x", "podziękowania", "AGH", "e", "g"};

        // then
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parseStringToMoveDirections(onlyWrongData));
    }
}