package simulation;

import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AutomatSimulation2Test {
    AutomatSimulation2 automatSimulation2;
    RandomValues randomValues;

    @BeforeEach
    void setUp(){
        automatSimulation2=new AutomatSimulation2(3);
        automatSimulation2.addHersteller(new HerstellerImplementierung("Blueberryland"));
        automatSimulation2.addHersteller(new HerstellerImplementierung("Gooseberryland"));
        randomValues=new RandomValues(new HerstellerImplementierung("Blueberryland"),
                new HerstellerImplementierung("Gooseberryland"));
    }

    @Test
    void testConstructor(){
        AutomatSimulation2 automatSimulation2= new AutomatSimulation2(2);
        assertEquals(2, automatSimulation2.getCAPACITY());
    }

    @Test
    void testConstructorWithNegativeCapacity(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new AutomatSimulation2(-1);} );
    }

    @Test
    void addVerkaufsobjektHappyPath() {
        assertNull(automatSimulation2.addVerkaufsobjekt(randomValues.getRandomKuchen()));
    }

    @Test
    void testIfLockGetsUnlockedAfterAddVerkaufsobjekt(){
        automatSimulation2.addVerkaufsobjekt(randomValues.getRandomKuchen());
        assertTrue(automatSimulation2.getLock().tryLock());
    }

    @Test
    void deleteVerkaufsobjektWithOldestDateOneItem() {
        KuchenImplementierung alterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation2.addVerkaufsobjekt(alterKuchen);
        automatSimulation2.deleteVerkaufsobjektWithOldestDate();
        assertFalse(automatSimulation2.getVerkaufsobjekte().contains(alterKuchen));
    }

    @Test
    void deleteVerkaufsobjektWithOldestDateTwoItems() {
        KuchenImplementierung alterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung jungerKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation2.addVerkaufsobjekt(alterKuchen);
        automatSimulation2.addVerkaufsobjekt(jungerKuchen);
        automatSimulation2.deleteVerkaufsobjektWithOldestDate();
        assertEquals(jungerKuchen, automatSimulation2.getVerkaufsobjekte().get(0));
    }

    @Test
    void deleteVerkaufsobjektWithOldestDateThreeItems() {
        KuchenImplementierung alterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung jungerKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung juengsterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation2.addVerkaufsobjekt(alterKuchen);
        automatSimulation2.addVerkaufsobjekt(jungerKuchen);
        automatSimulation2.addVerkaufsobjekt(juengsterKuchen);
        automatSimulation2.deleteVerkaufsobjektWithOldestDate();
        assertFalse(automatSimulation2.getVerkaufsobjekte().contains(alterKuchen));
    }

    @Test
    void testIfLockGetsUnlockedAfterDeleteVerkaufsobjektWithOldestDate(){
        automatSimulation2.addVerkaufsobjekt(randomValues.getRandomKuchen());
        automatSimulation2.deleteVerkaufsobjektWithOldestDate();
        assertTrue(automatSimulation2.getLock().tryLock());
    }

    @Test
    void doRandomInspectionOneItem() {
        automatSimulation2.addVerkaufsobjekt(randomValues.getRandomKuchen());
        Date oldDate= automatSimulation2.getVerkaufsobjekte().get(0).getInspektionsdatum();
        automatSimulation2.doRandomInspection();
        Date newDate= automatSimulation2.getVerkaufsobjekte().get(0).getInspektionsdatum();
        assertTrue(oldDate!=newDate);
    }

    @Test
    void doRandomInspectionThreeItems() {
        KuchenImplementierung alterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung jungerKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        KuchenImplementierung juengsterKuchen=new KuchenImplementierung(new HerstellerImplementierung("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation2.addVerkaufsobjekt(alterKuchen);
        automatSimulation2.addVerkaufsobjekt(jungerKuchen);
        automatSimulation2.addVerkaufsobjekt(juengsterKuchen);

        //Wenn an dieser Stelle eine Inspektion durchgeführt wird,
        //muss der betroffene Kuchen danach das aktuellste/juengste Inspektionsdatum besitzen

        KuchenImplementierung betroffenerKuchen = automatSimulation2.doRandomInspection();
        assertFalse(betroffenerKuchen.getInspektionsdatum().before(juengsterKuchen.getInspektionsdatum()));
    }

}