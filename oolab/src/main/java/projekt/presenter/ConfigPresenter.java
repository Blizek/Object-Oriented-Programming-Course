package projekt.presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projekt.Simulation;
import projekt.SimulationEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ConfigPresenter {
    @FXML
    TextField mapHeight, mapWidth, startGrassAmount, eatenGrassEnergy, grassGrownAmount, startAnimalsAmount, startAnimalEnergy,
            minEnergyToFullAnimal, sexEnergyCost, minMutationAmount, maxMutationAmount, animalGenomeLength, gameplaySpeed;

    @FXML
    CheckBox coldWarGameplay, geneticChangeGameplay;

    @FXML
    Label mapHeightError, mapWidthError, startGrassAmountError, eatenGrassEnergyError, grassGrownAmountError,
            startAnimalsAmountError, startAnimalEnergyError, minEnergyToFullAnimalError, sexEnergyCostError,
            minMutationAmountError, maxMutationAmountError, animalGenomeLengthError, gameplaySpeedError;

    @FXML
    Button historyGameLoad, startNewGame;

    private final List<Simulation> simulations = new ArrayList<>();
    private SimulationEngine simulationEngine;

    public void initialize() {
        setNumericOnly(mapHeight);
        setNumericOnly(mapWidth);
        setNumericOnly(startGrassAmount);
        setNumericOnly(eatenGrassEnergy);
        setNumericOnly(grassGrownAmount);
        setNumericOnly(startAnimalsAmount);
        setNumericOnly(startAnimalEnergy);
        setNumericOnly(minEnergyToFullAnimal);
        setNumericOnly(sexEnergyCost);
        setNumericOnly(minMutationAmount);
        setNumericOnly(maxMutationAmount);
        setNumericOnly(animalGenomeLength);
        setNumericOnly(gameplaySpeed);
    }

    @FXML
    private void startGame() {
        if (checkInputCorrectness()) {
            if (coldWarGameplay.isSelected()) {
                System.out.println("Mapa z biegunami");
            } else {
                System.out.println("Zwykła mapa");
            }

            if (geneticChangeGameplay.isSelected()) {
                System.out.println("Dodaj nową mutację");
            } else {
                System.out.println("Zwykła mutacja");
            }
            System.out.println("Gotowa rozgrywka");
            Thread simulationThread = new Thread(() -> {
                try {
                    javafx.application.Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getClassLoader().getResource("fxml/gameMap.fxml"));
                            AnchorPane root = loader.load();

                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Symulacja");
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    // Uruchamianie logiki symulacji
                    //newSimulation.run();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            simulationThread.start();
        } else {
            System.out.println("Niegotowa rozgrywka");
        }
    }

    private void setNumericOnly(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            if (!character.matches("[0-9]")) {
                event.consume();
            }
        });
    }

    private boolean checkInputCorrectness() {
        clearAllErrorTexts();
        boolean isDataCorrect = true;

        if (mapHeight.getText().isEmpty()) {
            mapHeightError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(mapHeight.getText()) <= 0) {
            mapHeightError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (mapWidth.getText().isEmpty()) {
            mapWidthError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(mapWidth.getText()) <= 0) {
            mapWidthError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (startGrassAmount.getText().isEmpty()) {
            startGrassAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (eatenGrassEnergy.getText().isEmpty()) {
            eatenGrassEnergyError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (grassGrownAmount.getText().isEmpty()) {
            grassGrownAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (startAnimalsAmount.getText().isEmpty()) {
            startAnimalsAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(startAnimalsAmount.getText()) <= 0) {
            startAnimalsAmountError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (startAnimalEnergy.getText().isEmpty()) {
            startAnimalEnergyError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(startAnimalEnergy.getText()) <= 0) {
            startAnimalEnergyError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (minEnergyToFullAnimal.getText().isEmpty()) {
            minEnergyToFullAnimalError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(minEnergyToFullAnimal.getText()) <= 0) {
            minEnergyToFullAnimalError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (sexEnergyCost.getText().isEmpty()) {
            sexEnergyCostError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(sexEnergyCost.getText()) <= 0) {
            sexEnergyCostError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (minMutationAmount.getText().isEmpty()) {
            minMutationAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (maxMutationAmount.getText().isEmpty()) {
            maxMutationAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (animalGenomeLength.getText().isEmpty()) {
            animalGenomeLengthError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (gameplaySpeed.getText().isEmpty()) {
            gameplaySpeedError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        return isDataCorrect;
    }

    private void clearAllErrorTexts() {
        mapHeightError.setText("");
        mapWidthError.setText("");
        startGrassAmountError.setText("");
        eatenGrassEnergyError.setText("");
        grassGrownAmountError.setText("");
        startAnimalsAmountError.setText("");
        startAnimalEnergyError.setText("");
        minEnergyToFullAnimalError.setText("");
        sexEnergyCostError.setText("");
        minMutationAmountError.setText("");
        maxMutationAmountError.setText("");
        animalGenomeLengthError.setText("");
        gameplaySpeedError.setText("");
    }
}
