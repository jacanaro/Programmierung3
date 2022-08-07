package observer_pattern;

import domain_logic.Allergen;

import java.util.HashSet;

public class VendingMachineAllergensObserver implements Observer {
    private ObservableVendingMachine observableVendingMachine;
    private HashSet<Allergen> allergene;

    public VendingMachineAllergensObserver(ObservableVendingMachine automat) {
        this.observableVendingMachine = automat;
        this.allergene = new HashSet<>(automat.getAllergene(true));
        this.observableVendingMachine.addObserver(this);
    }

    @Override
    public void update(){
        if(!allergene.equals(observableVendingMachine.getAllergene(true))){
            System.out.println("Allergene im alten Automat: "+ allergene.toString()+
                    "\n Allergene im neuen Automat: "+ observableVendingMachine.getAllergene(true).toString());
            allergene= observableVendingMachine.getAllergene(true);
        }
    }

    public ObservableVendingMachine getObservableAutomat() {
        return observableVendingMachine;
    }

    public HashSet<Allergen> getAllergene() {
        return allergene;
    }
}
