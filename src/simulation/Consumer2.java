package simulation;


public class Consumer2 extends Thread{
    private AutomatSimulation2 automat;
    public Consumer2(AutomatSimulation2 automat) {
       this.automat=automat;
    }

    @Override
    public void run() {
            while (true) {
                System.out.println(this.getName()+ " versucht Kuchen zu loeschen");
                this.automat.deleteVerkaufsobjektWithOldestDate();
            }
    }
}
