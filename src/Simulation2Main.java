import domainLogic.automat.HerstellerImplementierung;
import observerPattern.AutomatSimulationObserver;
import simulation.*;

public class Simulation2Main {
    public static void main(String[] args) {
        try{
        AutomatSimulation2 automat = new AutomatSimulation2(Integer.parseInt(args[0]));
        new AutomatSimulationObserver(automat);
        automat.addHersteller(new HerstellerImplementierung("Blueberryland"));
        automat.addHersteller(new HerstellerImplementierung("Gooseberryland"));
        new Consumer2(automat).start();
        new Producer2(automat).start();
        new Inspector(automat).start();
        }catch (Exception e){
            System.out.println(e+" Kommandozeilenparameter: Automat-Capacity angeben!");
        }
    }
}