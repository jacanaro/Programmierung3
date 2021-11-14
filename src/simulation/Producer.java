package simulation;

import domainLogic.automat.HerstellerImplementierung;

public class Producer extends Thread {
    private AutomatSimulation1 automat;

    public Producer(AutomatSimulation1 automat) {
        this.automat = automat;
    }

    public void run() {
        while (true) {
            synchronized (this.automat) {
                if (this.automat.getVerkaufsobjekte().size() != this.automat.getCAPACITY()) {
                    RandomValues r = new RandomValues(new HerstellerImplementierung("Blueberryland"),
                            new HerstellerImplementierung("Gooseberryland"));
                    System.out.println(this.getName() + " will kuchen hinzufuegen");
                    this.automat.addVerkaufsobjekt(r.getRandomKuchen());
                }
            }
        }
    }
}
