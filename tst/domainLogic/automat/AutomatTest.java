package domainLogic.automat;

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

class AutomatTest {
    private Automat automat;
    private HerstellerImplementierung testHersteller;
    private HashSet<Allergen> allergens;
    private KuchenImplementierung testVerkaufsobjekt;

    @BeforeEach
    public void setUp(){
        automat= new Automat(20);
        testHersteller=new HerstellerImplementierung("TestHersteller");
        allergens=new HashSet<>();
        allergens.add(Allergen.Gluten);
        testVerkaufsobjekt= new KuchenImplementierung(testHersteller, allergens, "Kremtest",
                1, Duration.ofHours(220),  new BigDecimal(1));
    }

    @Test
    void testConstructor(){
        Automat automat= new Automat(2);
        assertEquals(2, automat.getCAPACITY());
    }

    @Test
    void createAutomatWithNegativCapacityValue(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new Automat(-1);} );
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

        ArrayList<HerstellerImplementierung> herstellerListe= new ArrayList<>(automat.getHerstellerSet());

        assertEquals(1, herstellerListe.size());
    }

    @Test
    void addHerstellerMehrereUnterschiedliche(){
        automat.addHersteller(testHersteller);
        automat.addHersteller(new HerstellerImplementierung("andererHersteller"));

        ArrayList<HerstellerImplementierung> herstellerListe= new ArrayList<>(automat.getHerstellerSet());

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
        automat.addHersteller(new HerstellerImplementierung("andererHersteller"));
        automat.addHersteller(new HerstellerImplementierung("andererHersteller2222"));
        automat.deleteHersteller("TestHersteller");
        assertFalse(automat.getHerstellerSet().contains(testHersteller));
    }

    @Test
    void deleteTheRightOneOutOfTwoHersteller() {
        automat.addHersteller(testHersteller);
        automat.addHersteller(new HerstellerImplementierung("andererHersteller"));
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
        KuchenImplementierung obstTorte= new KuchenImplementierung("kreme", testHersteller, allergens, 3000, Duration.ofHours(42), "obstsorte", new BigDecimal(2));
        automat.addVerkaufsobjekt(obstTorte);
        assertTrue(automat.getVerkaufsobjekte().contains(obstTorte));
    }

    @Test
    void addVerkaufsobjektObstkuchen(){
        automat.addHersteller(testHersteller);
        KuchenImplementierung obstKuchen= new KuchenImplementierung(testHersteller, allergens, 2000, Duration.ofHours(42), "obstsorte", new BigDecimal(3));
        automat.addVerkaufsobjekt(obstKuchen);
        assertTrue(automat.getVerkaufsobjekte().contains(obstKuchen));
    }

    @Test
    void addVerkaufsobjektZweimal(){
        automat.addHersteller(testHersteller);
        KuchenImplementierung obstKuchen= new KuchenImplementierung(testHersteller, allergens, 2000, Duration.ofHours(42), "obstsorte", new BigDecimal(3));
        automat.addVerkaufsobjekt(obstKuchen);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        assertTrue(automat.getVerkaufsobjekte().contains(testVerkaufsobjekt));
        assertTrue(automat.getVerkaufsobjekte().contains(obstKuchen));
    }

    @Test
    void addVerkaufsobjektHerstellerNichtImAutomat(){
        HerstellerImplementierung falscherHersteller=new HerstellerImplementierung("falscherHersteller");
        allergens=new HashSet<>();
        allergens.add(Allergen.Gluten);
        KuchenImplementierung kuchenMitFalschemHersteller= new KuchenImplementierung(falscherHersteller, allergens, "Kremtest",
                1, Duration.ofHours(220),  new BigDecimal(1));
        assertEquals(AutomatErrorCodes.HERSTELLER_ERROR, automat.addVerkaufsobjekt(kuchenMitFalschemHersteller));
    }

    @Test
    void addVerkaufsobjektWennAutomatVoll(){
        Automat vollerAutomat=new Automat(1);
        vollerAutomat.addHersteller(testHersteller);
        vollerAutomat.addVerkaufsobjekt(testVerkaufsobjekt);
        assertEquals(AutomatErrorCodes.CAPACITY_ERROR, vollerAutomat.addVerkaufsobjekt(testVerkaufsobjekt));
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