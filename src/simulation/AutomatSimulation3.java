package simulation;

import domainLogic.automat.KuchenImplementierung;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class AutomatSimulation3 extends AutomatSimulation2 {
    public AutomatSimulation3(int capacity) {
        super(capacity);
    }

    public void deleteVerkaufsobjekteWithOldestDateXTimes() {
        super.getLock().lock();
        try {
            while (super.getVerkaufsobjekte().size() == 0) super.getFilled().await();
            if (super.getVerkaufsobjekte().size() == 0)
                throw new IllegalStateException();

            Random random = new Random();
            int numberOfDeletions = random.nextInt(super.getVerkaufsobjekte().size());
            if(numberOfDeletions==0)numberOfDeletions++;
            for (int j = 0; j < numberOfDeletions; j++) {
                Date oldestDate = new SimpleDateFormat("dd/MM/yyyy").parse("99/99/9999");
                KuchenImplementierung oldestCake = null;
                ArrayList<KuchenImplementierung> k = super.getVerkaufsobjekte();

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
                super.deleteVerkaufsobjekt(oldestCake.getFachnummer());
            }

            super.getOneDeleted().signal();
        } catch (InterruptedException | ParseException e) {
            System.out.println(e);
        } finally {
            super.getLock().unlock();
        }
    }
}
