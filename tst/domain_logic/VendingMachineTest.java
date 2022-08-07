package domain_logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VendingMachineTest {
    private VendingMachine automat;
    private ManufacturerImpl testHersteller;
    private HashSet<Allergen> allergens;
    private CakeImpl testVerkaufsobjekt;

    @BeforeEach
    public void setUp(){
        automat= new VendingMachine(20);
        testHersteller=new ManufacturerImpl("TestHersteller");
        allergens=new HashSet<>();
        allergens.add(Allergen.Gluten);
        testVerkaufsobjekt= new CakeImpl(testHersteller, allergens, "Kremtest",
                1, Duration.ofHours(220),  new BigDecimal(1));
    }

    @Test
    void testConstructor(){
        VendingMachine automat= new VendingMachine(2);
        assertEquals(2, automat.getCAPACITY());
    }

    @Test
    void createAutomatWithNegativCapacityValue(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new VendingMachine(-1);} );
    }

    @Test
    void addHerstellerEinfach() {
        automat.addHersteller(testHersteller);
        assertTrue(automat.getHerstellerSet().contains(testHersteller));
    }

    @Test
    void addHerstellerDoppelt(){
        automat.addHersteller(testHersteller);
        automat.addHersteller(testHersteller);

        ArrayList<ManufacturerImpl> herstellerListe= new ArrayList<>(automat.getHerstellerSet());

        assertEquals(1, herstellerListe.size());
    }

    @Test
    void addHerstellerMehrereUnterschiedliche(){
        automat.addHersteller(testHersteller);
        automat.addHersteller(new ManufacturerImpl("andererHersteller"));

        ArrayList<ManufacturerImpl> herstellerListe= new ArrayList<>(automat.getHerstellerSet());

        assertEquals(2, herstellerListe.size());
    }

    @Test
    void deleteOneExistingHersteller() {
        automat.addHersteller(testHersteller);
        automat.deleteHersteller("TestHersteller");
        assertFalse(automat.getHerstellerSet().contains(testHersteller));
    }

    @Test
    void deleteOneExistingHerstellerDoppelt() {
        automat.addHersteller(testHersteller);
        automat.deleteHersteller("TestHersteller");
        automat.deleteHersteller("TestHersteller");
        assertFalse(automat.getHerstellerSet().contains(testHersteller));
    }

    @Test
    void deleteOneOutOfThreeHersteller() {
        automat.addHersteller(testHersteller);
        automat.addHersteller(new ManufacturerImpl("andererHersteller"));
        automat.addHersteller(new ManufacturerImpl("andererHersteller2222"));
        automat.deleteHersteller("TestHersteller");
        assertFalse(automat.getHerstellerSet().contains(testHersteller));
    }

    @Test
    void deleteTheRightOneOutOfTwoHersteller() {
        automat.addHersteller(testHersteller);
        automat.addHersteller(new ManufacturerImpl("andererHersteller"));
        automat.deleteHersteller("andererHersteller");
        assertTrue(automat.getHerstellerSet().contains(testHersteller));
    }


    @Test
    void deleteNotExistingHersteller() {
        automat.addHersteller(testHersteller);
        automat.deleteHersteller("NotExistingHersteller");
        assertTrue(automat.getHerstellerSet().contains(testHersteller));
    }

    @Test
    void addVerkaufsobjektKremkuchen() {
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        assertTrue(automat.getVerkaufsobjekte().contains(testVerkaufsobjekt));
    }

    @Test
    void addVerkaufsobjektObsttorte(){
        automat.addHersteller(testHersteller);
        CakeImpl obstTorte= new CakeImpl("kreme", testHersteller, allergens, 3000, Duration.ofHours(42), "obstsorte", new BigDecimal(2));
        automat.addVerkaufsobjekt(obstTorte);
        assertTrue(automat.getVerkaufsobjekte().contains(obstTorte));
    }

    @Test
    void addVerkaufsobjektObstkuchen(){
        automat.addHersteller(testHersteller);
        CakeImpl obstKuchen= new CakeImpl(testHersteller, allergens, 2000, Duration.ofHours(42), "obstsorte", new BigDecimal(3));
        automat.addVerkaufsobjekt(obstKuchen);
        assertTrue(automat.getVerkaufsobjekte().contains(obstKuchen));
    }

    @Test
    void addVerkaufsobjektZweimal(){
        automat.addHersteller(testHersteller);
        CakeImpl obstKuchen= new CakeImpl(testHersteller, allergens, 2000, Duration.ofHours(42), "obstsorte", new BigDecimal(3));
        automat.addVerkaufsobjekt(obstKuchen);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        assertTrue(automat.getVerkaufsobjekte().contains(testVerkaufsobjekt));
        assertTrue(automat.getVerkaufsobjekte().contains(obstKuchen));
    }

    @Test
    void addVerkaufsobjektHerstellerNichtImAutomat(){
        ManufacturerImpl falscherHersteller=new ManufacturerImpl("falscherHersteller");
        allergens=new HashSet<>();
        allergens.add(Allergen.Gluten);
        CakeImpl kuchenMitFalschemHersteller= new CakeImpl(falscherHersteller, allergens, "Kremtest",
                1, Duration.ofHours(220),  new BigDecimal(1));
        assertEquals(VendingMachineErrorCodes.HERSTELLER_ERROR, automat.addVerkaufsobjekt(kuchenMitFalschemHersteller));
    }

    @Test
    void addVerkaufsobjektWennAutomatVoll(){
        VendingMachine vollerAutomat=new VendingMachine(1);
        vollerAutomat.addHersteller(testHersteller);
        vollerAutomat.addVerkaufsobjekt(testVerkaufsobjekt);
        assertEquals(VendingMachineErrorCodes.CAPACITY_ERROR, vollerAutomat.addVerkaufsobjekt(testVerkaufsobjekt));
    }

    @Test
    void deleteVerkaufsobjekt() {
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        automat.deleteVerkaufsobjekt(0);
        assertTrue(!automat.getVerkaufsobjekte().contains(testVerkaufsobjekt));
    }

    @Test
    void doInspection() {
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        Date oldDate= automat.getVerkaufsobjekte().get(0).getInspektionsdatum();
        automat.doInspection(0);
        Date newDate= automat.getVerkaufsobjekte().get(0).getInspektionsdatum();
        assertTrue(oldDate!=newDate);
    }

    @Test
    void listHerstellerWithCakeCount() {
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        Map<String, Integer> herstellerWithCakeCount= automat.listHerstellerWithCakeCount();
        assertTrue(herstellerWithCakeCount.get("TestHersteller").equals(1));
    }

    @Test
    void getVerkaufsobjekte() {
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        assertTrue(automat.getVerkaufsobjekte().get(0)==testVerkaufsobjekt);
    }

    @Test
    void getVerkaufsobjekteOfType() {
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        assertTrue(automat.getVerkaufsobjekteOfType("Kremkuchen").get(0).getKuchentyp().equals("Kremkuchen"));
    }

    @Test
    void getAllergeneThatExistInAutomat() {
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        assertTrue(automat.getAllergene(true).equals(allergens));
    }

    @Test
    void getAllergeneThatDontExistInAutomat() {
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        HashSet<Allergen> allergensNotInAutomat= new HashSet<>();
        allergensNotInAutomat.add(Allergen.Haselnuss);
        allergensNotInAutomat.add(Allergen.Erdnuss);
        allergensNotInAutomat.add(Allergen.Sesamsamen);
        assertTrue(automat.getAllergene(false).equals(allergensNotInAutomat));
    }
}