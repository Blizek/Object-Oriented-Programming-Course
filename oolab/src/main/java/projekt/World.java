package projekt;

import projekt.model.GameMap;
import projekt.model.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class World {
    public static void main(String[] args) {
        Scanner scanner  = new Scanner(System.in);

        System.out.println("Wysokość mapy:");
        int mapHeight = scanner.nextInt();
        System.out.println("Szerokość mapy:");
        int mapWidth = scanner.nextInt();
        System.out.println("Startowa liczba roślin:");
        int startGrassAmount = scanner.nextInt();
        System.out.println("Energia zapewniania przez zjedzenie jednej rośliny:");
        int eatenGrassEnergy = scanner.nextInt();
        System.out.println("Liczba roślin wyrastająca każdego dnia:");
        int grassGrownAmount = scanner.nextInt();
        System.out.println("Startowa liczba zwierzaków:");
        int startAnimalsAmount = scanner.nextInt();
        System.out.println("Startowa energia zwierzaków:");
        int startAnimalEnergy = scanner.nextInt();
        System.out.println("Energia konieczna, by uznać zwierzaka za najedzonego (i gotowego do rozmnażania:");
        int minEnergyToFullAnimal = scanner.nextInt();
        System.out.println("Energia rodziców zużywana by stworzyć potomka:");
        int sexEnergyCost = scanner.nextInt();
        System.out.println("Minimalna liczba mutacji u potomków:");
        int minMutationAmount = scanner.nextInt();
        System.out.println("Maksymalna liczba mutacji u potomków:");
        int maxMutationAmount = scanner.nextInt();
        System.out.println("Długość genomu zwierzaków:");
        int animalGenomeLength = scanner.nextInt();

        try {
            GameMap map = new GameMap.Builder()
                    .setTopRightCorner(mapWidth, mapHeight)
                    .setStartGrassCount(startGrassAmount)
                    .build();

            Simulation simulation = new Simulation.Builder()
                    .setMap(map)
                    .setEatenGrassEnergy(eatenGrassEnergy)
                    .setGrassGrownAmount(grassGrownAmount)
                    .setStartAnimalsAmount(startAnimalsAmount)
                    .setStartAnimalEnergy(startAnimalEnergy)
                    .setMinEnergyToFullAnimal(minEnergyToFullAnimal)
                    .setSexEnergyCost(sexEnergyCost)
                    .setMinMutationAmount(minMutationAmount)
                    .setMaxMutationAmount(maxMutationAmount)
                    .setAnimalGenomeLength(animalGenomeLength)
                    .build();

            List<Simulation> simulations = new ArrayList<>();
            simulations.add(simulation);

            System.out.println(map);

//            SimulationEngine simEngine = new SimulationEngine(simulations);
//            simEngine.runAsyncInThreadPool();
//            simEngine.awaitSimulationEnd();
            simulation.run();
            System.out.println("System zakończył działanie");
        } catch (IllegalArgumentException e /*| InterruptedException e*/) {
            System.out.println(e.getMessage());
        }
    }
}
