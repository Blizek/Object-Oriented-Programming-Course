package projekt.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    TextField mapHeightInput, mapWidthInput, startGrassAmountInput, eatenGrassEnergyInput, grassGrownAmountInput, startAnimalsAmountInput,
            startAnimalEnergyInput, minEnergyToFullAnimalInput, sexEnergyCostInput, minMutationAmountInput, maxMutationAmountInput,
            animalGenomeLengthInput, gameplaySpeedInput, configNameInput;

    @FXML
    CheckBox coldWarGameplayCheckbox, geneticChangeGameplayCheckbox;

    @FXML
    Label mapHeightError, mapWidthError, startGrassAmountError, eatenGrassEnergyError, grassGrownAmountError,
            startAnimalsAmountError, startAnimalEnergyError, minEnergyToFullAnimalError, sexEnergyCostError,
            minMutationAmountError, maxMutationAmountError, animalGenomeLengthError, gameplaySpeedError, configNameError;

    @FXML
    Button historyGameLoadButton, startNewGameButton;

    private final List<Simulation> simulations = new ArrayList<>();
    private SimulationEngine simulationEngine;

    public void initialize() {
        setNumericOnly(mapHeightInput);
        setNumericOnly(mapWidthInput);
        setNumericOnly(startGrassAmountInput);
        setNumericOnly(eatenGrassEnergyInput);
        setNumericOnly(grassGrownAmountInput);
        setNumericOnly(startAnimalsAmountInput);
        setNumericOnly(startAnimalEnergyInput);
        setNumericOnly(minEnergyToFullAnimalInput);
        setNumericOnly(sexEnergyCostInput);
        setNumericOnly(minMutationAmountInput);
        setNumericOnly(maxMutationAmountInput);
        setNumericOnly(animalGenomeLengthInput);
        setNumericOnly(gameplaySpeedInput);
    }

    @FXML
    private void startGame() {
        if (checkInputCorrectness()) {
            if (coldWarGameplayCheckbox.isSelected()) {
                System.out.println("Mapa z biegunami");
            } else {
                System.out.println("Zwykła mapa");
            }

            if (geneticChangeGameplayCheckbox.isSelected()) {
                System.out.println("Dodaj nową mutację");
            } else {
                System.out.println("Zwykła mutacja");
            }
            System.out.println("Gotowa rozgrywka");
            Thread simulationThread = new Thread(() -> {
                try {
                    Platform.runLater(() -> {
                        try {
                            startSimulationWindow();
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

        if (mapHeightInput.getText().isEmpty()) {
            mapHeightError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(mapHeightInput.getText()) <= 0) {
            mapHeightError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (mapWidthInput.getText().isEmpty()) {
            mapWidthError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(mapWidthInput.getText()) <= 0) {
            mapWidthError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (startGrassAmountInput.getText().isEmpty()) {
            startGrassAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (eatenGrassEnergyInput.getText().isEmpty()) {
            eatenGrassEnergyError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (grassGrownAmountInput.getText().isEmpty()) {
            grassGrownAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (startAnimalsAmountInput.getText().isEmpty()) {
            startAnimalsAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(startAnimalsAmountInput.getText()) <= 0) {
            startAnimalsAmountError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (startAnimalEnergyInput.getText().isEmpty()) {
            startAnimalEnergyError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(startAnimalEnergyInput.getText()) <= 0) {
            startAnimalEnergyError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (minEnergyToFullAnimalInput.getText().isEmpty()) {
            minEnergyToFullAnimalError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(minEnergyToFullAnimalInput.getText()) <= 0) {
            minEnergyToFullAnimalError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (sexEnergyCostInput.getText().isEmpty()) {
            sexEnergyCostError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        } else if (Integer.parseInt(sexEnergyCostInput.getText()) <= 0) {
            sexEnergyCostError.setText("To pole musi mieć wartość dodanią");
            isDataCorrect = false;
        }

        if (minMutationAmountInput.getText().isEmpty()) {
            minMutationAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (maxMutationAmountInput.getText().isEmpty()) {
            maxMutationAmountError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (animalGenomeLengthInput.getText().isEmpty()) {
            animalGenomeLengthError.setText("To pole musi być wypełnione");
            isDataCorrect = false;
        }

        if (gameplaySpeedInput.getText().isEmpty()) {
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

    private void startSimulationWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/gameMap.fxml"));
        AnchorPane root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Darwin World");
        stage.getIcons().add(new Image("img/logo.png"));
        stage.show();
    }

    @FXML
    public void saveConfig() {
        configNameError.setText("");
        boolean isConfigCorrect = checkInputCorrectness();

        if (configNameInput.getText().isEmpty()) {
            configNameError.setText("Aby zapisać konfigurację to pole musi być wypełnione");
            isConfigCorrect = false;
        }

        if (isConfigCorrect) {
            System.out.println("Zapisano konfigurację");
        } else {
            System.out.println("Popraw błędy");
        }
    }
}
