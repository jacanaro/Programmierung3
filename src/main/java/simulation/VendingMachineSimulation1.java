package simulation;

import domain_logic.VendingMachineErrorCodes;
import domain_logic.CakeImpl;
import observer_pattern.ObservableVendingMachine;

import java.util.ArrayList;
import java.util.Random;

public class VendingMachineSimulation1 extends ObservableVendingMachine {

    public VendingMachineSimulation1(int capacity) {
        super(capacity);
    }

    @Override
    public VendingMachineErrorCodes addVerkaufsobjekt(CakeImpl kuchen) {
            if (this.getVerkaufsobjekte().size() >= this.getCAPACITY()) throw new IllegalStateException();

            VendingMachineErrorCodes a = super.addVerkaufsobjekt(kuchen);
            return a;
    }

    public boolean deleteVerkaufsobjektRandom() {
            if (this.getVerkaufsobjekte().size() == 0) throw new IllegalStateException();

            ArrayList<CakeImpl> kListe = getVerkaufsobjekte();
            Random random = new Random();
            int randomIndexOfVerkaufsobjektliste = random.nextInt(kListe.size());

            CakeImpl k = kListe.get(randomIndexOfVerkaufsobjektliste);
            boolean success = false;
            if (k != null) success = super.deleteVerkaufsobjekt(k.getFachnummer());
            return success;
    }
}
