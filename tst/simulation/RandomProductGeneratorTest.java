package simulation;

import domain_logic.ManufacturerImpl;
import domain_logic.CakeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomProductGeneratorTest {
    RandomProductGenerator randomProductGenerator;
    @BeforeEach
    void setUp(){
        randomProductGenerator =new RandomProductGenerator(new ManufacturerImpl("Blueberryland"),
                new ManufacturerImpl("Gooseberryland"));
    }

    @Test
    void testConstructor(){
        ManufacturerImpl bla= new ManufacturerImpl("bla");
        ManufacturerImpl bla2= new ManufacturerImpl("bla2");
        RandomProductGenerator randomProductGenerator = new RandomProductGenerator(bla, bla2);
        assertEquals(bla, randomProductGenerator.getHersteller1());
        assertEquals(bla2, randomProductGenerator.getHersteller2());
    }

    @Test
    void testIfReturnValueEqualsNull() {
        assertNotNull(randomProductGenerator.getRandomKuchen());
    }

    @Test
    void testReturnType() {
        CakeImpl randomKuchen= randomProductGenerator.getRandomKuchen();
        assertTrue(randomKuchen instanceof CakeImpl);
    }

    @Test
    void getTwoDifferentRandomKuchen(){
        CakeImpl randomKuchen1= randomProductGenerator.getRandomKuchen();
        CakeImpl randomKuchen2= randomProductGenerator.getRandomKuchen();
        assertNotEquals(randomKuchen2, randomKuchen1);
    }
}