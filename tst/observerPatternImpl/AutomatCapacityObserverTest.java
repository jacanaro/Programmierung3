package observerPatternImpl;
import domainLogic.automat.Allergen;
import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;
import observerPattern.AutomatCapacityObserver;
import observerPattern.ObservableAutomat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AutomatCapacityObserverTest {
    private ObservableAutomat automat;
    private HerstellerImplementierung drOetker=new HerstellerImplementierung("Dr. Oetker");
    private HashSet<Allergen> allergens=new HashSet<>();
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        automat = new ObservableAutomat(10);
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);

        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, allergens, 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, new HashSet<>(), 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, allergens, 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, new HashSet<>(), 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, allergens, 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, new HashSet<>(), 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, allergens, 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, new HashSet<>(), 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        new AutomatCapacityObserver(automat);
    }

    @AfterEach
    void tearDown(){
        System.setOut(originalOut);
    }

    @Test
    void testConstructor(){
        AutomatCapacityObserver capacityObserver= new AutomatCapacityObserver(automat);
        assertEquals(automat, capacityObserver.getObservableAutomat());
        assertEquals(automat.getVerkaufsobjekte(), capacityObserver.getVerkaufsobjektListe());
        assertTrue(automat.getBeobachterList().contains(capacityObserver));
    }

    @Test
    void checkForUpdateMsgWhenAutomatReaches90PercentOfItsCapacity(){
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, new HashSet<>(), 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));

        String emptyString="";

        assertEquals(emptyString,outContent.toString());
    }

    @Test
    void checkForUpdateMsgWhenAutomatRemainsUnder90PercentOfItsCapacity(){
        automat.deleteVerkaufsobjekt(0);

        String emptyString="";

        assertEquals(emptyString,outContent.toString());
    }

    @Test
    void checkForUpdateMsgWhenAutomatExceeds90PercentOfItsCapacity(){
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, new HashSet<>(), 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, new HashSet<>(), 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));

        String emptyString="";

        assertNotEquals(emptyString,outContent.toString());
    }
}