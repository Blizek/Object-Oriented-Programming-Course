package projekt.model.animalMakers;

import projekt.model.Animal;

import java.util.List;
import java.util.Random;

public class AnimalMakerSlightCorrection extends AbstractAnimalMaker{
    public AnimalMakerSlightCorrection(int minMutationCount, int maxMutationCount, int GenomeCount, int startAnimalEnergy, int sexEnergyCost, int minEnergyToFullAnimal) {
        super(minMutationCount, maxMutationCount, GenomeCount, startAnimalEnergy, sexEnergyCost, minEnergyToFullAnimal);
    }

    @Override
    protected void modifyGenome(List<Integer> childGenome) {
        Random random = new Random();
        int isNewGene; // if we change gene or not
        int isOneDownOrUp; // if the gene changes one up or one down
        for (int i = 0; i < childGenome.size(); i++) {
            isNewGene = random.nextInt(2); // 1 -> change the gene, 0 -> continue
            if (isNewGene == 1) {
                isOneDownOrUp = random.nextInt(2); // 1 -> increase gene about 1, 0 -> decrease gene about 1
                int change;
                if (isOneDownOrUp == 1) {
                    change = 1;
                } else {
                    change = -1;
                }
                int genome_i = childGenome.get(i);
                genome_i = (genome_i + change) % 8;
                if (genome_i == -1) genome_i = 7;
                childGenome.set(i, genome_i);
            }
        }
    }
}
