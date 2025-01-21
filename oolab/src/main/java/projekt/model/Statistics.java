package projekt.model;

import projekt.model.maps.EarthMap;

import java.util.*;
import java.util.stream.Collectors;

public class Statistics {
    private Statistics(){
        throw new UnsupportedOperationException("Utility class");
    }

    public static int getAllAnimalsCount(List<Animal> animals){
        return animals.size();
    }

    public static int getAllGrassesCount(List<Grass> grasses){
        return grasses.size();
    }

    public static int getAllFreeSpacesCount(EarthMap gameMap){
        Set<Vector2d> keysSet = new HashSet<>(gameMap.getAnimalsMap().keySet());
        keysSet.addAll(gameMap.getGrassesMap().keySet());
        return gameMap.getMapArea() - keysSet.size();
    }

    public static List<List<Integer>> getTopMostPopularGenomes(List<Animal> animals, int topCount){
        Map<List<Integer>, Integer> genomeCount = new HashMap<>();
        for(Animal animal : animals){
            List<Integer> genome = animal.getGenome();
            genomeCount.put(genome, genomeCount.getOrDefault(genome, 0) + 1);
        }
        return genomeCount.entrySet().stream()
                .sorted(Map.Entry.<List<Integer>, Integer>comparingByValue().reversed())
                .limit(topCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static List<Integer> getMostPopularGenome(List<Animal> animals){
        return getTopMostPopularGenomes(animals, 1).getFirst();
    }

    public static float getAverageEnergy(List<Animal> animals){
        return (float) animals.stream().mapToInt(Animal::getEnergy).sum() / animals.size();
    }

    public static float getAverageDaysLived(List<Animal> animals){
        return (float) animals.stream().mapToInt(Animal::getDaysLived).sum() / animals.size();
    }

    public static float getAverageChildrenCount(List<Animal> animals){
        return (float) animals.stream().mapToInt(Animal::getChildrenCount).sum() / animals.size();
    }
}
