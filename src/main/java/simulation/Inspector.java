package simulation;

public class Inspector extends Thread {
    private AutomatSimulation2 automat;

    public Inspector(AutomatSimulation2 automat) {
        this.automat = automat;
    }

    public void run() {
        do {
            System.out.println(this.getName() + " versucht Inspektion auszuloesen");
            automat.doRandomInspection();
        } while (true);
    }
}