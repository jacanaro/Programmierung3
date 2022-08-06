package simulation;

import domainLogic.automat.HerstellerImplementierung;

public class Producer3 extends Thread {
    private AutomatSimulation3 automat;

    public Producer3(AutomatSimulation3 automat) {
        this.automat = automat;
    }

    public void run() {
        while (true) {
            RandomValues r = new RandomValues(new HerstellerImplementierung("Blueberryland"),
                    new HerstellerImplementierung("Gooseberryland"));
            System.out.println(this.getName() + " will kuchen hinzufuegen");
            this.automat.addVerkaufsobjekt(r.getRandomKuchen());
        }
    }
}
