package projekt.presenter;

import com.google.gson.Gson;

import java.io.*;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projekt.Simulation;
import projekt.SimulationEngine;
import projekt.model.maps.AbstractMap;
import projekt.model.maps.EarthMap;
import projekt.model.maps.PoleMap;
import projekt.model.util.ConfigData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ConfigPresenter {
    @FXML
    private TextField mapHeightInput, mapWidthInput, startGrassAmountInput, eatenGrassEnergyInput, grassGrownAmountInput, startAnimalsAmountInput,
            startAnimalEnergyInput, minEnergyToFullAnimalInput, sexEnergyCostInput, minMutationAmountInput, maxMutationAmountInput,
            animalGenomeLengthInput, gameplaySpeedInput, configNameInput;

    @FXML
    private CheckBox coldWarGameplayCheckbox, geneticChangeGameplayCheckbox;

    @FXML
    private Label mapHeightError, mapWidthError, startGrassAmountError, eatenGrassEnergyError, grassGrownAmountError,
            startAnimalsAmountError, startAnimalEnergyError, minEnergyToFullAnimalError, sexEnergyCostError,
            minMutationAmountError, maxMutationAmountError, animalGenomeLengthError, gameplaySpeedError, configNameError;

    @FXML
    private Button startNewGameButton;

    @FXML
    private VBox savedConfigsVBox;

    private final List<Simulation> simulations = new ArrayList<>();
    private SimulationEngine simulationEngine;

    private int mapHeight;
    private int mapWidth;
    private int startGrassAmount;
    private int eatenGrassEnergy;
    private int grassGrownAmount;
    private int startAnimalsAmount;
    private int startAnimalEnergy;
    private int minEnergyToFullAnimal;
    private int sexEnergyCost;
    private int minMutationAmount;
    private int maxMutationAmount;
    private int animalGenomeLength;
    private int gameplaySpeed;
    private boolean isColdWarGameplay;
    private boolean isSlightCorrection;

    private AbstractMap map;
    private GameMapPresenter presenter;


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
        getSavedConfigs();
    }

    @FXML
    private void startGame() {
        getInputValues();
        if (checkInputCorrectness()) {

            if (isColdWarGameplay) {
                map = new PoleMap(mapWidth - 1, mapHeight - 1, startGrassAmount, grassGrownAmount, eatenGrassEnergy);
            } else {
                map = new EarthMap(mapWidth - 1, mapHeight - 1, startGrassAmount, grassGrownAmount, eatenGrassEnergy);
            }

            Thread simulationThread = new Thread(() -> {
                try {
                    Platform.runLater(() -> {
                        try {
                            startSimulationWindow();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            simulationThread.setDaemon(true);
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

    private void getInputValues() {
        mapHeight = Integer.parseInt(mapHeightInput.getText());
        mapWidth = Integer.parseInt(mapWidthInput.getText());
        startGrassAmount = Integer.parseInt(startGrassAmountInput.getText());
        eatenGrassEnergy = Integer.parseInt(eatenGrassEnergyInput.getText());
        grassGrownAmount = Integer.parseInt(grassGrownAmountInput.getText());
        startAnimalsAmount = Integer.parseInt(startAnimalsAmountInput.getText());
        startAnimalEnergy = Integer.parseInt(startAnimalEnergyInput.getText());
        minEnergyToFullAnimal = Integer.parseInt(minEnergyToFullAnimalInput.getText());
        sexEnergyCost = Integer.parseInt(sexEnergyCostInput.getText());
        minMutationAmount = Integer.parseInt(minMutationAmountInput.getText());
        maxMutationAmount = Integer.parseInt(maxMutationAmountInput.getText());
        animalGenomeLength = Integer.parseInt(animalGenomeLengthInput.getText());
        gameplaySpeed = Integer.parseInt(gameplaySpeedInput.getText());
        isColdWarGameplay = coldWarGameplayCheckbox.isSelected();
        isSlightCorrection = geneticChangeGameplayCheckbox.isSelected();
    }

    private void startSimulationWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/gameMap.fxml"));
        AnchorPane root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Darwin World");
        stage.getIcons().add(new Image("img/logo.png"));
        GameMapPresenter presenter = loader.getController();

        Simulation simulation = new Simulation.Builder()
                .setMap(map)
                .setEatenGrassEnergy(eatenGrassEnergy)
                .setGrassGrownAmount(grassGrownAmount)
                .setStartAnimalsAmount(startAnimalsAmount)
                .setStartAnimalEnergy(startAnimalEnergy)
                .setMinEnergyToFullAnimal(minEnergyToFullAnimal)
                .setSexEnergyCost(sexEnergyCost)
                .setMinMutationAmount(minMutationAmount)
                .setMaxMutationAmount(maxMutationAmount)
                .setAnimalGenomeLength(animalGenomeLength)
                .setGameplaySpeed(gameplaySpeed)
                .setSlightCorrection(isSlightCorrection)
                .build();

        presenter.startSimulation(simulation, map);
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
            saveToJson("savedConfigs/" + configNameInput.getText() + ".json");
        } else {
            System.out.println("Popraw błędy");
        }
    }

    private void getSavedConfigs() {
        savedConfigsVBox.getChildren().clear();
        String path = "oolab/src/main/resources/savedConfigs";
        File directory = new File(path);
        System.out.println(directory);

        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));

                Button button = new Button(fileNameWithoutExtension);
                button.setMaxWidth(242);
                button.setOnAction(e -> uploadSavedConfig(fileName));
                savedConfigsVBox.getChildren().add(button);
            }
        }
    }


    private void saveToJson(String filePath) {
        getInputValues();
        ConfigData configData = new ConfigData();
        configData.setMapHeight(mapHeight);
        configData.setMapWidth(mapWidth);
        configData.setStartGrassAmount(startGrassAmount);
        configData.setEatenGrassEnergy(eatenGrassEnergy);
        configData.setGrassGrownAmount(grassGrownAmount);
        configData.setStartAnimalsAmount(startAnimalsAmount);
        configData.setStartAnimalEnergy(startAnimalEnergy);
        configData.setMinEnergyToFullAnimal(minEnergyToFullAnimal);
        configData.setSexEnergyCost(sexEnergyCost);
        configData.setMinMutationAmount(minMutationAmount);
        configData.setMaxMutationAmount(maxMutationAmount);
        configData.setAnimalGenomeLength(animalGenomeLength);
        configData.setGameplaySpeed(gameplaySpeed);
        configData.setColdWarGameplay(isColdWarGameplay);
        configData.setSlightCorrection(isSlightCorrection);
        Gson gson = new Gson();

        String finalFilePath = "src/main/resources/" + filePath;
        try {
            // Create the directory if it doesn't exist
            File file = new File(finalFilePath);
            file.getParentFile().mkdirs();

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(configData, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        getSavedConfigs();
    }

    private void uploadSavedConfig(String fileName) {
        Gson gson = new Gson();
        ConfigData configData;
        try (FileReader reader = new FileReader("oolab/src/main/resources/savedConfigs/" + fileName)) {
            configData = gson.fromJson(reader, ConfigData.class);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        mapHeightInput.setText(Integer.toString(configData.getMapHeight()));
        mapWidthInput.setText(Integer.toString(configData.getMapWidth()));
        startGrassAmountInput.setText(Integer.toString(configData.getStartGrassAmount()));
        eatenGrassEnergyInput.setText(Integer.toString(configData.getEatenGrassEnergy()));
        grassGrownAmountInput.setText(Integer.toString(configData.getGrassGrownAmount()));
        startAnimalsAmountInput.setText(Integer.toString(configData.getStartAnimalsAmount()));
        startAnimalEnergyInput.setText(Integer.toString(configData.getStartAnimalEnergy()));
        minEnergyToFullAnimalInput.setText(Integer.toString(configData.getMinEnergyToFullAnimal()));
        sexEnergyCostInput.setText(Integer.toString(configData.getSexEnergyCost()));
        minMutationAmountInput.setText(Integer.toString(configData.getMinMutationAmount()));
        maxMutationAmountInput.setText(Integer.toString(configData.getMaxMutationAmount()));
        animalGenomeLengthInput.setText(Integer.toString(configData.getAnimalGenomeLength()));
        gameplaySpeedInput.setText(Integer.toString(configData.getGameplaySpeed()));
        coldWarGameplayCheckbox.setSelected(configData.isColdWarGameplay());
        geneticChangeGameplayCheckbox.setSelected(configData.isSlightCorrection());
    }
}
