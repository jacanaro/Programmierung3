package observer_pattern;

import domain_logic.Allergen;

import java.util.HashSet;

public class VendingMachineAllergensObserver implements Observer {
    private ObservableVendingMachine observableVendingMachine;
    private HashSet<Allergen> allergens;

    public VendingMachineAllergensObserver(ObservableVendingMachine observableVendingMachine) {
        this.observableVendingMachine = observableVendingMachine;
        this.allergens = new HashSet<>(observableVendingMachine.getAllergens(true));
        this.observableVendingMachine.addObserver(this);
    }

    @Override
    public void update(){
        if(!allergens.equals(observableVendingMachine.getAllergens(true))){
            System.out.println("Allergene im alten Automat: "+ allergens.toString()+
                    "\n Allergene im neuen Automat: "+ observableVendingMachine.getAllergens(true).toString());
            allergens = observableVendingMachine.getAllergens(true);
        }
    }

    public ObservableVendingMachine getObservableAutomat() {
        return observableVendingMachine;
    }

    public HashSet<Allergen> getAllergens() {
        return allergens;
    }
}
