package simulation;
import domain_logic.ManufacturerImpl;
import domain_logic.CakeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AutomatSimulation3Test {
    VendingMachineSimulation3 automatSimulation3;
    RandomProductGenerator randomProductGenerator;

    @BeforeEach
    void setUp(){
        automatSimulation3= new VendingMachineSimulation3(5);
        automatSimulation3.addHersteller(new ManufacturerImpl("Blueberryland"));
        automatSimulation3.addHersteller(new ManufacturerImpl("Gooseberryland"));
        randomProductGenerator = new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                new ManufacturerImpl("Gooseberryland"));
    }

    @Test
    void testConstructor(){
        VendingMachineSimulation3 automatSimulation3= new VendingMachineSimulation3(2);
        assertEquals(2, automatSimulation3.getCAPACITY());
    }

    @Test
    void testConstructorWithNegativeCapacity(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new VendingMachineSimulation3(-1);} );
    }
    @Test
    void deleteAtLeastOneWithOneItemInAutomat() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertEquals(0, automatSimulation3.getVerkaufsobjekte().size());
    }

    @Test
    void deleteAtLeastOneWithTwoItemsInAutomat() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.addVerkaufsobjekt(jungerKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertNotEquals(2, automatSimulation3.getVerkaufsobjekte().size());
    }

    @Test
    void deleteAtLeastTheOldestItemWithTwoItemsInAutomat() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.addVerkaufsobjekt(jungerKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertFalse(automatSimulation3.getVerkaufsobjekte().contains(alterKuchen));
    }

    @Test
    void deleteAtLeastOneWithThreeItemsInAutomat() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl juengsterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.addVerkaufsobjekt(jungerKuchen);
        automatSimulation3.addVerkaufsobjekt(juengsterKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertNotEquals(3,automatSimulation3.getVerkaufsobjekte().size());
    }

    @Test
    void deleteAtLeastTheOldestItemWithThreeItemsInAutomat() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addVerkaufsobjekt(alterKuchen);
        automatSimulation3.addVerkaufsobjekt(jungerKuchen);
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertFalse(automatSimulation3.getVerkaufsobjekte().contains(alterKuchen));
    }

    @Test
    void testIfLockGetsUnlockedAfterDeleteVerkaufsobjektWithOldestDateXTimes(){
        automatSimulation3.addVerkaufsobjekt(randomProductGenerator.getRandomKuchen());
        automatSimulation3.deleteVerkaufsobjekteWithOldestDateXTimes();
        assertTrue(automatSimulation3.getLock().tryLock());
    }
}