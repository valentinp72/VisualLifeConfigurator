package fr.ttvp.visuallifeconfigurator.model;

public class SimulatorThread extends Thread {

    private Simulator simulator;
    private boolean playing;

    public SimulatorThread(Simulator simulator) {
        this.simulator = simulator;
        this.playing = false;
    }

    @Override
    public void run() {
        // TODO: à refactorer, en implémantant les méthodes play et pause de sorte qu'elles
        // endorment et réveillent le thread
        while(true) {
            try {
                int delay = 200;
                if(playing) {
                    simulator.step();
                }
                else {
                    delay = 500;
                }
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void play() {
        this.playing = true;
    }

    public void pause() {
        this.playing = false;
    }
}
