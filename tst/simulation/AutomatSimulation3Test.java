package simulation;
import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AutomatSimulation3Test {
    AutomatSimulation3 automatSimulation3;
    RandomValues randomValues;

    @BeforeEach
    void setUp(){
        automatSimulation3= new AutomatSimulation3(5);
        automatSimulation3.addHersteller(new HerstellerImplementierung("Blueberryland"));
        automatSimulation3.addHersteller(new HerstellerImplementierung("Gooseberryland"));
        randomValues= new RandomValues(new HerstellerImplementierung("Blueberryland"),
                new HerstellerImplementierung("Gooseberryland"));
    }

    @Test
    void testConstructor(){
        AutomatSimulation3 automatSimulation3= new AutomatSimulation3(2);
        assertEquals(2, automatSimulation3.getCAPACITY());
    }

    @Test
    void testConstructorWithNegativeCapacity(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new AutomatSimulation3(-1);} );
    }
    @Test
    void deleteAtLeastOneWithOneItemInAutomat() {
        KuchenImplementierung alterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertEquals(0, automatSimulation3.getVerkaufsobjekte().size());
    }

    @Test
    void deleteAtLeastOneWithTwoItemsInAutomat() {
        KuchenImplementierung alterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung jungerKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.addVerkaufsobjekt(jungerKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertNotEquals(2, automatSimulation3.getVerkaufsobjekte().size());
    }

    @Test
    void deleteAtLeastTheOldestItemWithTwoItemsInAutomat() {
        KuchenImplementierung alterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung jungerKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.addVerkaufsobjekt(jungerKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertFalse(automatSimulation3.getVerkaufsobjekte().contains(alterKuchen));
    }

    @Test
    void deleteAtLeastOneWithThreeItemsInAutomat() {
        KuchenImplementierung alterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung jungerKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung juengsterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.addVerkaufsobjekt(jungerKuchen);
        automatSimulation3.addVerkaufsobjekt(juengsterKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertNotEquals(3,automatSimulation3.getVerkaufsobjekte().size());
    }

    @Test
    void deleteAtLeastTheOldestItemWithThreeItemsInAutomat() {
        KuchenImplementierung alterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung jungerKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.addVerkaufsobjekt(jungerKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertFalse(automatSimulation3.getVerkaufsobjekte().contains(alterKuchen));
    }

    @Test
    void testIfLockGetsUnlockedAfterDeleteVerkaufsobjektWithOldestDateXTimes(){
        automatSimulation3.addVerkaufsobjekt(randomValues.getRandomKuchen());
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertTrue(automatSimulation3.getLock().tryLock());
    }
}