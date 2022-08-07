package simulation;

import domain_logic.CakeImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class VendingMachineSimulation3 extends VendingMachineSimulation2 {
    public VendingMachineSimulation3(int capacity) {
        super(capacity);
    }

    public void deleteVerkaufsobjekteWithOldestDateXTimes() {
        super.getLock().lock();
        try {
            while (super.getProducts().size() == 0) super.getFilled().await();
            if (super.getProducts().size() == 0)
                throw new IllegalStateException();

            Random random = new Random();
            int numberOfDeletions = random.nextInt(super.getProducts().size());
            if(numberOfDeletions==0)numberOfDeletions++;
            for (int j = 0; j < numberOfDeletions; j++) {
                Date oldestDate = new SimpleDateFormat("dd/MM/yyyy").parse("99/99/9999");
                CakeImpl oldestCake = null;
                ArrayList<CakeImpl> k = super.getProducts();

                for (int i = 0; i < k.size(); i++) {
                    Date d = k.get(i).getDateOfInspection();

                    if (d.compareTo(oldestDate) == 0) {
                        continue;
                    } else if (d.compareTo(oldestDate) < 0) {
                        oldestDate = d;
                        oldestCake = k.get(i);
                    } else if (d.compareTo(oldestDate) > 0) {
                        continue;
                    }
                }
                super.deleteProduct(oldestCake.getVendingMachineSlot());
            }

            super.getOneDeleted().signal();
        } catch (InterruptedException | ParseException e) {
            System.out.println(e);
        } finally {
            super.getLock().unlock();
        }
    }
}
