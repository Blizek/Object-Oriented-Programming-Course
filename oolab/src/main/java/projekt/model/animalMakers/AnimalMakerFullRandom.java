package projekt.model.animalMakers;

import projekt.model.Animal;
import projekt.model.random.RandomGenomeMutationSelector;

import java.util.List;
import java.util.Random;

public class AnimalMakerFullRandom extends AbstractAnimalMaker {
    public AnimalMakerFullRandom(int minMutationCount, int maxMutationCount, int GenomeCount, int startAnimalEnergy, int sexEnergyCost) {
        super(minMutationCount, maxMutationCount, GenomeCount, startAnimalEnergy, sexEnergyCost);
    }

    @Override
    protected List<Integer> prepareAnimalGenome(Animal mother, Animal father) {
        List<Integer> childGenome = super.prepareAnimalGenome(mother, father);
        fullRandom(childGenome);
        return childGenome;
    }

    private void fullRandom(List<Integer> childGenome) {
        Random random = new Random();
        int mutations = random.nextInt(minMutationCount, maxMutationCount + 1);

        RandomGenomeMutationSelector genesIndexForFullMutations = new RandomGenomeMutationSelector(childGenome.size(), mutations);

        int newGene;
        for (int mutationIndex: genesIndexForFullMutations) {
            newGene = random.nextInt(8);
            childGenome.set(mutationIndex, newGene);
        }
    }
}
