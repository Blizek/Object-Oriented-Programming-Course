package projekt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulationsList;
    private final List<Thread> threadList = new ArrayList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public SimulationEngine(List<Simulation> simulationsList) {
        this.simulationsList = simulationsList;
    }

    public void runAsync() {
        for (Simulation simulation : simulationsList) {
            Thread thread = new Thread(simulation);
            threadList.add(thread);
            thread.start();
        }
    }

    public void awaitSimulationEnd() throws InterruptedException {
        for (Thread thread : threadList) {
            thread.join();
        }
        executorService.shutdown();
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }

    public void runAsyncInThreadPool() {
        for (Simulation simulation : simulationsList) {
            executorService.submit(simulation);
        }
    }
}
