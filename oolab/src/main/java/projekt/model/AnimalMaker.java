package projekt.model;

import projekt.model.random.RandomGenomeMutationSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalMaker implements ElementMaker<Animal> {
    private final int minMutationCount;
    private final int maxMutationCount;
    private final int GenomeCount;
    private final int startAnimalEnergy;
    private final int sexEnergyCost;

    public AnimalMaker(int minMutationCount, int maxMutationCount, int GenomeCount, int startAnimalEnergy, int sexEnergyCost){
        this.minMutationCount = minMutationCount;
        this.maxMutationCount = maxMutationCount;
        this.GenomeCount = GenomeCount;
        this.startAnimalEnergy = startAnimalEnergy;
        this.sexEnergyCost = sexEnergyCost;
    }
    @Override
    public Animal makeAnimal(Vector2d position) {
        List<Integer> genome = new ArrayList<>();
        for(int i = 0; i < GenomeCount; i++){
            genome.add((int)(Math.random() * 8));
        }
        return new Animal(position, startAnimalEnergy, genome, null, null);
    }

    public Animal makeAnimalFromParents(Animal mother, Animal father){
        System.out.println("Making child");
        int combinedEnergy = mother.getEnergy() + father.getEnergy();

        List<Integer> motherGenome = mother.getGenome();
        List<Integer> fatherGenome = father.getGenome();

        List<Integer> childGenome = new ArrayList<>();

        float fatherPercent = (float) father.getEnergy() / combinedEnergy;
        float motherPercent = (float) mother.getEnergy() / combinedEnergy;

        int fatherGenes = (int) fatherPercent * GenomeCount;
        int motherGenes = (int) motherPercent * GenomeCount;


        Random random = new Random();
        int geneSide = random.nextInt(2);

        if(geneSide == 0){
            for(int i = 0; i < fatherGenes; i++){
                childGenome.add(fatherGenome.get(i));
            }
            for(int i = fatherGenes; i < GenomeCount; i--){
                childGenome.add(motherGenome.get(i));
            }
        }else{
            for(int i = 0; i < motherGenes; i++){
                childGenome.add(motherGenome.get(i));
            }
            for(int i = motherGenes; i < GenomeCount; i--){
                childGenome.add(fatherGenome.get(i));
            }
        }
        int mutations = random.nextInt(minMutationCount, maxMutationCount + 1);

        RandomGenomeMutationSelector genesIndexForFullMutations = new RandomGenomeMutationSelector(childGenome.size(), mutations);

        int newGene;
        for (int mutationIndex: genesIndexForFullMutations) {
            newGene = random.nextInt(8);
            childGenome.set(mutationIndex, newGene);
        }

        int isNewGene; // if we change gene or not
        int isOneDownOrUp; // if the gene changes one up or one down
        for (int i = 0; i < childGenome.size(); i++) {
            isNewGene = random.nextInt(); // 1 -> change the gene, 0 -> continue
            if (isNewGene == 1) {
                isOneDownOrUp = random.nextInt(); // 1 -> increase gene about 1, 0 -> decrease gene about 1
                int change;
                if (isOneDownOrUp == 1) {
                    change = 1;
                } else {
                    change = -1;
                }
                int genome_i = childGenome.get(i);
                genome_i = (genome_i + change) % 8;
                childGenome.set(i, genome_i);
            }
        }

        father.loseEnergy(sexEnergyCost);
        mother.loseEnergy(sexEnergyCost);

        Animal child = new Animal(mother.getPosition(), sexEnergyCost*2, childGenome, mother, father);
        mother.setChildren(child);
        father.setChildren(child);


        return child;
    }

}
