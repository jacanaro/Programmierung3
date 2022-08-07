package observer_pattern;

import simulation.VendingMachineSimulation1;

public class AutomatSimulationObserver extends VendingMachineAllergensObserver {
    VendingMachineSimulation1 automat;

    public AutomatSimulationObserver(VendingMachineSimulation1 automat) {
        super(automat);
        this.automat=automat;
    }

    @Override
    public void update() {
        System.out.println("Aktuelle Kuchenliste: \n"+automat.getVerkaufsobjekte().toString());
    }
}
