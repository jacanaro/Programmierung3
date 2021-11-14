package observerPatternImpl;
import domainLogic.automat.Allergen;
import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;
import observerPattern.AutomatAllergeneObserver;
import observerPattern.ObservableAutomat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ObservableAutomatTest {
    private ObservableAutomat automat;

    @BeforeEach
    void setUp() {
        automat = new ObservableAutomat(20);
    }

    @Test
    void testConstructor(){
        ObservableAutomat observableAutomat= new ObservableAutomat(2);
        assertEquals(2, observableAutomat.getCAPACITY());
    }

    @Test
    void testConstructorWithNegativeCapacity(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new ObservableAutomat(-1);} );
    }

    @Test
    void addObserver() {
        AutomatAllergeneObserver testObserver = new AutomatAllergeneObserver(automat);
        assertTrue(automat.addObserver(testObserver));
    }

    @Test
    void removeObserverHappyPath() {
        AutomatAllergeneObserver testObserver = new AutomatAllergeneObserver(automat);
        assertTrue(automat.removeObserver(testObserver));
    }

    @Test
    void notifyObservers() {
        AutomatAllergeneObserver observerMock = mock(AutomatAllergeneObserver.class);
        automat.addObserver(observerMock);
        automat.notifyObservers();
        verify(observerMock, times(1)).update();
    }

    @Test
    void addVerkaufsobjekt() {
        HerstellerImplementierung drOetker = new HerstellerImplementierung("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);

        //if null, no error(code) occured
        assertNull(automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00"))));
    }

    @Test
    void testIfAddVerkaufsobjektNotifiesObserver(){
        AutomatAllergeneObserver observerMock = mock(AutomatAllergeneObserver.class);
        HerstellerImplementierung drOetker = new HerstellerImplementierung("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addObserver(observerMock);
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));
        verify(observerMock, times(1)).update();
    }

    @Test
    void deleteVerkaufsobjekt() {
        HerstellerImplementierung drOetker = new HerstellerImplementierung("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));

        assertTrue(automat.deleteVerkaufsobjekt(0));
    }
    @Test
    void testIfDeleteVerkaufsobjektNotifiesObserver(){
        HerstellerImplementierung drOetker = new HerstellerImplementierung("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));
        AutomatAllergeneObserver observerMock = mock(AutomatAllergeneObserver.class);
        automat.addObserver(observerMock);
        automat.deleteVerkaufsobjekt(0);

        verify(observerMock, times(1)).update();
    }
    @Test
    void doInspection() {
        HerstellerImplementierung drOetker = new HerstellerImplementierung("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));
        Date oldDate= automat.getVerkaufsobjekte().get(0).getInspektionsdatum();
        automat.doInspection(0);
        Date newDate= automat.getVerkaufsobjekte().get(0).getInspektionsdatum();
        assertTrue(oldDate!=newDate);
    }
    @Test
    void testIfDoInspectionNotifiesObserver(){
        HerstellerImplementierung drOetker = new HerstellerImplementierung("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new KuchenImplementierung("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));
        AutomatAllergeneObserver observerMock = mock(AutomatAllergeneObserver.class);
        automat.addObserver(observerMock);

        automat.doInspection(0);
        verify(observerMock, times(1)).update();
    }
}