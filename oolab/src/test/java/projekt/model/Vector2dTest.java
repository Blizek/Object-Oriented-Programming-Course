package projekt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {
    // given
    final Vector2d v1 = new Vector2d(3, 6);
    final Vector2d v2 = new Vector2d(4, 8);
    final Vector2d v3 = new Vector2d(4, 10);
    final Vector2d v4 = new Vector2d(1, 10);
    final Vector2d v5 = new Vector2d(-1, -5);
    final Vector2d v6 = new Vector2d(-1, 7);
    final Vector2d v7 = new Vector2d(2, 7);
    final Vector2d v8 = new Vector2d(4, 8);
    final Vector2d v9 = new Vector2d(0, 3);

    /**
     * Test to check if Vector2d.toString works properly (test for both positive number, both negative and
     * one positive with one negative)
     */
    @Test
    void vectorToString() {
        // then
        assertEquals(v1.toString(), "(3, 6)");
        assertEquals(v5.toString(), "(-1, -5)");
        assertEquals(v6.toString(), "(-1, 7)");
    }

    /**
     * Test to check if Vector2d.precedes works properly (test for
     * both numbers less or equal to second vector,
     * both vectors are same
     * x number is less than in other vector but y is greater than in second vector,
     * both number are greater than in second vector,
     * x number is greater than in second vector but y number is less or equal then in second vector)
     */
    @Test
    void isVectorPrecedesAnotherVector() {
        // then
        assertTrue(v1.precedes(v2));
        assertTrue(v3.precedes(v3));
        assertFalse(v1.precedes(v7));
        assertFalse(v4.precedes(v5));
        assertFalse(v2.precedes(v6));
    }

    /**
     * Test to check if Vector2d.follows works properly (tests for
     * both number are greater or equal to second vector,
     * both vectors are same,
     * x number is less than in second vector but y number is equal or greater than in second vector,
     * x number is greater or equal than in second vector but y is less than in second vector,
     * both numbers in vector are less than in second)
     */
    @Test
    void isVectorFollowsAnotherVector() {
        // then
        assertTrue(v3.follows(v4));
        assertTrue(v5.follows(v5));
        assertFalse(v6.follows(v7));
        assertFalse(v1.follows(v7));
        assertFalse(v5.follows(v1));
    }

    /**
     * Test to check if Vector2d.add works properly
     * (create new vector that x value is summary of x values in both vectors and y value in an analogous way)
     */
    @Test
    void isAddTwoVectorsRight() {
        // when
        Vector2d summaryVector = v1.add(v5);
        Vector2d summaryVector2 = v3.add(v7);

        // then
        assertEquals(summaryVector, new Vector2d(2, 1));
        assertEquals(summaryVector2, new Vector2d(6, 17));
    }

    /**
     * Test to check id Vector2d.subtract works properly
     * (creates new vector that x value is subtraction of x values in both vectors and y values in an analogous way)
     */
    @Test
    void isSubtractTwoVectorsRight() {
        // when
        Vector2d subtractionVector = v7.subtract(v3);
        Vector2d subtractionVector2 = v1.subtract(v4);

        // then
        assertEquals(subtractionVector, new Vector2d(-2, -3));
        assertEquals(subtractionVector2, new Vector2d(2, -4));
    }

    /**
     * Test to check if Vector2d.upperRight works properly
     * (creates new vector that has x value set as max of x from both vectors and y value in analogous way)
     * (test for
     * in both vectors x and y values are different,
     * in both vectors x values are equal but y values not,
     * in both vectors y values are equal but x values not,
     * both vectors are same)
     */
    @Test
    void isUpperRightVectorRight() {
        // when
        Vector2d upperRightBothDifferent = v1.upperRight(v6);
        Vector2d upperRightXSame = v2.upperRight(v3);
        Vector2d upperRightYSame = v3.upperRight(v4);
        Vector2d upperRightBothSame = v2.upperRight(v2);

        // then
        assertEquals(upperRightBothDifferent, new Vector2d(3, 7));
        assertEquals(upperRightXSame, new Vector2d(4, 10));
        assertEquals(upperRightYSame, new Vector2d(4, 10));
        assertEquals(upperRightBothSame, new Vector2d(4, 8));
    }

    /**
     * Test to check if Vector2d.lowerLeft works properly
     * (creates new vector that has x value set as min of x from both vectors and y value in analogous way)
     * (test for
     * in both vectors x and y values are different,
     * in both vectors x values are equal but y values not,
     * in both vectors y values are equal but x values not,
     * both vectors are same)
     */
    @Test
    void isLowerLeftVectorRight() {
        // when
        Vector2d upperRightBothDifferent = v1.lowerLeft(v6);
        Vector2d upperRightXSame = v2.lowerLeft(v3);
        Vector2d upperRightYSame = v3.lowerLeft(v4);
        Vector2d upperRightBothSame = v2.lowerLeft(v2);

        // then
        assertEquals(upperRightBothDifferent, new Vector2d(-1, 6));
        assertEquals(upperRightXSame, new Vector2d(4, 8));
        assertEquals(upperRightYSame, new Vector2d(1, 10));
        assertEquals(upperRightBothSame, new Vector2d(4, 8));
    }

    /**
     * Test to check if Vector2d.opposite works properly (test covers:
     * - both x and y are positive
     * - both x and y are negative
     * - x and y have different signs
     * - x or y is zero)
     */
    @Test
    void isOppositeVectorRight() {
        // when
        Vector2d oppositeVectorPositiveNumbers = v4.opposite(); // both numbers are positive
        Vector2d oppositeVectorNegativeNumbers = v5.opposite(); // both numbers are negative
        Vector2d oppositeVectorDifferentSigns = v6.opposite(); // x and y have different signs
        Vector2d oppositeVectorZero = v9.opposite(); // x or y is zero

        // then
        assertEquals(oppositeVectorPositiveNumbers, new Vector2d(-1, -10));
        assertEquals(oppositeVectorNegativeNumbers, new Vector2d(1, 5));
        assertEquals(oppositeVectorDifferentSigns, new Vector2d(1, -7));
        assertEquals(oppositeVectorZero, new Vector2d(0, -3));
    }

    /**
     * Test to check if Vector2d.equals works properly (test covers:
     * - good vector
     * - vector as String
     */
    @Test
    void areVectorsEqual() {
        // given
        String vectorAsString = "(4, 8)";

        // then
        assertTrue(v2.equals(v8));
        assertFalse(v2.equals(vectorAsString));
    }

}