package simulation;

public class Consumer3 extends Thread{
    private AutomatSimulation3 automat;
    public Consumer3(AutomatSimulation3 automat) {
        this.automat=automat;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this.getName()+ " versucht Kuchen zu loeschen");
            this.automat.deleteVerkaufsobjekteWithOldestDateXTimes();
        }
    }
}
