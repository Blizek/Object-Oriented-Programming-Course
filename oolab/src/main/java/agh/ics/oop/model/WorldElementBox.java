package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class WorldElementBox extends VBox {
    private static final Map<String, Image> loadedImages = new HashMap<>();

    public WorldElementBox(WorldElement element) {
        String imagePath = "/img/" + element.getImageName();

        Image image = loadedImages.computeIfAbsent(imagePath, path -> {
            try (InputStream imageStream = getClass().getResourceAsStream(path)) {
                if (imageStream == null) {
                    throw new IllegalArgumentException("Image not found from this path: " + path);
                }
                return new Image(imageStream);
            } catch (IOException e) {
                throw new RuntimeException("Error loading image: " + path, e);
            }
        });

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        Label positionLabel = new Label(element.getPosition().toString());

        this.getChildren().addAll(imageView, positionLabel);
        this.setAlignment(Pos.CENTER);

    }
}
