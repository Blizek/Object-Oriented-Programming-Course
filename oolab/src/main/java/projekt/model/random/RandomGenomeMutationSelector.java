package projekt.model.random;

import java.util.*;

public class RandomGenomeMutationSelector implements Iterable<Integer> {
    private final int genomeToMutateAmount;
    private final List<Integer> notMutatedGenomes = new ArrayList<>();
    private final Random random = new Random(); // static?

    public RandomGenomeMutationSelector(int genomeLength, int genomeToMutateAmount) {
        this.genomeToMutateAmount = genomeToMutateAmount;

        for (int i = 0; i < genomeLength; i++) {
            notMutatedGenomes.add(i);
        }
    }

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int currentMutatedCounter = 0;

            @Override
            public boolean hasNext() {
                return currentMutatedCounter < genomeToMutateAmount;
            }

            @Override
            public Integer next() {
                int randomGenomeIndex = random.nextInt(notMutatedGenomes.size());
                int tempLastIndex = notMutatedGenomes.getLast();
                int randomGenome = notMutatedGenomes.get(randomGenomeIndex);
                notMutatedGenomes.set(notMutatedGenomes.size() - 1, randomGenome);
                notMutatedGenomes.set(randomGenomeIndex, tempLastIndex);
                currentMutatedCounter++;
                return notMutatedGenomes.removeLast();
            }
        };
    }
}
