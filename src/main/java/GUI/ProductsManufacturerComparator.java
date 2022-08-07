package GUI;

import domain_logic.CakeImpl;

import java.util.Comparator;

public class ProductsManufacturerComparator implements Comparator<CakeImpl> {
    @Override
    public int compare(CakeImpl k1, CakeImpl k2){
        return k1.getHersteller().getName().compareTo(k2.getHersteller().getName());
    }
}
