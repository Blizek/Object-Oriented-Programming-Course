package projekt.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import projekt.Simulation;
import projekt.SimulationEngine;
import projekt.model.*;
import projekt.model.maps.AbstractMap;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

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

    @FXML
    private Text animalsCounterText;

    @FXML
    private HBox gameStats;

    @FXML
    private Text grassCounterText;

    @FXML
    private Text freePlacesCounterText;

    @FXML
    private Text mostPopularGenText;

    @FXML
    private Text averageAnimalsEnergyText;

    @FXML
    private Text averageAnimalsLifetimeText;

    @FXML
    private Text averageAnimalsChildrenText;

    @FXML
    private AnchorPane chart1;

    @FXML
    private AnchorPane chart11;

    @FXML
    private AnchorPane messagesLog;

    @FXML
    private Text dayCounterText;

    @FXML
    private AnchorPane gameMapPane;

    @FXML
    private AnchorPane selectedAnimalStats;

    @FXML
    private ImageView watchingAnimalImage;

    @FXML
    private Text watchingAnimalPositionText;

    @FXML
    private Text watchingAnimalDirection;

    @FXML
    private Text watchingAnimalGenomeText;

    @FXML
    private HBox gameStats1;

    @FXML
    private Text watchingAnimalActualGenText;

    @FXML
    private Text watchingAnimalEnergyText;

    @FXML
    private Text watchingAnimalEatenGrassCounterText;

    @FXML
    private Text watchingAnimalChildrenCounterText;

    @FXML
    private Text watchingAnimalDescendantCounterText;

    @FXML
    private Text watchingAnimalLifetimeText;

    private double worldElementBoxHeight;
    private double worldElementBoxWidth;
    private double boxSize;

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void drawColumns() {
        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(boxSize));
        }
    }

    private void drawRows() {
        for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++) {
            mapGrid.getRowConstraints().add(new RowConstraints(boxSize));
        }
    }

//    private void numerateColumnAndRow() {
//        Label label = new Label("y/x");
//        mapGrid.add(label, 0, 0);
//        GridPane.setHalignment(label, HPos.CENTER);
//
//        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
//            label = new Label(String.valueOf(x));
//            mapGrid.add(label, x - lowerLeft.getX() + 1, 0);
//            GridPane.setHalignment(label, HPos.CENTER);
//        }
//
//        for (int y = upperRight.getY(); y >= lowerLeft.getY(); y--) {
//            label = new Label(String.valueOf(y));
//            mapGrid.add(label, 0, upperRight.getY() - y + 1);
//            GridPane.setHalignment(label, HPos.CENTER);
//        }
//    }

    private void fillMap() {
        List<WorldElement> elements = worldMap.getElements();
        for (WorldElement element : elements){
            if(element instanceof Animal || element instanceof Grass){
                WorldElementBox elementBox = new WorldElementBox(element, boxSize);
                mapGrid.add(elementBox, element.getPosition().getX() - lowerLeft.getX(), upperRight.getY() - element.getPosition().getY());
                GridPane.setHalignment(elementBox, HPos.CENTER);
            }
        }
    }

    private void drawMap() {
        clearGrid();

        drawColumns();
        drawRows();

        setGridCellsColors();
        // numerateColumnAndRow();

        fillMap();
    }

    @Override
    public void mapChanged(AbstractMap worldMap) {
        Platform.runLater(() -> {
            drawMap();
        });
    }

    private void setGridCellsColors() {
        for (int i = 0; i < upperRight.getX() + 1; i++) {
            for (int j = 0; j < upperRight.getY() + 1; j++) {
                Rectangle rectangle = new Rectangle(boxSize, boxSize);
                rectangle.setStyle("-fx-fill: #555555");
                mapGrid.add(rectangle, j, i);
            }
        }
    }

    public void startSimulation(Simulation simulation, AbstractMap worldMap) {
        try {
            this.worldMap = worldMap;
            worldMap.addObserver(this);

            Boundary mapBoundary = worldMap.getMapBoundary();
            lowerLeft = mapBoundary.lowerLeft();
            upperRight = mapBoundary.upperRight();

            int columnsCounter = upperRight.getY() + 2;
            int rowsCounter = upperRight.getX() + 2;

            double gridWidth = 812;
            double gridHeight = 753;

            worldElementBoxWidth = gridWidth / columnsCounter;
            worldElementBoxHeight = gridHeight / rowsCounter;
            boxSize = Math.min(worldElementBoxHeight, worldElementBoxWidth);

            List<Simulation> simulations = new ArrayList<>();
            simulations.add(simulation);
            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsync();


            drawColumns();
            drawRows();

            setGridCellsColors();
            // numerateColumnAndRow();

            fillMap();

            System.out.println("System zakończył działanie");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}