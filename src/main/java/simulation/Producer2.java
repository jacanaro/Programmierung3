package simulation;

import domain_logic.ManufacturerImpl;

public class Producer2 extends Thread {
    private VendingMachineSimulation2 automat;

    public Producer2(VendingMachineSimulation2 automat) {
        this.automat = automat;
    }

    public void run() {
        while (true) {
            RandomProductGenerator r = new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                    new ManufacturerImpl("Gooseberryland"));
            System.out.println(this.getName() + " will kuchen hinzufuegen");
            this.automat.addVerkaufsobjekt(r.getRandomKuchen());
        }
    }
}
