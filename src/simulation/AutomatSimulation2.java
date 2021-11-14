package simulation;

import domainLogic.automat.AutomatErrorCodes;

import domainLogic.automat.KuchenImplementierung;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AutomatSimulation2 extends AutomatSimulation1 {
    private final Lock lock = new ReentrantLock();
    private final Condition filled = this.lock.newCondition();
    private final Condition oneDeleted = this.lock.newCondition();

    public Lock getLock() {
        return lock;
    }

    public Condition getFilled() {
        return filled;
    }

    public Condition getOneDeleted() {
        return oneDeleted;
    }

    public AutomatSimulation2(int capacity) {
        super(capacity);
    }


    private boolean isFull() {
        return getCAPACITY() == this.getVerkaufsobjekte().size();
    }

    @Override
    public AutomatErrorCodes addVerkaufsobjekt(KuchenImplementierung kuchen) {
        this.lock.lock();
        try {
            while (this.isFull()) this.oneDeleted.await();
            if (this.getVerkaufsobjekte().size() >= getCAPACITY()) throw new IllegalStateException();

            AutomatErrorCodes a = super.addVerkaufsobjekt(kuchen);
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
            while (getVerkaufsobjekte().size() == 0) this.filled.await();
            if (this.getVerkaufsobjekte().size() == 0)
                throw new IllegalStateException();

            Date oldestDate = new SimpleDateFormat("dd/MM/yyyy").parse("99/99/9999");
            KuchenImplementierung oldestCake = null;
            ArrayList<KuchenImplementierung> k = getVerkaufsobjekte();

            for (int i = 0; i < k.size(); i++) {
                Date d = k.get(i).getInspektionsdatum();

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
            if (oldestCake != null) success = super.deleteVerkaufsobjekt(oldestCake.getFachnummer());
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

    public KuchenImplementierung doRandomInspection() {
        KuchenImplementierung inspectedItem=null;
        this.lock.lock();
        try {
            if (this.getVerkaufsobjekte().size() != 0){
                ArrayList<KuchenImplementierung> kListe = getVerkaufsobjekte();
                Random random = new Random();
                int randomIndexOfVerkaufsobjektliste = random.nextInt(kListe.size());

                KuchenImplementierung k = kListe.get(randomIndexOfVerkaufsobjektliste);
                inspectedItem=super.doInspection(k.getFachnummer());
            }
        } finally {
            this.lock.unlock();
        }
        return inspectedItem;
    }

}

