package projekt.model;

import projekt.model.maps.AbstractMap;

import java.util.*;
import java.util.stream.Collectors;

public class Statistics {
    private Statistics() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int getAllAnimalsCount(List<Animal> animals) {
        return animals.size();
    }

    public static int getAllGrassesCount(HashMap<Vector2d, Grass> grasses) {
        return grasses.size();
    }

    public static int getAllFreeSpacesCount(AbstractMap gameMap) {
        Set<Vector2d> keysSet = new HashSet<>(gameMap.getAnimalsMap().keySet());
        keysSet.addAll(gameMap.getGrassesMap().keySet());
        return gameMap.getMapArea() - keysSet.size();
    }

    public static List<Integer> getMostPopularGenome(List<Animal> animals) {
        return getTopMostPopularGenomes(animals, 1);
    }

    public static List<Integer> getTopMostPopularGenomes(List<Animal> animals, int topCount) {
        Map<Integer, Integer> geneCount = new HashMap<>();
        for (Animal animal : animals) {
            List<Integer> genome = animal.getGenome();
            for (Integer gene : genome) {
                geneCount.put(gene, geneCount.getOrDefault(gene, 0) + 1);
            }
        }

        List<Map.Entry<Integer, Integer>> sortedGeneCount = geneCount.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        int thresholdCount = sortedGeneCount.get(topCount - 1).getValue();

        return sortedGeneCount.stream()
                .filter(entry -> entry.getValue() >= thresholdCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static float getAverageEnergy(List<Animal> animals) {
        return (float) (Math.round(((float) animals.stream().mapToInt(Animal::getEnergy).sum() / animals.size()) * 100.0) / 100.0);
    }

    public static float getAverageDaysLived(List<Animal> animals) {
        return (float) (Math.round(((float) animals.stream().mapToInt(Animal::getDaysLived).sum() / animals.size()) * 100.0) / 100.0);
    }

    public static float getAverageChildrenCount(List<Animal> animals) {
        return (float) (Math.round(((float) animals.stream().mapToInt(Animal::getChildrenCount).sum() / animals.size()) * 100.0) / 100.0);
    }
}
