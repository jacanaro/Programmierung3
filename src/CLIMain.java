import CLI.ConsoleReader;
import eventSystem.*;
import observerPattern.AutomatAllergeneObserver;
import observerPattern.AutomatCapacityObserver;
import observerPattern.ObservableAutomat;
import java.util.Scanner;

public class CLIMain {
    public static void main(String[] args){
        try{
            ObservableAutomat a = new ObservableAutomat(Integer.parseInt(args[0]));
            ConsoleReader r;
            if (args.length == 2) {
                r = new ConsoleReader(a, new AutomatAllergeneObserver(a), new AutomatCapacityObserver(a), new Scanner(System.in), args[1]);
            } else{
                r = new ConsoleReader(a, new AutomatAllergeneObserver(a), new AutomatCapacityObserver(a), new Scanner(System.in));
            }
            InputEventHandler handler = new InputEventHandler();
            InputEventListener lPrint = new InputEventListenerPrint();
            InputEventListener lAddHersteller = new InputEventListenerAddHersteller();
            InputEventListener lAddObsttorte = new InputEventListenerAddObsttorte();
            InputEventListener lAddObstkuchen = new InputEventListenerAddObstkuchen();
            InputEventListener lAddKremkuchen = new InputEventListenerAddKremkuchen();
            InputEventListener lListHersteller = new InputEventListenerListHersteller();
            InputEventListener lListProducts = new InputEventListenerListProducts();
            InputEventListener lListAllergene = new InputEventListenerListAllergene();
            InputEventListener lDeleteProduct = new InputEventListenerDeleteProduct();
            InputEventListener lDeleteHersteller = new InputEventListenerDeleteHersteller();
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
            r.setHandler(handler);
            r.start();
        }catch (Exception e){
            System.out.println(e+" Kommandozeilenparameter: Automat-Capacity angeben!");
        }
    }
}
