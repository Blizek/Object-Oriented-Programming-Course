package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class World {
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(1, 2);

        Animal animal = new Animal();
        System.out.println(animal);
        Animal animal2 = new Animal(position1);
        System.out.println(animal.isAt(position1));
        System.out.println(animal2.isAt(position1));
        animal.move(MoveDirection.BACKWARD);
        System.out.println("Zwierzak 1: %s".formatted(animal));
        animal.move(MoveDirection.BACKWARD);
        System.out.println("Zwierzak 1: %s".formatted(animal));
        animal.move(MoveDirection.BACKWARD);
        System.out.println("Zwierzak 1: %s".formatted(animal));
        animal2.move(MoveDirection.RIGHT);
        System.out.println("Zwierzak 2: %s".formatted(animal2));
        animal2.move(MoveDirection.FORWARD);
        System.out.println("Zwierzak 2: %s".formatted(animal2));
        animal2.move(MoveDirection.FORWARD);
        System.out.println("Zwierzak 2: %s".formatted(animal2));
        animal2.move(MoveDirection.FORWARD);
        System.out.println("Zwierzak 2: %s".formatted(animal2));
        animal2.move(MoveDirection.FORWARD);
        System.out.println("Zwierzak 2: %s".formatted(animal2));

        List<MoveDirection> directions = OptionsParser.parseStringToMoveDirections(args);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();
    }
}
