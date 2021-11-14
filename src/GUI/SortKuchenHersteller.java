package GUI;

import IO.KuchenImplementierungPojo;
import domainLogic.automat.KuchenImplementierung;

import java.util.Comparator;

public class SortKuchenHersteller implements Comparator<KuchenImplementierung> {
    @Override
    public int compare(KuchenImplementierung k1, KuchenImplementierung k2){
        return k1.getHersteller().getName().compareTo(k2.getHersteller().getName());
    }
}
