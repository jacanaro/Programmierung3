package simulation;

import domain_logic.ManufacturerImpl;

public class Producer3 extends Thread {
    private VendingMachineSimulation3 automat;

    public Producer3(VendingMachineSimulation3 automat) {
        this.automat = automat;
    }

    public void run() {
        while (true) {
            RandomProductGenerator r = new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                    new ManufacturerImpl("Gooseberryland"));
            System.out.println(this.getName() + " will kuchen hinzufuegen");
            this.automat.addProduct(r.getRandomKuchen());
        }
    }
}
