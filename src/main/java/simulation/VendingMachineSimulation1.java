package simulation;

import domain_logic.VendingMachineErrorCodes;
import domain_logic.CakeImpl;
import observer_pattern.ObservableVendingMachine;

import java.util.ArrayList;
import java.util.Random;

public class VendingMachineSimulation1 extends ObservableVendingMachine {

    public VendingMachineSimulation1(int capacity) {
        super(capacity);
    }

    @Override
    public VendingMachineErrorCodes addProduct(CakeImpl product) {
            if (this.getProducts().size() >= this.getCAPACITY()) throw new IllegalStateException();

            VendingMachineErrorCodes a = super.addProduct(product);
            return a;
    }

    public boolean deleteRandomProduct() {
            if (this.getProducts().size() == 0) throw new IllegalStateException();

            ArrayList<CakeImpl> products = getProducts();
            Random random = new Random();
            int randomIndex = random.nextInt(products.size());

            CakeImpl product = products.get(randomIndex);
            boolean success = false;
            if (product != null) success = super.deleteProduct(product.getVendingMachineSlot());
            return success;
    }
}
