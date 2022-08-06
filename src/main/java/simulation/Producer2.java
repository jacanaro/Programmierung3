package simulation;

import domainLogic.automat.HerstellerImplementierung;

public class Producer2 extends Thread {
    private AutomatSimulation2 automat;

    public Producer2(AutomatSimulation2 automat) {
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
