package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap worldMap;
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    @FXML
    private Label infoLabel;
    @FXML
    private TextField animalsMovesTextField;
    @FXML
    private GridPane mapGrid;

    public void setWorldMap(WorldMap map) {
        this.worldMap = map;
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void drawColumns() {
        for (int x = lowerLeft.getX(); x <= upperRight.getX() + 1; x++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(35));
        }
    }

    private void drawRows() {
        for (int y = lowerLeft.getY(); y <= upperRight.getY() + 1; y++) {
            mapGrid.getRowConstraints().add(new RowConstraints(35));
        }
    }

    private void numerateColumnAndRow() {
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);

        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            label = new Label(String.valueOf(x));
            mapGrid.add(label, x - lowerLeft.getX() + 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int y = upperRight.getY(); y >= lowerLeft.getY(); y--) {
            label = new Label(String.valueOf(y));
            mapGrid.add(label, 0, upperRight.getY() - y + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void fillTMap() {
        List<WorldElement> elements = worldMap.getElements();
        for (WorldElement element : elements){
            if(element instanceof Animal || worldMap.objectAt(element.getPosition()) instanceof Grass){
                Text text = new Text(element.toString());
                GridPane.setHalignment(text, HPos.CENTER);
                mapGrid.add(text, element.getPosition().getX() - lowerLeft.getX() + 1, upperRight.getY() - element.getPosition().getY() + 1);
            }
        }
    }

    private void drawMap() {
        clearGrid();
        
        Boundary mapBoundary = worldMap.getCurrentBounds();
        lowerLeft = mapBoundary.lowerLeft();
        upperRight = mapBoundary.upperRight();

        drawColumns();
        drawRows();

        numerateColumnAndRow();

        fillTMap();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            infoLabel.setText(message);
        });
    }

    public void onSimulationStartClicked() {
        try {
            String animalsMoveString = animalsMovesTextField.getText();
            List<MoveDirection> animalsMovesAsList = OptionsParser.parseStringToMoveDirections(animalsMoveString.split(" "));

            Vector2d position1 = new Vector2d(2, 2);
            Vector2d position2 = new Vector2d(1, 2);
            List<Vector2d> animalsStartPositions = new ArrayList<>();
            animalsStartPositions.add(position1);
            animalsStartPositions.add(position2);

            GrassField map = new GrassField(10);
            map.addObserver(this);
            setWorldMap(map);

            Simulation simulation = new Simulation(animalsStartPositions, animalsMovesAsList, map);
            List<Simulation> simulations = new ArrayList<>();
            simulations.add(simulation);
            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsync();
            System.out.println("System zakończył działanie");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
