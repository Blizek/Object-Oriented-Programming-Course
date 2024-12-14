package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap worldMap;
    @FXML
    private Label infoLabel;
    @FXML
    private TextField textField;

    public void setWorldMap(WorldMap map) {
        this.worldMap = map;
    }

    public void drawMap() {

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
            String animalsMoveString = textField.getText();
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
