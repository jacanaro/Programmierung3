package simulation;

import domain_logic.ManufacturerImpl;
import domain_logic.CakeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AutomatSimulation2Test {
    VendingMachineSimulation2 automatSimulation2;
    RandomProductGenerator randomProductGenerator;

    @BeforeEach
    void setUp(){
        automatSimulation2=new VendingMachineSimulation2(3);
        automatSimulation2.addManufacturer(new ManufacturerImpl("Blueberryland"));
        automatSimulation2.addManufacturer(new ManufacturerImpl("Gooseberryland"));
        randomProductGenerator =new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                new ManufacturerImpl("Gooseberryland"));
    }

    @Test
    void testConstructor(){
        VendingMachineSimulation2 automatSimulation2= new VendingMachineSimulation2(2);
        assertEquals(2, automatSimulation2.getCAPACITY());
    }

    @Test
    void testConstructorWithNegativeCapacity(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new VendingMachineSimulation2(-1);} );
    }

    @Test
    void addVerkaufsobjektHappyPath() {
        assertNull(automatSimulation2.addProduct(randomProductGenerator.getRandomProduct()));
    }

    @Test
    void testIfLockGetsUnlockedAfterAddVerkaufsobjekt(){
        automatSimulation2.addProduct(randomProductGenerator.getRandomProduct());
        assertTrue(automatSimulation2.getLock().tryLock());
    }

    @Test
    void deleteVerkaufsobjektWithOldestDateOneItem() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation2.addProduct(alterKuchen);
        automatSimulation2.deleteProductWithOldestInspectionDate();
        assertFalse(automatSimulation2.getProducts().contains(alterKuchen));
    }

    @Test
    void deleteVerkaufsobjektWithOldestDateTwoItems() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation2.addProduct(alterKuchen);
        automatSimulation2.addProduct(jungerKuchen);
        automatSimulation2.deleteProductWithOldestInspectionDate();
        assertEquals(jungerKuchen, automatSimulation2.getProducts().get(0));
    }

    @Test
    void deleteVerkaufsobjektWithOldestDateThreeItems() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl juengsterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation2.addProduct(alterKuchen);
        automatSimulation2.addProduct(jungerKuchen);
        automatSimulation2.addProduct(juengsterKuchen);
        automatSimulation2.deleteProductWithOldestInspectionDate();
        assertFalse(automatSimulation2.getProducts().contains(alterKuchen));
    }

    @Test
    void testIfLockGetsUnlockedAfterDeleteVerkaufsobjektWithOldestDate(){
        automatSimulation2.addProduct(randomProductGenerator.getRandomProduct());
        automatSimulation2.deleteProductWithOldestInspectionDate();
        assertTrue(automatSimulation2.getLock().tryLock());
    }

    @Test
    void doRandomInspectionOneItem() {
        automatSimulation2.addProduct(randomProductGenerator.getRandomProduct());
        Date oldDate= automatSimulation2.getProducts().get(0).getDateOfInspection();
        automatSimulation2.doRandomInspection();
        Date newDate= automatSimulation2.getProducts().get(0).getDateOfInspection();
        assertTrue(oldDate!=newDate);
    }

    @Test
    void doRandomInspectionThreeItems() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl juengsterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation2.addProduct(alterKuchen);
        automatSimulation2.addProduct(jungerKuchen);
        automatSimulation2.addProduct(juengsterKuchen);

        //Wenn an dieser Stelle eine Inspektion durchgeführt wird,
        //muss der betroffene Kuchen danach das aktuellste/juengste Inspektionsdatum besitzen

        CakeImpl betroffenerKuchen = automatSimulation2.doRandomInspection();
        assertFalse(betroffenerKuchen.getDateOfInspection().before(juengsterKuchen.getDateOfInspection()));
    }

}