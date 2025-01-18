package projekt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalMaker implements ElementMaker<Animal> {
    private final int minMutationCount;
    private final int maxMutationCount;
    private final int GenomeCount;
    private final int startAnimalEnergy;

    public AnimalMaker(int minMutationCount, int maxMutationCount, int GenomeCount, int startAnimalEnergy){
        this.minMutationCount = minMutationCount;
        this.maxMutationCount = maxMutationCount;
        this.GenomeCount = GenomeCount;
        this.startAnimalEnergy = startAnimalEnergy;
    }
    @Override
    public Animal make(Vector2d position) {
        List<Integer> genome = new ArrayList<>();
        for(int i = 0; i < GenomeCount; i++){
            genome.add((int)(Math.random() * 8));
        }
        return new Animal(position, startAnimalEnergy, genome, null, null);
    }

    public Animal fromParentsMake(Animal mother, Animal father){
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
            for(int i = fatherGenes; i < GenomeCount; i++){
                childGenome.add(motherGenome.get(i));
            }
        }else{
            for(int i = 0; i < motherGenes; i++){
                childGenome.add(motherGenome.get(i));
            }
            for(int i = motherGenes; i < GenomeCount; i++){
                childGenome.add(fatherGenome.get(i));
            }
        }
        return new Animal(mother.getPosition(), startAnimalEnergy, childGenome, mother, father);
    }

}
