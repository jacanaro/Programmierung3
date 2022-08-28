package observer_pattern;

import simulation.VendingMachineSimulation1;

public class VendingMachineSimulationObserver extends VendingMachineAllergensObserver {
    VendingMachineSimulation1 vendingMachineSimulation1;

    public VendingMachineSimulationObserver(VendingMachineSimulation1 vendingMachineSimulation1) {
        super(vendingMachineSimulation1);
        this.vendingMachineSimulation1 = vendingMachineSimulation1;
    }

    @Override
    public void update() {
        System.out.println("Aktuelle Kuchenliste: \n"+ vendingMachineSimulation1.getProducts().toString());
    }
}
