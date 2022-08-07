package IO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class VendingMachinePOJO {
    public Collection<CakePojo> verkaufsobjektListe=new ArrayList<>();
    public Collection<ManufacturerPojo> herstellerSet=new HashSet<>();
    public int capacity;

    public Collection<CakePojo> getVerkaufsobjektListe() {
        return verkaufsobjektListe;
    }

    public Collection<ManufacturerPojo> getHerstellerSet() {
        return herstellerSet;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "AutomatPOJO{" +
                "verkaufsobjektListe=" + verkaufsobjektListe +
                ", herstellerSet=" + herstellerSet +
                ", capacity=" + capacity +
                '}';
    }
}
