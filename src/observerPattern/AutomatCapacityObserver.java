package observerPattern;

import domainLogic.automat.KuchenImplementierung;

import java.util.ArrayList;

public class AutomatCapacityObserver implements Observer {
    private ObservableAutomat observableAutomat;
    private ArrayList<KuchenImplementierung> verkaufsobjektListe;

    public AutomatCapacityObserver(ObservableAutomat automat) {
        this.observableAutomat = automat;
        this.verkaufsobjektListe= new ArrayList<>(automat.getVerkaufsobjekte());
        this.observableAutomat.addObserver(this);
    }

    public ObservableAutomat getObservableAutomat() {
        return observableAutomat;
    }

    public ArrayList<KuchenImplementierung> getVerkaufsobjektListe() {
        return verkaufsobjektListe;
    }

    @Override
    public void update(){
        verkaufsobjektListe=observableAutomat.getVerkaufsobjekte();
        if(verkaufsobjektListe.size()>observableAutomat.getCAPACITY()*0.9) {
            System.out.println(verkaufsobjektListe.size()+" von "+observableAutomat.getCAPACITY()+" Fächern sind belegt!");
        }
    }
}
