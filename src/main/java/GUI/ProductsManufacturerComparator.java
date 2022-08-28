package GUI;

import domain_logic.CakeImpl;

import java.util.Comparator;

public class ProductsManufacturerComparator implements Comparator<CakeImpl> {
    @Override
    public int compare(CakeImpl product1, CakeImpl product2){
        return product1.getManufacturer().getName().compareTo(product2.getManufacturer().getName());
    }
}
