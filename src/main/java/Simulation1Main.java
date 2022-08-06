import domainLogic.automat.HerstellerImplementierung;
import observerPattern.AutomatSimulationObserver;
import simulation.AutomatSimulation1;
import simulation.Consumer;
import simulation.Producer;

public class Simulation1Main {
    public static void main(String[] args) {
        try{
        AutomatSimulation1 automat = new AutomatSimulation1(Integer.parseInt(args[0]));
        new AutomatSimulationObserver(automat);
        automat.addHersteller(new HerstellerImplementierung("Blueberryland"));
        automat.addHersteller(new HerstellerImplementierung("Gooseberryland"));
        new Consumer(automat).start();
        new Producer(automat).start();
        }catch (Exception e){
            System.out.println(e+" Kommandozeilenparameter: Automat-Capacity angeben!");
        }
    }
}
