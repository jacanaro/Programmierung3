package IO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class VendingMachinePOJO {
    public Collection<CakePojo> productPojos =new ArrayList<>();
    public Collection<ManufacturerPojo> manufacturerPojos =new HashSet<>();
    public int capacity;

    public Collection<CakePojo> getProductPojos() {
        return productPojos;
    }

    public Collection<ManufacturerPojo> getManufacturerPojos() {
        return manufacturerPojos;
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
                "verkaufsobjektListe=" + productPojos +
                ", herstellerSet=" + manufacturerPojos +
                ", capacity=" + capacity +
                '}';
    }
}
