package agh.ics.oop;
import agh.ics.oop.model.*;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationApp extends Application{
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        try {
            Vector2d position1 = new Vector2d(2, 2);
            Vector2d position2 = new Vector2d(1, 2);

            GrassField grassField = new GrassField(10);

            grassField.addObserver(presenter);

            List<MoveDirection> animalsDirections = OptionsParser.parseStringToMoveDirections(getParameters().getRaw().toArray(new String[0]));

            List<Vector2d> animalsStartPositions = new ArrayList<>();
            animalsStartPositions.add(position1);
            animalsStartPositions.add(position2);

            Simulation simulation = new Simulation(animalsStartPositions, animalsDirections, grassField);
            List<Simulation> simulations = new ArrayList<>();
            simulations.add(simulation);
            SimulationEngine engine = new SimulationEngine(simulations);
            engine.runAsyncInThreadPool();
            engine.awaitSimulationEnd();
            System.out.println("System zakończył działanie");
        } catch (IllegalArgumentException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        configureStage(primaryStage, viewRoot);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
