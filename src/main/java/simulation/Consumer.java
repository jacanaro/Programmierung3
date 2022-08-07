package simulation;

public class Consumer extends Thread {
    private VendingMachineSimulation1 automat;

    public Consumer(VendingMachineSimulation1 automat) {
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
