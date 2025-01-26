package projekt.model.util;

public class ConfigData {
    private int mapHeight;
    private int mapWidth;
    private int startGrassAmount;
    private int eatenGrassEnergy;
    private int grassGrownAmount;
    private int startAnimalsAmount;
    private int startAnimalEnergy;
    private int minEnergyToFullAnimal;
    private int sexEnergyCost;
    private int minMutationAmount;
    private int maxMutationAmount;
    private int animalGenomeLength;
    private int gameplaySpeed;
    private boolean isColdWarGameplay;
    private boolean isSlightCorrection;

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getStartGrassAmount() {
        return startGrassAmount;
    }

    public void setStartGrassAmount(int startGrassAmount) {
        this.startGrassAmount = startGrassAmount;
    }

    public int getEatenGrassEnergy() {
        return eatenGrassEnergy;
    }

    public void setEatenGrassEnergy(int eatenGrassEnergy) {
        this.eatenGrassEnergy = eatenGrassEnergy;
    }

    public int getGrassGrownAmount() {
        return grassGrownAmount;
    }

    public void setGrassGrownAmount(int grassGrownAmount) {
        this.grassGrownAmount = grassGrownAmount;
    }

    public int getStartAnimalsAmount() {
        return startAnimalsAmount;
    }

    public void setStartAnimalsAmount(int startAnimalsAmount) {
        this.startAnimalsAmount = startAnimalsAmount;
    }

    public int getStartAnimalEnergy() {
        return startAnimalEnergy;
    }

    public void setStartAnimalEnergy(int startAnimalEnergy) {
        this.startAnimalEnergy = startAnimalEnergy;
    }

    public int getMinEnergyToFullAnimal() {
        return minEnergyToFullAnimal;
    }

    public void setMinEnergyToFullAnimal(int minEnergyToFullAnimal) {
        this.minEnergyToFullAnimal = minEnergyToFullAnimal;
    }

    public int getSexEnergyCost() {
        return sexEnergyCost;
    }

    public void setSexEnergyCost(int sexEnergyCost) {
        this.sexEnergyCost = sexEnergyCost;
    }

    public int getMinMutationAmount() {
        return minMutationAmount;
    }

    public void setMinMutationAmount(int minMutationAmount) {
        this.minMutationAmount = minMutationAmount;
    }

    public int getMaxMutationAmount() {
        return maxMutationAmount;
    }

    public void setMaxMutationAmount(int maxMutationAmount) {
        this.maxMutationAmount = maxMutationAmount;
    }

    public int getAnimalGenomeLength() {
        return animalGenomeLength;
    }

    public void setAnimalGenomeLength(int animalGenomeLength) {
        this.animalGenomeLength = animalGenomeLength;
    }

    public int getGameplaySpeed() {
        return gameplaySpeed;
    }

    public void setGameplaySpeed(int gameplaySpeed) {
        this.gameplaySpeed = gameplaySpeed;
    }

    public boolean isColdWarGameplay() {
        return isColdWarGameplay;
    }

    public void setColdWarGameplay(boolean coldWarGameplay) {
        isColdWarGameplay = coldWarGameplay;
    }

    public boolean isSlightCorrection() {
        return isSlightCorrection;
    }

    public void setSlightCorrection(boolean slightCorrection) {
        isSlightCorrection = slightCorrection;
    }
}
