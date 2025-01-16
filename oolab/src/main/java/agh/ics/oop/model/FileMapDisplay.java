package agh.ics.oop.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class FileMapDisplay implements MapChangeListener {
    private final WorldMap map;
    private final UUID mapId;

    public FileMapDisplay(WorldMap map) {
        this.map = map;
        this.mapId = map.getId();
    }


    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        String mapLogFile = "map_%s.log".formatted(mapId);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mapLogFile, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
