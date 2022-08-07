import CLI.CLI;
import event_system.*;
import observer_pattern.VendingMachineAllergensObserver;
import observer_pattern.VendingMachineCapacityObserver;
import observer_pattern.ObservableVendingMachine;
import java.util.Scanner;

public class CLIMain {
    public static void main(String[] args){
        try{
            ObservableVendingMachine a = new ObservableVendingMachine(Integer.parseInt(args[0]));
            CLI r;
            if (args.length == 2) {
                r = new CLI(a, new VendingMachineAllergensObserver(a), new VendingMachineCapacityObserver(a), new Scanner(System.in), args[1]);
            } else{
                r = new CLI(a, new VendingMachineAllergensObserver(a), new VendingMachineCapacityObserver(a), new Scanner(System.in));
            }
            InputEventHandler handler = new InputEventHandler();
            InputEventListener lPrint = new InputEventListenerPrint();
            InputEventListener lAddHersteller = new InputEventListenerAddManufacturer();
            InputEventListener lAddObsttorte = new InputEventListenerAddFruitFlan();
            InputEventListener lAddObstkuchen = new InputEventListenerAddFruitCake();
            InputEventListener lAddKremkuchen = new InputEventListenerAddCreamCake();
            InputEventListener lListHersteller = new InputEventListenerListManufacturer();
            InputEventListener lListProducts = new InputEventListenerListProducts();
            InputEventListener lListAllergene = new InputEventListenerListAllergens();
            InputEventListener lDeleteProduct = new InputEventListenerDeleteProduct();
            InputEventListener lDeleteHersteller = new InputEventListenerDeleteManufacturer();
            InputEventListener lDoInspection = new InputEventListenerDoInspection();
            InputEventListener lSafeAndLoad = new InputEventListenerSafeAndLoad();
            handler.add(lSafeAndLoad);
            handler.add(lDoInspection);
            handler.add(lDeleteHersteller);
            handler.add(lDeleteProduct);
            handler.add(lListAllergene);
            handler.add(lListHersteller);
            handler.add(lListProducts);
            handler.add(lPrint);
            handler.add(lAddHersteller);
            handler.add(lAddObsttorte);
            handler.add(lAddObstkuchen);
            handler.add(lAddKremkuchen);
            r.setInputEventHandler(handler);
            r.start();
        }catch (Exception e){
            System.out.println(e+" Kommandozeilenparameter: Automat-Capacity angeben!");
        }
    }
}
