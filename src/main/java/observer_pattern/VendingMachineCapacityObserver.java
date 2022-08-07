package observer_pattern;

import domain_logic.CakeImpl;

import java.util.ArrayList;

public class VendingMachineCapacityObserver implements Observer {
    private ObservableVendingMachine observableVendingMachine;
    private ArrayList<CakeImpl> verkaufsobjektListe;

    public VendingMachineCapacityObserver(ObservableVendingMachine automat) {
        this.observableVendingMachine = automat;
        this.verkaufsobjektListe= new ArrayList<>(automat.getVerkaufsobjekte());
        this.observableVendingMachine.addObserver(this);
    }

    @Override
    public void update(){
        verkaufsobjektListe= observableVendingMachine.getVerkaufsobjekte();
        if(verkaufsobjektListe.size()> observableVendingMachine.getCAPACITY()*0.9) {
            System.out.println(verkaufsobjektListe.size()+" von "+ observableVendingMachine.getCAPACITY()+" Fächern sind belegt!");
        }
    }

    public ObservableVendingMachine getObservableAutomat() {
        return observableVendingMachine;
    }

    public ArrayList<CakeImpl> getVerkaufsobjektListe() {
        return verkaufsobjektListe;
    }
}
