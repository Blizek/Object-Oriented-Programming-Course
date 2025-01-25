package projekt.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import projekt.Simulation;
import projekt.SimulationEngine;
import projekt.model.*;
import projekt.model.maps.AbstractMap;

import java.util.ArrayList;
import java.util.List;

public class GameMapPresenter implements MapChangeListener {
    private AbstractMap worldMap;
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    @FXML
    private Label infoLabel;
    @FXML
    private TextField animalsMovesTextField;
    @FXML
    private GridPane mapGrid;



    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
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

    private void fillMap() {
        List<WorldElement> elements = worldMap.getElements();
        for (WorldElement element : elements){
            if(element instanceof Animal || worldMap.objectAt(element.getPosition()) instanceof Grass){
                WorldElementBox elementBox = new WorldElementBox(element);
                mapGrid.add(elementBox, element.getPosition().getX() - lowerLeft.getX() + 1, upperRight.getY() - element.getPosition().getY() + 1);
                GridPane.setHalignment(elementBox, HPos.CENTER);
            }
        }
    }

    private void drawMap() {
        clearGrid();

        Boundary mapBoundary = worldMap.getMapBoundary();
        lowerLeft = mapBoundary.lowerLeft();
        upperRight = mapBoundary.upperRight();

        drawColumns();
        drawRows();

        numerateColumnAndRow();

        fillMap();
    }

    @Override
    public void mapChanged(AbstractMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            infoLabel.setText(message);
        });
    }

    public void startSimulation(Simulation simulation, AbstractMap worldMap) {
        try {
            this.worldMap = worldMap;

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