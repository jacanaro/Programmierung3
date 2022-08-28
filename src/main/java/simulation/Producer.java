package simulation;

import domain_logic.ManufacturerImpl;

public class Producer extends Thread {
    private VendingMachineSimulation1 vendingMachineSimulation1;

    public Producer(VendingMachineSimulation1 vendingMachineSimulation1) {
        this.vendingMachineSimulation1 = vendingMachineSimulation1;
    }

    public void run() {
        while (true) {
            synchronized (this.vendingMachineSimulation1) {
                if (this.vendingMachineSimulation1.getProducts().size() != this.vendingMachineSimulation1.getCAPACITY()) {
                    RandomProductGenerator randomProductGenerator = new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                            new ManufacturerImpl("Gooseberryland"));
                    System.out.println(this.getName() + " will kuchen hinzufuegen");
                    this.vendingMachineSimulation1.addProduct(randomProductGenerator.getRandomProduct());
                }
            }
        }
    }
}
