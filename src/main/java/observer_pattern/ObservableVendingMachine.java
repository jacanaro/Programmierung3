package observer_pattern;

import domain_logic.VendingMachine;
import domain_logic.VendingMachineErrorCodes;
import domain_logic.CakeImpl;

import java.util.ArrayList;


public class ObservableVendingMachine extends VendingMachine implements Observable {

    ArrayList<Observer> beobachterList = new ArrayList<>();

    public ObservableVendingMachine(int capacity) {
        super(capacity);
    }

    @Override public boolean addObserver(Observer observer){
        return this.beobachterList.add(observer);
    }

    @Override public boolean removeObserver(Observer observer){
        return this.beobachterList.remove(observer);
    }

    @Override public void notifyObservers(){
        for (Observer observer : beobachterList) {
            observer.update();
        }
    }

    @Override
    public VendingMachineErrorCodes addProduct(CakeImpl cake) {
        VendingMachineErrorCodes a=super.addProduct(cake);
        notifyObservers();
        return a;
    }

    @Override
    public boolean deleteProduct(int vendingMachineSlot) {
        boolean success=super.deleteProduct(vendingMachineSlot);
        notifyObservers();
        return success;
    }

    @Override
    public CakeImpl doInspection(int vendingMachineSlot) {
        CakeImpl k=super.doInspection(vendingMachineSlot);
        notifyObservers();
        return k;
    }

    public ArrayList<Observer> getBeobachterList() {
        return beobachterList;
    }
}
