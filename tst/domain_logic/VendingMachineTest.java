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
        automat.addManufacturer(testHersteller);
        assertTrue(automat.getManufacturers().contains(testHersteller));
    }

    @Test
    void addHerstellerDoppelt(){
        automat.addManufacturer(testHersteller);
        automat.addManufacturer(testHersteller);

        ArrayList<ManufacturerImpl> herstellerListe= new ArrayList<>(automat.getManufacturers());

        assertEquals(1, herstellerListe.size());
    }

    @Test
    void addHerstellerMehrereUnterschiedliche(){
        automat.addManufacturer(testHersteller);
        automat.addManufacturer(new ManufacturerImpl("andererHersteller"));

        ArrayList<ManufacturerImpl> herstellerListe= new ArrayList<>(automat.getManufacturers());

        assertEquals(2, herstellerListe.size());
    }

    @Test
    void deleteOneExistingHersteller() {
        automat.addManufacturer(testHersteller);
        automat.deleteManufacturer("TestHersteller");
        assertFalse(automat.getManufacturers().contains(testHersteller));
    }

    @Test
    void deleteOneExistingHerstellerDoppelt() {
        automat.addManufacturer(testHersteller);
        automat.deleteManufacturer("TestHersteller");
        automat.deleteManufacturer("TestHersteller");
        assertFalse(automat.getManufacturers().contains(testHersteller));
    }

    @Test
    void deleteOneOutOfThreeHersteller() {
        automat.addManufacturer(testHersteller);
        automat.addManufacturer(new ManufacturerImpl("andererHersteller"));
        automat.addManufacturer(new ManufacturerImpl("andererHersteller2222"));
        automat.deleteManufacturer("TestHersteller");
        assertFalse(automat.getManufacturers().contains(testHersteller));
    }

    @Test
    void deleteTheRightOneOutOfTwoHersteller() {
        automat.addManufacturer(testHersteller);
        automat.addManufacturer(new ManufacturerImpl("andererHersteller"));
        automat.deleteManufacturer("andererHersteller");
        assertTrue(automat.getManufacturers().contains(testHersteller));
    }


    @Test
    void deleteNotExistingHersteller() {
        automat.addManufacturer(testHersteller);
        automat.deleteManufacturer("NotExistingHersteller");
        assertTrue(automat.getManufacturers().contains(testHersteller));
    }

    @Test
    void addVerkaufsobjektKremkuchen() {
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        assertTrue(automat.getProducts().contains(testVerkaufsobjekt));
    }

    @Test
    void addVerkaufsobjektObsttorte(){
        automat.addManufacturer(testHersteller);
        CakeImpl obstTorte= new CakeImpl("kreme", testHersteller, allergens, 3000, Duration.ofHours(42), "obstsorte", new BigDecimal(2));
        automat.addProduct(obstTorte);
        assertTrue(automat.getProducts().contains(obstTorte));
    }

    @Test
    void addVerkaufsobjektObstkuchen(){
        automat.addManufacturer(testHersteller);
        CakeImpl obstKuchen= new CakeImpl(testHersteller, allergens, 2000, Duration.ofHours(42), "obstsorte", new BigDecimal(3));
        automat.addProduct(obstKuchen);
        assertTrue(automat.getProducts().contains(obstKuchen));
    }

    @Test
    void addVerkaufsobjektZweimal(){
        automat.addManufacturer(testHersteller);
        CakeImpl obstKuchen= new CakeImpl(testHersteller, allergens, 2000, Duration.ofHours(42), "obstsorte", new BigDecimal(3));
        automat.addProduct(obstKuchen);
        automat.addProduct(testVerkaufsobjekt);
        assertTrue(automat.getProducts().contains(testVerkaufsobjekt));
        assertTrue(automat.getProducts().contains(obstKuchen));
    }

    @Test
    void addVerkaufsobjektHerstellerNichtImAutomat(){
        ManufacturerImpl falscherHersteller=new ManufacturerImpl("falscherHersteller");
        allergens=new HashSet<>();
        allergens.add(Allergen.Gluten);
        CakeImpl kuchenMitFalschemHersteller= new CakeImpl(falscherHersteller, allergens, "Kremtest",
                1, Duration.ofHours(220),  new BigDecimal(1));
        assertEquals(VendingMachineErrorCodes.MANUFACTURER_ERROR, automat.addProduct(kuchenMitFalschemHersteller));
    }

    @Test
    void addVerkaufsobjektWennAutomatVoll(){
        VendingMachine vollerAutomat=new VendingMachine(1);
        vollerAutomat.addManufacturer(testHersteller);
        vollerAutomat.addProduct(testVerkaufsobjekt);
        assertEquals(VendingMachineErrorCodes.CAPACITY_ERROR, vollerAutomat.addProduct(testVerkaufsobjekt));
    }

    @Test
    void deleteVerkaufsobjekt() {
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        automat.deleteProduct(0);
        assertTrue(!automat.getProducts().contains(testVerkaufsobjekt));
    }

    @Test
    void doInspection() {
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        Date oldDate= automat.getProducts().get(0).getDateOfInspection();
        automat.doInspection(0);
        Date newDate= automat.getProducts().get(0).getDateOfInspection();
        assertTrue(oldDate!=newDate);
    }

    @Test
    void listHerstellerWithCakeCount() {
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        Map<String, Integer> herstellerWithCakeCount= automat.listManufacturersWithProductsCounted();
        assertTrue(herstellerWithCakeCount.get("TestHersteller").equals(1));
    }

    @Test
    void getVerkaufsobjekte() {
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        assertTrue(automat.getProducts().get(0)==testVerkaufsobjekt);
    }

    @Test
    void getVerkaufsobjekteOfType() {
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        assertTrue(automat.getProductsByType("Kremkuchen").get(0).getTypeOfProduct().equals("Kremkuchen"));
    }

    @Test
    void getAllergeneThatExistInAutomat() {
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        assertTrue(automat.getAllergens(true).equals(allergens));
    }

    @Test
    void getAllergeneThatDontExistInAutomat() {
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        HashSet<Allergen> allergensNotInAutomat= new HashSet<>();
        allergensNotInAutomat.add(Allergen.Haselnuss);
        allergensNotInAutomat.add(Allergen.Erdnuss);
        allergensNotInAutomat.add(Allergen.Sesamsamen);
        assertTrue(automat.getAllergens(false).equals(allergensNotInAutomat));
    }
}