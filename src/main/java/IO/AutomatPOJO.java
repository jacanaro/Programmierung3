package IO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class AutomatPOJO {
    public Collection<KuchenImplementierungPojo> verkaufsobjektListe=new ArrayList<>();
    public Collection<HerstellerImplementierungPojo> herstellerSet=new HashSet<>();
    public int capacity;

    public Collection<KuchenImplementierungPojo> getVerkaufsobjektListe() {
        return verkaufsobjektListe;
    }

    public Collection<HerstellerImplementierungPojo> getHerstellerSet() {
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
