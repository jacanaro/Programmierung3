import domain_logic.ManufacturerImpl;
import observer_pattern.VendingMachineSimulationObserver;
import simulation.VendingMachineSimulation1;
import simulation.Consumer;
import simulation.Producer;

public class Simulation1Main {
    public static void main(String[] args) {
        try{
        VendingMachineSimulation1 automat = new VendingMachineSimulation1(Integer.parseInt(args[0]));
        new VendingMachineSimulationObserver(automat);
        automat.addManufacturer(new ManufacturerImpl("Blueberryland"));
        automat.addManufacturer(new ManufacturerImpl("Gooseberryland"));
        new Consumer(automat).start();
        new Producer(automat).start();
        }catch (Exception e){
            System.out.println(e+" Kommandozeilenparameter: Automat-Capacity angeben!");
        }
    }
}
