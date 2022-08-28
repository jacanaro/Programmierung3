package IO;

import java.util.Comparator;

public class ProductsIdComparator implements Comparator<CakePojo> {
    @Override
    public int compare(CakePojo k1, CakePojo k2){
        return k1.getVendingMachineSlot() - k2.getVendingMachineSlot();
    }
}
