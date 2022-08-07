package observer_pattern;
import domain_logic.Allergen;
import domain_logic.ManufacturerImpl;
import domain_logic.CakeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ObservableVendingMachineTest {
    private ObservableVendingMachine automat;

    @BeforeEach
    void setUp() {
        automat = new ObservableVendingMachine(20);
    }

    @Test
    void testConstructor(){
        ObservableVendingMachine observableVendingMachine = new ObservableVendingMachine(2);
        assertEquals(2, observableVendingMachine.getCAPACITY());
    }

    @Test
    void testConstructorWithNegativeCapacity(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new ObservableVendingMachine(-1);} );
    }

    @Test
    void addObserver() {
        VendingMachineAllergensObserver testObserver = new VendingMachineAllergensObserver(automat);
        assertTrue(automat.addObserver(testObserver));
    }

    @Test
    void removeObserverHappyPath() {
        VendingMachineAllergensObserver testObserver = new VendingMachineAllergensObserver(automat);
        assertTrue(automat.removeObserver(testObserver));
    }

    @Test
    void notifyObservers() {
        VendingMachineAllergensObserver observerMock = mock(VendingMachineAllergensObserver.class);
        automat.addObserver(observerMock);
        automat.notifyObservers();
        verify(observerMock, times(1)).update();
    }

    @Test
    void addVerkaufsobjekt() {
        ManufacturerImpl drOetker = new ManufacturerImpl("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);

        //if null, no error(code) occured
        assertNull(automat.addVerkaufsobjekt(new CakeImpl("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00"))));
    }

    @Test
    void testIfAddVerkaufsobjektNotifiesObserver(){
        VendingMachineAllergensObserver observerMock = mock(VendingMachineAllergensObserver.class);
        ManufacturerImpl drOetker = new ManufacturerImpl("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addObserver(observerMock);
        automat.addVerkaufsobjekt(new CakeImpl("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));
        verify(observerMock, times(1)).update();
    }

    @Test
    void deleteVerkaufsobjekt() {
        ManufacturerImpl drOetker = new ManufacturerImpl("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new CakeImpl("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));

        assertTrue(automat.deleteVerkaufsobjekt(0));
    }
    @Test
    void testIfDeleteVerkaufsobjektNotifiesObserver(){
        ManufacturerImpl drOetker = new ManufacturerImpl("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new CakeImpl("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));
        VendingMachineAllergensObserver observerMock = mock(VendingMachineAllergensObserver.class);
        automat.addObserver(observerMock);
        automat.deleteVerkaufsobjekt(0);

        verify(observerMock, times(1)).update();
    }
    @Test
    void doInspection() {
        ManufacturerImpl drOetker = new ManufacturerImpl("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new CakeImpl("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));
        Date oldDate= automat.getVerkaufsobjekte().get(0).getInspektionsdatum();
        automat.doInspection(0);
        Date newDate= automat.getVerkaufsobjekte().get(0).getInspektionsdatum();
        assertTrue(oldDate!=newDate);
    }
    @Test
    void testIfDoInspectionNotifiesObserver(){
        ManufacturerImpl drOetker = new ManufacturerImpl("Dr. Oetker");
        HashSet<Allergen> allergens = new HashSet<>();
        automat.addHersteller(drOetker);
        allergens.add(Allergen.Haselnuss);
        automat.addVerkaufsobjekt(new CakeImpl("Schokocreme", drOetker, allergens, 1000,
                Duration.ofHours(220), "Erdbeere", new BigDecimal("3.00")));
        VendingMachineAllergensObserver observerMock = mock(VendingMachineAllergensObserver.class);
        automat.addObserver(observerMock);

        automat.doInspection(0);
        verify(observerMock, times(1)).update();
    }
}