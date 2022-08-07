import domain_logic.ManufacturerImpl;
import observer_pattern.AutomatSimulationObserver;
import simulation.*;

public class Simulation3Main {
    public static void main(String[] args) {
        try{
        VendingMachineSimulation3 automat = new VendingMachineSimulation3(Integer.parseInt(args[0]));
        new AutomatSimulationObserver(automat);
        automat.addHersteller(new ManufacturerImpl("Blueberryland"));
        automat.addHersteller(new ManufacturerImpl("Gooseberryland"));
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
