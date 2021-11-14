package JOS;

import domainLogic.automat.Automat;
import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class JOSTest {

    HerstellerImplementierung testHersteller;
    Automat automat;
    KuchenImplementierung testVerkaufsobjekt;

    @BeforeEach
    void setUp(){
        testHersteller=new HerstellerImplementierung("Blueberryland");
        testVerkaufsobjekt= new KuchenImplementierung(testHersteller, new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automat= new Automat(10);
    }

    @Test
    void testConsistencyOfSerialisation() {

        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        Automat.serialize("automatSerialisation1.ser", automat);
        Automat.serialize("automatSerialisation2.ser", automat);
        Automat deserializedAutomat1= Automat.deserialize("automatSerialisation1.ser");
        Automat deserializedAutomat2= Automat.deserialize("automatSerialisation2.ser");
        assertEquals(deserializedAutomat1.toString(), deserializedAutomat2.toString());
        assertEquals(automat.toString(), deserializedAutomat1.toString());
        assertEquals(automat.toString(), deserializedAutomat2.toString());
    }

    @Test
    void testIfDeserializeReturnsAutomat(){
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        Automat.serialize("automatSerialisation.ser", automat);
        Object deserializedAutomat= Automat.deserialize("automatSerialisation.ser");
        assertTrue(deserializedAutomat instanceof Automat);
        assertEquals(automat.toString(), deserializedAutomat.toString());
    }

    @Test
    void testSerializeObjectInputOutput(){
        automat.addHersteller(testHersteller);
        automat.addVerkaufsobjekt(testVerkaufsobjekt);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("1"))) {
            Automat.serialize(oos, automat);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("1"))) {
            Automat deserialized= Automat.deserialize(ois);

            assertEquals(automat.toString(), deserialized.toString());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}