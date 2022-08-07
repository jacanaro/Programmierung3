package observer_pattern;

import domain_logic.Allergen;

import java.util.HashSet;

public class VendingMachineAllergensObserver implements Observer {
    private ObservableVendingMachine observableVendingMachine;
    private HashSet<Allergen> allergene;

    public VendingMachineAllergensObserver(ObservableVendingMachine automat) {
        this.observableVendingMachine = automat;
        this.allergene = new HashSet<>(automat.getAllergens(true));
        this.observableVendingMachine.addObserver(this);
    }

    @Override
    public void update(){
        if(!allergene.equals(observableVendingMachine.getAllergens(true))){
            System.out.println("Allergene im alten Automat: "+ allergene.toString()+
                    "\n Allergene im neuen Automat: "+ observableVendingMachine.getAllergens(true).toString());
            allergene= observableVendingMachine.getAllergens(true);
        }
    }

    public ObservableVendingMachine getObservableAutomat() {
        return observableVendingMachine;
    }

    public HashSet<Allergen> getAllergene() {
        return allergene;
    }
}
