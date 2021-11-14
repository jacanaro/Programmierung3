package simulation;

public class Consumer extends Thread {
    private AutomatSimulation1 automat;

    public Consumer(AutomatSimulation1 automat) {
        this.automat = automat;
    }

    public void run() {
        while (true) {
            synchronized (this.automat) {
                if (this.automat.getVerkaufsobjekte().size() != 0) {
                    System.out.println(this.getName() + " versucht Kuchen zu loeschen");
                    this.automat.deleteVerkaufsobjektRandom();
                }
            }
        }
    }
}
