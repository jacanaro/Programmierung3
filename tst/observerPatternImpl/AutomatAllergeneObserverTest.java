package observerPatternImpl;

import domainLogic.automat.Allergen;
import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;
import observerPattern.AutomatAllergeneObserver;
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

class AutomatAllergeneObserverTest {
    private ObservableAutomat automat;
    private HerstellerImplementierung drOetker=new HerstellerImplementierung("Dr. Oetker");
    private HashSet<Allergen> allergens=new HashSet<>();
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        automat = new ObservableAutomat(20);
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, allergens, 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, new HashSet<>(), 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));

        new AutomatAllergeneObserver(automat);
    }

    @AfterEach
    void tearDown(){
        System.setOut(originalOut);
    }

    @Test
    void testConstructor(){
        AutomatAllergeneObserver allergeneObserver= new AutomatAllergeneObserver(automat);
        assertEquals(automat, allergeneObserver.getObservableAutomat());
        assertEquals(automat.getAllergene(true), allergeneObserver.getAllergene());
        assertTrue(automat.getBeobachterList().contains(allergeneObserver));
    }

    @Test
    void checkForUpdateMsgWhenCakeWithNewAllergensIsAdded() {
        allergens.add(Allergen.Sesamsamen);
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme",drOetker, allergens, 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));

        String emptyString="";

        assertNotEquals(emptyString,outContent.toString());
    }

    @Test
    void checkForUpdateMsgWhenCakeWithoutNewAllergensIsAdded() {
        automat.addVerkaufsobjekt(new KuchenImplementierung("Sahne",drOetker, allergens, 123,
                Duration.ofHours(123),"afg", new BigDecimal("1.00")));

        String emptyString="";

        assertEquals(emptyString,outContent.toString());
    }

    @Test
    void checkForUpdateMsgWhenCakeWithAllergensIsRemoved(){
        automat.deleteVerkaufsobjekt(0);

        String emptyString="";

        assertNotEquals(emptyString,outContent.toString());
    }

    @Test
    void checkForUpdateMsgWhenCakeWithoutAllergensIsRemoved(){
        automat.deleteVerkaufsobjekt(1);

        String emptyString="";

        assertEquals(emptyString,outContent.toString());
    }

    @Test
    void checkForUpdateMsgWhenNoCakeIsRemovedOrAdded() {
        String emptyString="";

        assertEquals(emptyString,outContent.toString());
    }

}