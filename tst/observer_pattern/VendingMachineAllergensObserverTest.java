package observer_pattern;

import domain_logic.Allergen;
import domain_logic.ManufacturerImpl;
import domain_logic.CakeImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineAllergensObserverTest {
    private ObservableVendingMachine automat;
    private ManufacturerImpl drOetker=new ManufacturerImpl("Dr. Oetker");
    private HashSet<Allergen> allergens=new HashSet<>();
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        automat = new ObservableVendingMachine(20);
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new CakeImpl("Schokocreme",drOetker, allergens, 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));
        automat.addVerkaufsobjekt(new CakeImpl("Schokocreme",drOetker, new HashSet<>(), 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));

        new VendingMachineAllergensObserver(automat);
    }

    @AfterEach
    void tearDown(){
        System.setOut(originalOut);
    }

    @Test
    void testConstructor(){
        VendingMachineAllergensObserver allergeneObserver= new VendingMachineAllergensObserver(automat);
        assertEquals(automat, allergeneObserver.getObservableAutomat());
        assertEquals(automat.getAllergene(true), allergeneObserver.getAllergene());
        assertTrue(automat.getBeobachterList().contains(allergeneObserver));
    }

    @Test
    void checkForUpdateMsgWhenCakeWithNewAllergensIsAdded() {
        allergens.add(Allergen.Sesamsamen);
        automat.addVerkaufsobjekt(new CakeImpl("Schokocreme",drOetker, allergens, 1000,
                Duration.ofHours(220),"Erdbeere", new BigDecimal("3.00")));

        String emptyString="";

        assertNotEquals(emptyString,outContent.toString());
    }

    @Test
    void checkForUpdateMsgWhenCakeWithoutNewAllergensIsAdded() {
        automat.addVerkaufsobjekt(new CakeImpl("Sahne",drOetker, allergens, 123,
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