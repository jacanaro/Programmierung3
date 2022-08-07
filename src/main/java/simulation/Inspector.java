package simulation;

public class Inspector extends Thread {
    private VendingMachineSimulation2 automat;

    public Inspector(VendingMachineSimulation2 automat) {
        this.automat = automat;
    }

    public void run() {
        do {
            System.out.println(this.getName() + " versucht Inspektion auszuloesen");
            automat.doRandomInspection();
        } while (true);
    }
}