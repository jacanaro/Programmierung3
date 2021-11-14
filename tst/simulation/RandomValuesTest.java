package simulation;

import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomValuesTest {
    RandomValues randomValues;
    @BeforeEach
    void setUp(){
        randomValues=new RandomValues(new HerstellerImplementierung("Blueberryland"),
                new HerstellerImplementierung("Gooseberryland"));
    }

    @Test
    void testConstructor(){
        HerstellerImplementierung bla= new HerstellerImplementierung("bla");
        HerstellerImplementierung bla2= new HerstellerImplementierung("bla2");
        RandomValues randomValues= new RandomValues(bla, bla2);
        assertEquals(bla, randomValues.getHersteller1());
        assertEquals(bla2, randomValues.getHersteller2());
    }

    @Test
    void testIfReturnValueEqualsNull() {
        assertNotNull(randomValues.getRandomKuchen());
    }

    @Test
    void testReturnType() {
        KuchenImplementierung randomKuchen=randomValues.getRandomKuchen();
        assertTrue(randomKuchen instanceof KuchenImplementierung);
    }

    @Test
    void getTwoDifferentRandomKuchen(){
        KuchenImplementierung randomKuchen1=randomValues.getRandomKuchen();
        KuchenImplementierung randomKuchen2=randomValues.getRandomKuchen();
        assertNotEquals(randomKuchen2, randomKuchen1);
    }
}