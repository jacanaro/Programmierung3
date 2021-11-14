import domainLogic.automat.HerstellerImplementierung;
import observerPattern.AutomatSimulationObserver;
import simulation.*;

public class Simulation3Main {
    public static void main(String[] args) {
        try{
        AutomatSimulation3 automat = new AutomatSimulation3(Integer.parseInt(args[0]));
        new AutomatSimulationObserver(automat);
        automat.addHersteller(new HerstellerImplementierung("Blueberryland"));
        automat.addHersteller(new HerstellerImplementierung("Gooseberryland"));
        new Consumer3(automat).start();
        new Producer3(automat).start();
        new Consumer3(automat).start();
        new Producer3(automat).start();
        new Inspector(automat).start();
        }catch (Exception e){
            System.out.println(e+" Kommandozeilenparameter: Automat-Capacity angeben!");
        }
    }
}
