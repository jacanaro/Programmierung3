package simulation;

import domain_logic.ManufacturerImpl;

public class Producer3 extends Thread {
    private VendingMachineSimulation3 vendingMachineSimulation3;

    public Producer3(VendingMachineSimulation3 vendingMachineSimulation3) {
        this.vendingMachineSimulation3 = vendingMachineSimulation3;
    }

    public void run() {
        while (true) {
            RandomProductGenerator r = new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                    new ManufacturerImpl("Gooseberryland"));
            System.out.println(this.getName() + " will kuchen hinzufuegen");
            this.vendingMachineSimulation3.addProduct(r.getRandomProduct());
        }
    }
}
