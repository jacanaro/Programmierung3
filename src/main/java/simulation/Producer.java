package simulation;

import domain_logic.ManufacturerImpl;

public class Producer extends Thread {
    private VendingMachineSimulation1 automat;

    public Producer(VendingMachineSimulation1 automat) {
        this.automat = automat;
    }

    public void run() {
        while (true) {
            synchronized (this.automat) {
                if (this.automat.getProducts().size() != this.automat.getCAPACITY()) {
                    RandomProductGenerator r = new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                            new ManufacturerImpl("Gooseberryland"));
                    System.out.println(this.getName() + " will kuchen hinzufuegen");
                    this.automat.addProduct(r.getRandomKuchen());
                }
            }
        }
    }
}
