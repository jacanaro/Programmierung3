package kapselung;

import domainLogic.automat.Automat;
import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulation.RandomValues;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;


public class KapselungTest {
    @BeforeEach
    void setUp(){

    }

    @Test
    void testAutomatVerkaufsobjektlisteKapselung(){
        Automat automat= new Automat(5);
        automat.addHersteller(new HerstellerImplementierung("hersteller1"));
        automat.addHersteller(new HerstellerImplementierung("hersteller2"));
        RandomValues randomValues= new RandomValues(new HerstellerImplementierung("hersteller1"),new HerstellerImplementierung("hersteller2"));
        automat.addVerkaufsobjekt(randomValues.getRandomKuchen());
        automat.addVerkaufsobjekt(randomValues.getRandomKuchen());
        ArrayList<KuchenImplementierung>kuchenImplementierungArrayList=automat.getVerkaufsobjekte();
        kuchenImplementierungArrayList.clear();
        assertFalse(automat.getVerkaufsobjekte().isEmpty());
    }
}
