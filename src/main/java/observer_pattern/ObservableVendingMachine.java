package observer_pattern;

import domain_logic.VendingMachine;
import domain_logic.VendingMachineErrorCodes;
import domain_logic.CakeImpl;

import java.util.ArrayList;


public class ObservableVendingMachine extends VendingMachine implements Observable {

    ArrayList<Observer> observers = new ArrayList<>();

    public ObservableVendingMachine(int capacity) {
        super(capacity);
    }

    @Override public boolean addObserver(Observer observer){
        return this.observers.add(observer);
    }

    @Override public boolean removeObserver(Observer observer){
        return this.observers.remove(observer);
    }

    @Override public void notifyObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public VendingMachineErrorCodes addProduct(CakeImpl product) {
        VendingMachineErrorCodes vendingMachineErrorCodes=super.addProduct(product);
        notifyObservers();
        return vendingMachineErrorCodes;
    }

    @Override
    public boolean deleteProduct(int vendingMachineSlot) {
        boolean success=super.deleteProduct(vendingMachineSlot);
        notifyObservers();
        return success;
    }

    @Override
    public CakeImpl doInspection(int vendingMachineSlot) {
        CakeImpl product=super.doInspection(vendingMachineSlot);
        notifyObservers();
        return product;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }
}
