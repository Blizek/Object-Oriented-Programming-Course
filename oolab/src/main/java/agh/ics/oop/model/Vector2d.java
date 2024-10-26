package agh.ics.oop.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Method to check if 'other' Vector2d has bigger or equal x and y position than checking object Vector2d
     * @param other another Vector2d object to compare
     * @return boolean if 'other' Vector2d has bigger or equal x and y position
     */
    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    /**
     * Method to check if 'other' Vector2d has less or equal x and y position than checking object
     * @param other another Vector2d object to compare
     * @return boolean if 'other' Vector2d has less or equal x and y position
     */
    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    /**
     * Method to create new Vector2d object which is a result of summary two vectors
     * @param other vector which we add to this Vector2d
     * @return new Vector2d object which is summary of this and 'other' Vector2d
     */
    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    /**
     * Method to create new Vector2d object which is a result of subtract two vectors
     * @param other vector which we subtract to this Vector2d
     * @return new Vector2d object which is subtract of this and 'other' Vector2d
     */
    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    /**
     * Creating a new Vector2d object that has max x value of two Vectors2d and max y value of two Vectors2d
     * @param other second Vector2d
     * @return new Vector2d that has max value of x and y from two Vectors2d
     */
    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(x, other.x), Math.max(y, other.y));
    }

    /**
     * Creating a new Vector2d object that has min x value of two Vectors2d and min y value of two Vectors2d
     * @param other second Vector2d
     * @return new Vector2d that has min value of x and y from two Vectors2d
     */
    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(x, other.x), Math.min(y, other.y));
    }

    /**
     * Creating an opposite Vector2d to this
     * @return new Vector2d that has both variables opposite to this one
     */
    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Checking if an object is equal to checking one
     * @param other object of class Object (doesn't have to be 'Vector2d' class object
     * @return boolean if two Vectors2d are equal (if the 'other' object isn't Vector2d object return false)
     */
    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d otherAsVector2d = (Vector2d) other;
        return x == otherAsVector2d.x && y == otherAsVector2d.y;
    }
}
