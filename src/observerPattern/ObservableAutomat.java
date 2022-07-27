package observerPattern;

import domainLogic.automat.Automat;
import domainLogic.automat.AutomatErrorCodes;
import domainLogic.automat.KuchenImplementierung;

import java.util.ArrayList;


public class ObservableAutomat extends Automat implements Observable {

    ArrayList<Observer> beobachterList = new ArrayList<>();

    public ObservableAutomat(int capacity) {
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
    public AutomatErrorCodes addVerkaufsobjekt(KuchenImplementierung kuchen) {
        AutomatErrorCodes a=super.addVerkaufsobjekt(kuchen);
        notifyObservers();
        return a;
    }

    @Override
    public boolean deleteVerkaufsobjekt(int fachnummer) {
        boolean success=super.deleteVerkaufsobjekt(fachnummer);
        notifyObservers();
        return success;
    }

    @Override
    public KuchenImplementierung doInspection(int fachnummer) {
        KuchenImplementierung k=super.doInspection(fachnummer);
        notifyObservers();
        return k;
    }

    public ArrayList<Observer> getBeobachterList() {
        return beobachterList;
    }
}
