package projekt.model.animalMakers;

import projekt.model.Animal;
import projekt.model.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractAnimalMaker {
    protected final int minMutationCount;
    protected final int maxMutationCount;
    protected final int GenomeCount;
    protected final int startAnimalEnergy;
    protected final int sexEnergyCost;
    protected final int minEnergyToFullAnimal;

    public AbstractAnimalMaker(int minMutationCount, int maxMutationCount, int GenomeCount, int startAnimalEnergy, int sexEnergyCost, int minEnergyToFullAnimal) {
        this.minMutationCount = minMutationCount;
        this.maxMutationCount = maxMutationCount;
        this.GenomeCount = GenomeCount;
        this.startAnimalEnergy = startAnimalEnergy;
        this.sexEnergyCost = sexEnergyCost;
        this.minEnergyToFullAnimal = minEnergyToFullAnimal;
    }

    public Animal makeAnimal(Vector2d position) {
        List<Integer> genome = new ArrayList<>();
        for (int i = 0; i < GenomeCount; i++) {
            genome.add((int) (Math.random() * 8));
        }
        return new Animal(position, startAnimalEnergy, genome, null, null, minEnergyToFullAnimal);
    }

    public Animal makeAnimalFromParents(Animal mother, Animal father) {
        int combinedEnergy = mother.getEnergy() + father.getEnergy();

        List<Integer> motherGenome = mother.getGenome();
        List<Integer> fatherGenome = father.getGenome();

        List<Integer> childGenome = new ArrayList<>();

        int fatherGenes;
        int motherGenes;

        float fatherPercent = (float) father.getEnergy() / combinedEnergy;
        float motherPercent = (float) mother.getEnergy() / combinedEnergy;

        fatherGenes = (int) (fatherPercent * GenomeCount);
        motherGenes = (int) (motherPercent * GenomeCount);


        Random random = new Random();
        int geneSide = random.nextInt(2);

        if (geneSide == 0) {
            for (int i = 0; i < fatherGenes; i++) {
                childGenome.add(fatherGenome.get(i));
            }
            for (int i = fatherGenes; i < GenomeCount; i++) {
                childGenome.add(motherGenome.get(i));
            }
        } else {
            for (int i = 0; i < motherGenes; i++) {
                childGenome.add(motherGenome.get(i));
            }
            for (int i = motherGenes; i < GenomeCount; i++) {
                childGenome.add(fatherGenome.get(i));
            }
        }

        modifyGenome(childGenome);

        father.loseEnergy(sexEnergyCost);
        mother.loseEnergy(sexEnergyCost);

        Animal child = new Animal(mother.getPosition(), sexEnergyCost * 2, childGenome, mother, father, minEnergyToFullAnimal);
        mother.setChildren(child);
        father.setChildren(child);
        addDescendantCount(father);
        addDescendantCount(mother);
        return child;
    }

    protected abstract void modifyGenome(List<Integer> childGenome);

    private void addDescendantCount(Animal animal) {
        animal.newDescendant();
        if (animal.getMother() == null) return; // obviously, if there is no mother, there is no father
        addDescendantCount(animal.getMother());
        addDescendantCount(animal.getFather());
    }
}
