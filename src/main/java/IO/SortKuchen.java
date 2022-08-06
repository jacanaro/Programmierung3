package IO;

import java.util.Comparator;

public class SortKuchen implements Comparator<KuchenImplementierungPojo> {
    @Override
    public int compare(KuchenImplementierungPojo k1, KuchenImplementierungPojo k2){
        return k1.getFachnummer() - k2.getFachnummer();
    }
}
