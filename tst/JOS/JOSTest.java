package JOS;

import domain_logic.VendingMachine;
import domain_logic.ManufacturerImpl;
import domain_logic.CakeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class JOSTest {

    ManufacturerImpl testHersteller;
    VendingMachine automat;
    CakeImpl testVerkaufsobjekt;

    @BeforeEach
    void setUp(){
        testHersteller=new ManufacturerImpl("Blueberryland");
        testVerkaufsobjekt= new CakeImpl(testHersteller, new HashSet<>(),
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27"));
        automat= new VendingMachine(10);
    }

    @Test
    void testConsistencyOfSerialisation() {

        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        VendingMachine.serializeVendingMachine("automatSerialisation1.ser", automat);
        VendingMachine.serializeVendingMachine("automatSerialisation2.ser", automat);
        VendingMachine deserializedAutomat1= VendingMachine.deserializeVendingMachine("automatSerialisation1.ser");
        VendingMachine deserializedAutomat2= VendingMachine.deserializeVendingMachine("automatSerialisation2.ser");
        assertEquals(deserializedAutomat1.toString(), deserializedAutomat2.toString());
        assertEquals(automat.toString(), deserializedAutomat1.toString());
        assertEquals(automat.toString(), deserializedAutomat2.toString());
    }

    @Test
    void testIfDeserializeReturnsAutomat(){
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        VendingMachine.serializeVendingMachine("automatSerialisation.ser", automat);
        Object deserializedAutomat= VendingMachine.deserializeVendingMachine("automatSerialisation.ser");
        assertTrue(deserializedAutomat instanceof VendingMachine);
        assertEquals(automat.toString(), deserializedAutomat.toString());
    }

    @Test
    void testSerializeObjectInputOutput(){
        automat.addManufacturer(testHersteller);
        automat.addProduct(testVerkaufsobjekt);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("1"))) {
            VendingMachine.serializeVendingMachine(oos, automat);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("1"))) {
            VendingMachine deserialized= VendingMachine.deserializeVendingMachine(ois);

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