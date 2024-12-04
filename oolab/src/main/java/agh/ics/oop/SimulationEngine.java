package agh.ics.oop;

import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulationsList;

    public SimulationEngine(List<Simulation> simulationsList) {
        this.simulationsList = simulationsList;
    }

    public void runSync() {
        for (Simulation simulation : simulationsList) {
            simulation.run();
        }
    }
}
