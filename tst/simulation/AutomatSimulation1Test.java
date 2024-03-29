package simulation;

import domain_logic.ManufacturerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutomatSimulation1Test {
    VendingMachineSimulation1 automatSimulation1;
    RandomProductGenerator randomProductGenerator;

    @BeforeEach
    void setUp(){
        automatSimulation1=new VendingMachineSimulation1(3);
        automatSimulation1.addManufacturer(new ManufacturerImpl("Blueberryland"));
        automatSimulation1.addManufacturer(new ManufacturerImpl("Gooseberryland"));
        randomProductGenerator =new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                new ManufacturerImpl("Gooseberryland"));
    }

    @Test
    void testConstructor(){
        VendingMachineSimulation1 automatSimulation1= new VendingMachineSimulation1(2);
        assertEquals(2, automatSimulation1.getCAPACITY());
    }

    @Test
    void testConstructorWithNegativeCapacity(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new VendingMachineSimulation1(-1);} );
    }

    @Test
    void addVerkaufsobjektWithAutomatHavingEnoughCapacity() {
        assertNull(automatSimulation1.addProduct(randomProductGenerator.getRandomProduct()));
    }

    @Test
    void testAddVerkaufsobjektNoCapacityAutomat(){
        automatSimulation1.addProduct(randomProductGenerator.getRandomProduct());
        automatSimulation1.addProduct(randomProductGenerator.getRandomProduct());
        automatSimulation1.addProduct(randomProductGenerator.getRandomProduct());
        assertThrows(IllegalStateException.class, ()
                -> {automatSimulation1.addProduct(randomProductGenerator.getRandomProduct());} );
    }

    @Test
    void deleteVerkaufsobjektRandom() {
        automatSimulation1.addProduct(randomProductGenerator.getRandomProduct());
        assertTrue(automatSimulation1.deleteRandomProduct());
    }

    @Test
    void deleteVerkaufsobjektRandomWithEmptyAutomat() {
        assertThrows(IllegalStateException.class, ()
                -> {automatSimulation1.deleteRandomProduct();} );
    }
}