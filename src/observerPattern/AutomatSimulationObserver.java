package observerPattern;

import simulation.AutomatSimulation1;

public class AutomatSimulationObserver extends AutomatAllergeneObserver {
    AutomatSimulation1 automat;
    public AutomatSimulationObserver(AutomatSimulation1 automat) {
        super(automat);
        this.automat=automat;
    }

    @Override
    public void update() {
        System.out.println("Aktuelle Kuchenliste: \n"+automat.getVerkaufsobjekte().toString());
    }
}
