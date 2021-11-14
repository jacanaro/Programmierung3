package observerPattern;

import domainLogic.automat.Allergen;

import java.util.HashSet;

public class AutomatAllergeneObserver implements Observer {

    private ObservableAutomat observableAutomat;
    private HashSet<Allergen> allergene;

    public AutomatAllergeneObserver(ObservableAutomat automat) {
        this.observableAutomat = automat;
        this.allergene = new HashSet<>(automat.getAllergene(true));
        this.observableAutomat.addObserver(this);
    }

    public ObservableAutomat getObservableAutomat() {
        return observableAutomat;
    }

    public HashSet<Allergen> getAllergene() {
        return allergene;
    }

    @Override
    public void update(){
        if(!allergene.equals(observableAutomat.getAllergene(true))){
            System.out.println("Allergene im alten Automat: "+ allergene.toString()+
                    "\n Allergene im neuen Automat: "+observableAutomat.getAllergene(true).toString());
            allergene=observableAutomat.getAllergene(true);
        }
    }
}
