package projekt;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulationsList;
    private final List<Thread> threadList = new ArrayList<>();

    public SimulationEngine(List<Simulation> simulationsList) {
        this.simulationsList = simulationsList;
    }

    public void runAsync() {
        for (Simulation simulation : simulationsList) {
            Thread thread = new Thread(simulation);
            threadList.add(thread);
            thread.run();
        }
    }
}
