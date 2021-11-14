package simulation;

import domainLogic.automat.AutomatErrorCodes;
import domainLogic.automat.KuchenImplementierung;
import observerPattern.ObservableAutomat;

import java.util.ArrayList;
import java.util.Random;

public class AutomatSimulation1 extends ObservableAutomat {//nicht ableiten, geht besser siehe über-/nächste vorlesung

    public AutomatSimulation1(int capacity) {
        super(capacity);
    }

    @Override
    public AutomatErrorCodes addVerkaufsobjekt(KuchenImplementierung kuchen) {
            if (this.getVerkaufsobjekte().size() >= this.getCAPACITY()) throw new IllegalStateException();

            AutomatErrorCodes a = super.addVerkaufsobjekt(kuchen);
            return a;
    }

    public boolean deleteVerkaufsobjektRandom() {
            if (this.getVerkaufsobjekte().size() == 0) throw new IllegalStateException();

            ArrayList<KuchenImplementierung> kListe = getVerkaufsobjekte();
            Random random = new Random();
            int randomIndexOfVerkaufsobjektliste = random.nextInt(kListe.size());

            KuchenImplementierung k = kListe.get(randomIndexOfVerkaufsobjektliste);
            boolean success = false;
            if (k != null) success = super.deleteVerkaufsobjekt(k.getFachnummer());
            return success;
    }
}
