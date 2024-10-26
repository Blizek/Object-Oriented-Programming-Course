package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;

public class World {
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        MapDirection dir = MapDirection.NORTH;
        System.out.println(dir);
        System.out.println(dir.next());
        System.out.println(dir.previous());
        System.out.println(dir.toUnitVector());
    }
}
