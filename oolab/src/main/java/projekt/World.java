package projekt;

import projekt.model.GameMap;
import projekt.model.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class World {
    public static void main(String[] args) {
        Scanner scanner  = new Scanner(System.in);
        int mapHeight = 1;
        int mapWidth = 1;
        int startGrassAmount = 4;
        int eatenGrassEnergy = 20;
        int grassGrownAmount = 4;
        int startAnimalsAmount = 5;
        int startAnimalEnergy = 50;
        int minEnergyToFullAnimal = 10;
        int sexEnergyCost = 5;
        int minMutationAmount = 0;
        int maxMutationAmount = 5;
        int animalGenomeLength = 8;

        System.out.println("chcesz stworzyc wlasna mape? (t/n)");
        if ( scanner.nextLine().equals("t") ) {
            System.out.println("Wysokość mapy:");
            mapHeight = scanner.nextInt();
            System.out.println("Szerokość mapy:");
            mapWidth = scanner.nextInt();
            System.out.println("Startowa liczba roślin:");
            startGrassAmount = scanner.nextInt();
            System.out.println("Energia zapewniania przez zjedzenie jednej rośliny:");
            eatenGrassEnergy = scanner.nextInt();
            System.out.println("Liczba roślin wyrastająca każdego dnia:");
            grassGrownAmount = scanner.nextInt();
            System.out.println("Startowa liczba zwierzaków:");
            startAnimalsAmount = scanner.nextInt();
            System.out.println("Startowa energia zwierzaków:");
            startAnimalEnergy = scanner.nextInt();
            System.out.println("Energia konieczna, by uznać zwierzaka za najedzonego (i gotowego do rozmnażania:");
            minEnergyToFullAnimal = scanner.nextInt();
            System.out.println("Energia rodziców zużywana by stworzyć potomka:");
            sexEnergyCost = scanner.nextInt();
            System.out.println("Minimalna liczba mutacji u potomków:");
            minMutationAmount = scanner.nextInt();
            System.out.println("Maksymalna liczba mutacji u potomków:");
            maxMutationAmount = scanner.nextInt();
            System.out.println("Długość genomu zwierzaków:");
            animalGenomeLength = scanner.nextInt();
        }

        try {
            GameMap map = new GameMap.Builder()
                    .setTopRightCorner(mapWidth, mapHeight)
                    .setStartGrassCount(startGrassAmount)
                    .setGrassEnergy(eatenGrassEnergy)
                    .setGrassGrowthCount(grassGrownAmount)
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
