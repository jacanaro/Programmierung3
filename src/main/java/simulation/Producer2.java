package simulation;

import domain_logic.ManufacturerImpl;

public class Producer2 extends Thread {
    private VendingMachineSimulation2 vendingMachineSimulation2;

    public Producer2(VendingMachineSimulation2 vendingMachineSimulation2) {
        this.vendingMachineSimulation2 = vendingMachineSimulation2;
    }

    public void run() {
        while (true) {
            RandomProductGenerator r = new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                    new ManufacturerImpl("Gooseberryland"));
            System.out.println(this.getName() + " will kuchen hinzufuegen");
            this.vendingMachineSimulation2.addProduct(r.getRandomProduct());
        }
    }
}
