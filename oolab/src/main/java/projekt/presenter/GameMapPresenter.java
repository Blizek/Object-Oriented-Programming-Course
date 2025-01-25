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
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GameMapPresenter implements MapChangeListener {
    @FXML
    private GridPane mapGrid;

    @FXML
    private Text animalsCounterText,  grassCounterText, freePlacesCounterText,  mostPopularGenText, averageAnimalsEnergyText,
            averageAnimalsLifetimeText, averageAnimalsChildrenText, dayCounterText, watchingAnimalPositionText,
            watchingAnimalDirection, watchingAnimalGenomeText, watchingAnimalActualGenText, watchingAnimalEnergyText,
            watchingAnimalEatenGrassCounterText, watchingAnimalChildrenCounterText, watchingAnimalDescendantCounterText,
            watchingAnimalLifetimeText;

    @FXML
    ImageView watchingAnimalImage;

    @FXML
    private HBox gameStats;

    @FXML
    private AnchorPane chart1, chart2, messagesLog, gameMapPane, selectedAnimalStats;

    private AbstractMap worldMap;
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private double boxSize;
    private List<Animal> animalsList;
    private Simulation simulation;
    private HashMap<Vector2d, Grass> grassesMap;

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
            if(element instanceof Animal || worldMap.objectAt(element.getPosition()).getFirst() instanceof Grass){
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
            updateSimulationStatistics();
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

            this.simulation = simulation;

            Boundary mapBoundary = worldMap.getMapBoundary();
            lowerLeft = mapBoundary.lowerLeft();
            upperRight = mapBoundary.upperRight();

            int columnsCounter = upperRight.getY() + 2;
            int rowsCounter = upperRight.getX() + 2;

            double gridWidth = 812;
            double gridHeight = 753;

            double worldElementBoxWidth = gridWidth / columnsCounter;
            double worldElementBoxHeight = gridHeight / rowsCounter;
            boxSize = Math.min(worldElementBoxHeight, worldElementBoxWidth);

            List<Simulation> simulations = new ArrayList<>();
            simulations.add(simulation);
            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsync();

            animalsList = simulation.getAnimalsList();

            drawColumns();
            drawRows();

            setGridCellsColors();
            // numerateColumnAndRow();

            fillMap();
            updateSimulationStatistics();

            System.out.println("System zakończył działanie");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateSimulationStatistics() {
        animalsList = simulation.getAnimalsList();
        grassesMap = worldMap.getGrassesMap();

        animalsCounterText.setText(Integer.toString(Statistics.getAllAnimalsCount(animalsList)));
        grassCounterText.setText(Integer.toString(Statistics.getAllGrassesCount(grassesMap)));
        freePlacesCounterText.setText(Integer.toString(Statistics.getAllFreeSpacesCount(worldMap)));
        mostPopularGenText.setText(Statistics.getMostPopularGenome(animalsList).stream().map(String::valueOf).collect(Collectors.joining(", ")));
        averageAnimalsEnergyText.setText(Float.toString(Statistics.getAverageEnergy(animalsList)));
        averageAnimalsLifetimeText.setText(Float.toString(Statistics.getAverageDaysLived(animalsList)));
        averageAnimalsChildrenText.setText(Float.toString(Statistics.getAverageChildrenCount(animalsList)));
    }
}