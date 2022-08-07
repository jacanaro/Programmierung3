package simulation;

import domain_logic.VendingMachineErrorCodes;

import domain_logic.CakeImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VendingMachineSimulation2 extends VendingMachineSimulation1 {
    private final Lock lock = new ReentrantLock();
    private final Condition filled = this.lock.newCondition();
    private final Condition oneDeleted = this.lock.newCondition();

    public VendingMachineSimulation2(int capacity) {
        super(capacity);
    }

    private boolean isFull() {
        return getCAPACITY() == this.getProducts().size();
    }

    @Override
    public VendingMachineErrorCodes addProduct(CakeImpl cake) {
        this.lock.lock();
        try {
            while (this.isFull()) this.oneDeleted.await();
            if (this.getProducts().size() >= getCAPACITY()) throw new IllegalStateException();

            VendingMachineErrorCodes a = super.addProduct(cake);
            if (a == null) this.filled.signal();
            return a;
        } catch (InterruptedException e){
            System.out.println(e);
            return null;
        }
        finally {
            this.lock.unlock();
        }
    }

    public boolean deleteVerkaufsobjektWithOldestDate() {
        this.lock.lock();
        try {
            while (getProducts().size() == 0) this.filled.await();
            if (this.getProducts().size() == 0)
                throw new IllegalStateException();

            Date oldestDate = new SimpleDateFormat("dd/MM/yyyy").parse("99/99/9999");
            CakeImpl oldestCake = null;
            ArrayList<CakeImpl> k = getProducts();

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
            boolean success = false;
            if (oldestCake != null) success = super.deleteProduct(oldestCake.getVendingMachineSlot());
            if (success) this.oneDeleted.signal();
            return success;

        } catch (InterruptedException | ParseException e){
            System.out.println(e);
            return false;
        }

        finally {
            this.lock.unlock();
        }
    }

    public CakeImpl doRandomInspection() {
        CakeImpl inspectedItem=null;
        this.lock.lock();
        try {
            if (this.getProducts().size() != 0){
                ArrayList<CakeImpl> kListe = getProducts();
                Random random = new Random();
                int randomIndexOfVerkaufsobjektliste = random.nextInt(kListe.size());

                CakeImpl k = kListe.get(randomIndexOfVerkaufsobjektliste);
                inspectedItem=super.doInspection(k.getVendingMachineSlot());
            }
        } finally {
            this.lock.unlock();
        }
        return inspectedItem;
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getFilled() {
        return filled;
    }

    public Condition getOneDeleted() {
        return oneDeleted;
    }
}

