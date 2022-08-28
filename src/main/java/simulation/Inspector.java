package simulation;

public class Inspector extends Thread {
    private VendingMachineSimulation2 vendingMachineSimulation2;

    public Inspector(VendingMachineSimulation2 vendingMachineSimulation2) {
        this.vendingMachineSimulation2 = vendingMachineSimulation2;
    }

    public void run() {
        do {
            System.out.println(this.getName() + " versucht Inspektion auszuloesen");
            vendingMachineSimulation2.doRandomInspection();
        } while (true);
    }
}