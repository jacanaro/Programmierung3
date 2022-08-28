import domain_logic.ManufacturerImpl;
import observer_pattern.VendingMachineSimulationObserver;
import simulation.*;

public class Simulation2Main {
    public static void main(String[] args) {
        try{
        VendingMachineSimulation2 automat = new VendingMachineSimulation2(Integer.parseInt(args[0]));
        new VendingMachineSimulationObserver(automat);
        automat.addManufacturer(new ManufacturerImpl("Blueberryland"));
        automat.addManufacturer(new ManufacturerImpl("Gooseberryland"));
        new Consumer2(automat).start();
        new Producer2(automat).start();
        new Inspector(automat).start();
        }catch (Exception e){
            System.out.println(e+" Kommandozeilenparameter: Automat-Capacity angeben!");
        }
    }
}