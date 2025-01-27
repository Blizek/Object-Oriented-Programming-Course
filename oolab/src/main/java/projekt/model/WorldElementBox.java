package projekt.model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class WorldElementBox extends VBox {
    private static final Map<String, Image> loadedImages = new HashMap<>();

    public WorldElementBox(WorldElement element, double boxSize) {
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
        imageView.setFitHeight(boxSize);
        imageView.setFitWidth(boxSize);

        this.getChildren().addAll(imageView);
        this.setAlignment(Pos.CENTER);
    }
}