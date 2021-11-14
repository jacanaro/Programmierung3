package simulation;

import domainLogic.automat.HerstellerImplementierung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutomatSimulation1Test {
    AutomatSimulation1 automatSimulation1;
    RandomValues randomValues;

    @BeforeEach
    void setUp(){
        automatSimulation1=new AutomatSimulation1(3);
        automatSimulation1.addHersteller(new HerstellerImplementierung("Blueberryland"));
        automatSimulation1.addHersteller(new HerstellerImplementierung("Gooseberryland"));
        randomValues=new RandomValues(new HerstellerImplementierung("Blueberryland"),
                new HerstellerImplementierung("Gooseberryland"));
    }

    @Test
    void testConstructor(){
        AutomatSimulation1 automatSimulation1= new AutomatSimulation1(2);
        assertEquals(2, automatSimulation1.getCAPACITY());
    }

    @Test
    void testConstructorWithNegativeCapacity(){
        assertThrows(IllegalArgumentException.class, ()
                -> {new AutomatSimulation1(-1);} );
    }

    @Test
    void addVerkaufsobjektWithAutomatHavingEnoughCapacity() {
        assertNull(automatSimulation1.addVerkaufsobjekt(randomValues.getRandomKuchen()));
    }

    @Test
    void testAddVerkaufsobjektNoCapacityAutomat(){
        automatSimulation1.addVerkaufsobjekt(randomValues.getRandomKuchen());
        automatSimulation1.addVerkaufsobjekt(randomValues.getRandomKuchen());
        automatSimulation1.addVerkaufsobjekt(randomValues.getRandomKuchen());
        assertThrows(IllegalStateException.class, ()
                -> {automatSimulation1.addVerkaufsobjekt(randomValues.getRandomKuchen());} );
    }

    @Test
    void deleteVerkaufsobjektRandom() {
        automatSimulation1.addVerkaufsobjekt(randomValues.getRandomKuchen());
        assertTrue(automatSimulation1.deleteVerkaufsobjektRandom());
    }

    @Test
    void deleteVerkaufsobjektRandomWithEmptyAutomat() {
        assertThrows(IllegalStateException.class, ()
                -> {automatSimulation1.deleteVerkaufsobjektRandom();} );
    }
}