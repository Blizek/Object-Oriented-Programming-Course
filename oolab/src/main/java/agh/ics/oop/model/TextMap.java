package agh.ics.oop.model;

import java.util.*;

public class TextMap implements WorldNumberPositionMap<String, Integer>{
    private final ArrayList<String> worldMap;
    private final ArrayList<String> wordsList;

    public TextMap(ArrayList<String> wordsList){
        this.wordsList = wordsList;
        this.worldMap = new ArrayList<>(wordsList);
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return position >= 0 && position < worldMap.size();
    }

    @Override
    public boolean place(String text) {
        if(canBePlaced(text)) {
            wordsList.add(text);
            worldMap.add(text);
            return true;
        }
        return false;
    }

    private boolean canBePlaced(String text){
        return !wordsList.contains(text);
    }

    @Override
    public void move(String text, MoveDirection direction) {
        int currentPosition = worldMap.indexOf(text);

        int moveDirection = switch(direction){
            case FORWARD -> 1;
            case BACKWARD -> -1;
            case RIGHT -> 1;
            case LEFT -> -1;
        };

        int nextPosition = currentPosition + moveDirection;

        if(canMoveTo(nextPosition)){
            worldMap.set(currentPosition, worldMap.get(nextPosition));
            worldMap.set(nextPosition, text);
        }
    }

    @Override
    public boolean isOccupied(Integer position) {
        return position < worldMap.size() && position >= 0;
    }

    @Override
    public String objectAt(Integer position) {
        return worldMap.get(position);
    }

    @Override
    public List<String> setObjectsOnMap(List<Integer> positions) {
        return List.copyOf(wordsList);
    }

    @Override
    public String toString(){
        return worldMap.toString();
    }
}