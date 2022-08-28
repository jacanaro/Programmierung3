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
        automatSimulation3.addManufacturer(new ManufacturerImpl("Blueberryland"));
        automatSimulation3.addManufacturer(new ManufacturerImpl("Gooseberryland"));
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
        automatSimulation3.addProduct(alterKuchen);
        automatSimulation3.deleteRandomAmountOfOldestProducts();
        assertEquals(0, automatSimulation3.getProducts().size());
    }

    @Test
    void deleteAtLeastOneWithTwoItemsInAutomat() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addProduct(alterKuchen);
        automatSimulation3.addProduct(jungerKuchen);
        automatSimulation3.deleteRandomAmountOfOldestProducts();
        assertNotEquals(2, automatSimulation3.getProducts().size());
    }

    @Test
    void deleteAtLeastTheOldestItemWithTwoItemsInAutomat() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addProduct(alterKuchen);
        automatSimulation3.addProduct(jungerKuchen);
        automatSimulation3.deleteRandomAmountOfOldestProducts();
        assertFalse(automatSimulation3.getProducts().contains(alterKuchen));
    }

    @Test
    void deleteAtLeastOneWithThreeItemsInAutomat() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl juengsterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addProduct(alterKuchen);
        automatSimulation3.addProduct(jungerKuchen);
        automatSimulation3.addProduct(juengsterKuchen);
        automatSimulation3.deleteRandomAmountOfOldestProducts();
        assertNotEquals(3,automatSimulation3.getProducts().size());
    }

    @Test
    void deleteAtLeastTheOldestItemWithThreeItemsInAutomat() {
        CakeImpl alterKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        CakeImpl jungerKuchen=new CakeImpl(new ManufacturerImpl("Gooseberryland"), new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automatSimulation3.addProduct(alterKuchen);
        automatSimulation3.addProduct(jungerKuchen);
        automatSimulation3.deleteRandomAmountOfOldestProducts();
        assertFalse(automatSimulation3.getProducts().contains(alterKuchen));
    }

    @Test
    void testIfLockGetsUnlockedAfterDeleteVerkaufsobjektWithOldestDateXTimes(){
        automatSimulation3.addProduct(randomProductGenerator.getRandomProduct());
        automatSimulation3.deleteRandomAmountOfOldestProducts();
        assertTrue(automatSimulation3.getLock().tryLock());
    }
}