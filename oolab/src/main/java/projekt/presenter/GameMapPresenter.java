package projekt.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
            watchingAnimalLifetimeText, watchingAnimalDeadDayText;

    @FXML
    private ImageView watchingAnimalImage;

    @FXML
    private HBox gameStats;

    @FXML
    private AnchorPane chart1, chart2, messagesLog, gameMapPane, selectedAnimalStats;

    @FXML
    private Button runStopGameplayButton, showPreferredFieldsButton, showAnimalsWithMostPopularGeneButton, endGameButton;

    @FXML
    private LineChart<String, Integer> animalsChart, grassesChart;

    private AbstractMap worldMap;
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private double boxSize;
    private List<Animal> animalsList;
    private Simulation simulation;
    private HashMap<Vector2d, Grass> grassesMap;
    private boolean isShowPreferredFields = false;
    private boolean isShowAnimalsWithMostPopularGene = false;
    private Animal watchingAnimal;
    private long watchingAnimalDeadDay;
    XYChart.Series<String, Integer> animalSeries = new XYChart.Series<>();
    XYChart.Series<String, Integer> grassesSeries = new XYChart.Series<>();
    private Thread simulationThread;

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
                if (element instanceof Animal) {
                    if (element != worldMap.dominantAnimalAtPosition(new Vector2d(element.getPosition().getX(), element.getPosition().getY())))
                        continue;
                    elementBox.setOnMouseClicked(event -> watchAnimalStats(new Vector2d(element.getPosition().getX(), element.getPosition().getY())));
                }
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
        if (watchingAnimal != null && watchingAnimalDeadDay == -1) {
            setGridCellsColors(List.of(watchingAnimal.getPosition()), "f5b600");
        }

        fillMap();
    }

    @Override
    public void mapChanged(AbstractMap worldMap) {
        Platform.runLater(() -> {
            drawMap();
            updateSimulationStatistics();
            updateWatchingAnimalStats();
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

    private void setGridCellsColors(Vector2d lowerLeft, Vector2d upperRight) {
        // setGridCellsColors();
        for (int i = lowerLeft.getX(); i <= upperRight.getX(); i++) {
            for (int j = lowerLeft.getY(); j <= upperRight.getY(); j++) {
                Rectangle rectangle = new Rectangle(boxSize, boxSize);
                rectangle.setStyle("-fx-fill: #4caf50");
                mapGrid.add(rectangle, i, j);
            }
        }
    }

    private void setGridCellsColors(List<Vector2d> positions, String hexColor) {
        // setGridCellsColors();
        for (Vector2d position : positions) {
            Rectangle rectangle = new Rectangle(boxSize, boxSize);
            rectangle.setStyle("-fx-fill: #" + hexColor);
            mapGrid.add(rectangle, position.getX() - lowerLeft.getX(), upperRight.getY() - position.getY());
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

            animalsChart.setLegendVisible(false);
            grassesChart.setLegendVisible(false);

            animalsChart.getData().add(animalSeries);
            grassesChart.getData().add(grassesSeries);

            simulationThread = new Thread(simulation);
            simulationThread.setDaemon(true);
            simulation.startGame(this.simulationThread);

            animalsList = simulation.getAnimalsList();

            drawColumns();
            drawRows();

            setGridCellsColors();
            // numerateColumnAndRow();

            fillMap();
            updateSimulationStatistics();

            Stage stage = (Stage) endGameButton.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                try {
                    closeThreadAndWindow(event);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            System.out.println("System zakończył działanie");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateSimulationStatistics() {
        animalsList = simulation.getAnimalsList();
        grassesMap = worldMap.getGrassesMap();

        if (!animalsList.isEmpty()) {
            animalsCounterText.setText(Integer.toString(Statistics.getAllAnimalsCount(animalsList)));
            grassCounterText.setText(Integer.toString(Statistics.getAllGrassesCount(grassesMap)));
            freePlacesCounterText.setText(Integer.toString(Statistics.getAllFreeSpacesCount(worldMap)));
            List<Integer> mostPopularGenome = Statistics.getMostPopularGenome(animalsList);
            if (mostPopularGenome.isEmpty()) mostPopularGenText.setText("-");
            else
                mostPopularGenText.setText(Statistics.getMostPopularGenome(animalsList).stream().map(String::valueOf).collect(Collectors.joining(", ")));
            averageAnimalsEnergyText.setText(Float.toString(Statistics.getAverageEnergy(animalsList)));
            averageAnimalsLifetimeText.setText(Float.toString(Statistics.getAverageDaysLived(animalsList)));
            averageAnimalsChildrenText.setText(Float.toString(Statistics.getAverageChildrenCount(animalsList)));
            dayCounterText.setText(Long.toString(simulation.getDay()));

            animalSeries.getData().add(new XYChart.Data<>(Long.toString(simulation.getDay()), Statistics.getAllAnimalsCount(animalsList)));
            grassesSeries.getData().add(new XYChart.Data<>(Long.toString(simulation.getDay()), Statistics.getAllGrassesCount(grassesMap)));
        }
    }

    public void runOrStopGameplay() throws InterruptedException {
        boolean isRunning = simulation.isRunning();

        if (isRunning) {
            runStopGameplayButton.setText("Wznów rozgrywkę");
            showPreferredFieldsButton.setDisable(false);
            showAnimalsWithMostPopularGeneButton.setDisable(false);
            simulation.stopGame(simulationThread);
        } else {
            runStopGameplayButton.setText("Zatrzymaj rozgrywkę");
            showPreferredFieldsButton.setDisable(true);
            showAnimalsWithMostPopularGeneButton.setDisable(true);
            isShowPreferredFields = false;
            isShowAnimalsWithMostPopularGene = false;
            simulationThread = new Thread(simulation);
            simulation.startGame(simulationThread);
        }
    }

    public void showPreferredFields() {
        clearGrid();
        drawColumns();
        drawRows();
        setGridCellsColors();
        if (watchingAnimal != null && watchingAnimalDeadDay == -1) setGridCellsColors(List.of(watchingAnimal.getPosition()), "f5b600");
        if (isShowAnimalsWithMostPopularGene) colorAnimalWithMostPopularGene();
        if (!isShowPreferredFields) {
            isShowPreferredFields = true;
            colorPreferredFields();
        } else isShowPreferredFields = false;
        fillMap();
    }

    public void showAnimalsWithMostPopularGene() {
        clearGrid();
        drawColumns();
        drawRows();
        setGridCellsColors();
        if (watchingAnimal != null && watchingAnimalDeadDay == -1) setGridCellsColors(List.of(watchingAnimal.getPosition()), "f5b600");
        if (isShowPreferredFields) colorPreferredFields();
        if (!isShowAnimalsWithMostPopularGene) {
            isShowAnimalsWithMostPopularGene = true;
            colorAnimalWithMostPopularGene();
        } else isShowAnimalsWithMostPopularGene = false;
        fillMap();
    }

    public void endGame() throws InterruptedException {
        simulation.killGame(simulationThread);
        Stage stage = (Stage) endGameButton.getScene().getWindow();
        stage.close();
    }

    private void watchAnimalStats(Vector2d position) {
        boolean isRunning = simulation.isRunning();

        if (!isRunning) {
            watchingAnimal = (Animal) worldMap.dominantAnimalAtPosition(position);
            watchingAnimalDeadDay = -1; // oznacza że jeszcze żyje

            clearGrid();
            drawColumns();
            drawRows();
            setGridCellsColors();
            if (isShowPreferredFields) colorPreferredFields();
            if (isShowAnimalsWithMostPopularGene) colorAnimalWithMostPopularGene();
            setGridCellsColors(List.of(position), "f5b600");
            fillMap();
            updateWatchingAnimalStats();
        }
    }

    private void updateWatchingAnimalStats() {
        if (watchingAnimal == null) {
            watchingAnimalImage.setImage(new Image("img/question-mark.png"));
            watchingAnimalPositionText.setText("-");
            watchingAnimalDirection.setText("-");
            watchingAnimalGenomeText.setText("-");
            watchingAnimalActualGenText.setText("-");
            watchingAnimalEnergyText.setText("-");
            watchingAnimalEatenGrassCounterText.setText("-");
            watchingAnimalChildrenCounterText.setText("-");
            watchingAnimalDescendantCounterText.setText("-");
            watchingAnimalLifetimeText.setText("-");
            watchingAnimalDeadDayText.setText("-");
        } else {
            watchingAnimalImage.setImage(new Image("img/" + watchingAnimal.getImageName()));
            watchingAnimalPositionText.setText(watchingAnimal.getPosition().toString());
            watchingAnimalDirection.setText(watchingAnimal.getDirection().toString());
            watchingAnimalGenomeText.setText(watchingAnimal.getAnimalFullGenome().stream().map(String::valueOf).collect(Collectors.joining(" ")));
            watchingAnimalActualGenText.setText(Integer.toString(watchingAnimal.getActualGenome()));
            watchingAnimalEnergyText.setText(Integer.toString(watchingAnimal.getEnergy()));
            watchingAnimalEatenGrassCounterText.setText(Integer.toString(watchingAnimal.getEatingCounter()));
            watchingAnimalChildrenCounterText.setText(Integer.toString(watchingAnimal.getChildrenCount()));
            watchingAnimalDescendantCounterText.setText(Integer.toString(watchingAnimal.getDescendants()));
            watchingAnimalLifetimeText.setText(Integer.toString(watchingAnimal.getDaysLived()));
            if (watchingAnimal.getEnergy() > 0) {
                watchingAnimalDeadDayText.setText("-");
            } else if (watchingAnimal.getEnergy() <= 0 && watchingAnimalDeadDay == -1) {
                watchingAnimalDeadDay = simulation.getDay();
                watchingAnimalDeadDayText.setText(Long.toString(watchingAnimalDeadDay));
            }
        }
    }

    private void colorPreferredFields() {
        Boundary equatorBoundary = worldMap.getEquatorBoundary();
        Vector2d equatorLowerLeft = equatorBoundary.lowerLeft();
        Vector2d equatorUpperRight = equatorBoundary.upperRight();
        setGridCellsColors(equatorLowerLeft, equatorUpperRight);
    }

    private void colorAnimalWithMostPopularGene() {
        List<Vector2d> positionsWithPostPopularGene = simulation.getPositionsWithGenes(Statistics.getMostPopularGenome(animalsList));
        setGridCellsColors(positionsWithPostPopularGene, "8600ee");
    }

    private void closeThreadAndWindow(WindowEvent event) throws InterruptedException {
        endGame();
        event.consume();
    }
}