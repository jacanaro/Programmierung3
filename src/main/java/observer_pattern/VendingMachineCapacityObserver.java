package observer_pattern;

import domain_logic.CakeImpl;

import java.util.ArrayList;

public class VendingMachineCapacityObserver implements Observer {
    private ObservableVendingMachine observableVendingMachine;
    private ArrayList<CakeImpl> products;

    public VendingMachineCapacityObserver(ObservableVendingMachine observableVendingMachine) {
        this.observableVendingMachine = observableVendingMachine;
        this.products = new ArrayList<>(observableVendingMachine.getProducts());
        this.observableVendingMachine.addObserver(this);
    }

    @Override
    public void update(){
        products = observableVendingMachine.getProducts();
        if(products.size()> observableVendingMachine.getCAPACITY()*0.9) {
            System.out.println(products.size()+" von "+ observableVendingMachine.getCAPACITY()+" Fächern sind belegt!");
        }
    }

    public ObservableVendingMachine getObservableAutomat() {
        return observableVendingMachine;
    }

    public ArrayList<CakeImpl> getProducts() {
        return products;
    }
}
