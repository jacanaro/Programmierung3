package flow;

import CLI.CLI;
import domain_logic.Allergen;
import domain_logic.ManufacturerImpl;
import domain_logic.CakeImpl;
import event_system.*;
import observer_pattern.VendingMachineCapacityObserver;
import observer_pattern.ObservableVendingMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCLIAbläufe {
    private ObservableVendingMachine automat;
    private InputEventHandler inputEventHandler;
    private InputEventListener lAddHersteller;
    private InputEventListener lListHersteller;
    private InputEventListener lListProducts;
    private InputEventListener lAddObsttorte;
    private InputEventListener lAddObstkuchen;
    private InputEventListener lDeleteKuchen;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        automat = new ObservableVendingMachine(20);
        inputEventHandler = new InputEventHandler();
        lAddHersteller = new InputEventListenerAddManufacturer();
        lListHersteller = new InputEventListenerListManufacturer();
        lListProducts = new InputEventListenerListProducts();
        lAddObsttorte= new InputEventListenerAddFruitFlan();
        lAddObstkuchen= new InputEventListenerAddFruitCake();
        lDeleteKuchen= new InputEventListenerDeleteProduct();
        inputEventHandler.add(lListHersteller);
        inputEventHandler.add(lListProducts);
        inputEventHandler.add(lAddHersteller);
        inputEventHandler.add(lAddObsttorte);
        inputEventHandler.add(lAddObstkuchen);
        inputEventHandler.add(lDeleteKuchen);
    }

    @Test
    void useCaseKuchenHinzufuegenUndLöschen(){
        String userInput = ":c\n" +
                "hersteller1\n" +
                "Obsttorte hersteller1 7,50 632 24 Gluten Apfel Sahne\n" +
                "Obstkuchen hersteller1 2,50 200 20 , Erdbeere\n" +
                ":d\n" +
                "0\n"+
                "1";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        CLI.setInputEventHandler(inputEventHandler);
        CLI.printCLIMenuAndValidateFirstUserInputString();
        CLI.handleValidUserInputCommands();
        CLI.handleValidUserInputCommands();
        CLI.handleValidUserInputCommands();

        assertEquals(2, automat.getProducts().size());

        CLI.printCLIMenuAndValidateFirstUserInputString();
        CLI.handleValidUserInputCommands();

        assertEquals(1, automat.getProducts().size());

        CLI.handleValidUserInputCommands();

        assertEquals(0, automat.getProducts().size());
    }

    @Test
    void useCaseHerstellerUndKuchenHinzufuegenUndAbrufen(){
        String userInput = ":c\n" +
                "hersteller1\n" +
                "Obsttorte hersteller1 7,50 632 24 Gluten Apfel Sahne\n" +
                "Obstkuchen hersteller1 2,50 200 20 , Erdbeere\n" +
                ":r\n" +
                "kuchen\n"+
                "hersteller";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        CLI.setInputEventHandler(inputEventHandler);
        CLI.printCLIMenuAndValidateFirstUserInputString();
        CLI.handleValidUserInputCommands();
        CLI.handleValidUserInputCommands();
        CLI.handleValidUserInputCommands();
        CLI.printCLIMenuAndValidateFirstUserInputString();
        String actualCommandHandled1= CLI.handleValidUserInputCommands();
        String actualCommandHandled2= CLI.handleValidUserInputCommands();

        String expectedCommandHandled1="user command handled: :r kuchen";
        assertEquals(expectedCommandHandled1, actualCommandHandled1);

        String expectedCommandHandled2="user command handled: :r hersteller";
        assertEquals(expectedCommandHandled2, actualCommandHandled2);

        ArrayList<ManufacturerImpl> herstellerListe = new ArrayList<>(automat.getManufacturers());
        assertEquals("hersteller1", herstellerListe.get(0).getName());

        ArrayList<CakeImpl> kuchenListe = new ArrayList<>(automat.getProducts());
        CakeImpl actualObstTorte=kuchenListe.get(0);
        HashSet<Allergen>allergens= new HashSet<>();
        allergens.add(Allergen.Gluten);
        CakeImpl expectedObstTorte=new CakeImpl("Sahne", herstellerListe.get(0), allergens, 632,
                Duration.ofHours(24), "Apfel", new BigDecimal("7.50"));
        assertEquals(expectedObstTorte.getTypeOfProduct(), actualObstTorte.getTypeOfProduct());
        assertEquals(expectedObstTorte.getManufacturer().getName(), actualObstTorte.getManufacturer().getName());
        assertEquals(expectedObstTorte.getShelfLive(), actualObstTorte.getShelfLive());
        assertEquals(expectedObstTorte.getAllergens(), actualObstTorte.getAllergens());
        assertEquals(expectedObstTorte.getPrice(), actualObstTorte.getPrice());
        assertEquals(expectedObstTorte.getCreamFlavor(), actualObstTorte.getCreamFlavor());
        assertEquals(expectedObstTorte.getTypeOfFruit(), actualObstTorte.getTypeOfFruit());

        CakeImpl actualObstKuchen=kuchenListe.get(1);
        CakeImpl expectedObstKuchen=new CakeImpl(herstellerListe.get(0), new HashSet<>(), 200,
                Duration.ofHours(20), "Erdbeere", new BigDecimal("2.50"));
        assertEquals(expectedObstKuchen.getTypeOfProduct(), actualObstKuchen.getTypeOfProduct());
        assertEquals(expectedObstKuchen.getManufacturer().getName(), actualObstKuchen.getManufacturer().getName());
        assertEquals(expectedObstKuchen.getShelfLive(), actualObstKuchen.getShelfLive());
        assertEquals(expectedObstKuchen.getAllergens(), actualObstKuchen.getAllergens());
        assertEquals(expectedObstKuchen.getPrice(), actualObstKuchen.getPrice());
        assertEquals(expectedObstKuchen.getTypeOfFruit(), actualObstKuchen.getTypeOfFruit());
    }

    @Test
    void testAddHerstellerWithWrongModi() {
        String userInput = ":p\nhersteller1";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        CLI.setInputEventHandler(inputEventHandler);

        CLI.printCLIMenuAndValidateFirstUserInputString();
        CLI.handleValidUserInputCommands();

        ArrayList<ManufacturerImpl> herstellerListe = new ArrayList<>(automat.getManufacturers());

        assertTrue(herstellerListe.isEmpty());
    }

    @Test
    void testAddHerstellerWithValidConsoleCommand() {
        String userInput = ":c\nhersteller1";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        CLI.setInputEventHandler(inputEventHandler);

        CLI.printCLIMenuAndValidateFirstUserInputString();
        CLI.handleValidUserInputCommands();

        ArrayList<ManufacturerImpl> herstellerListe = new ArrayList<>(automat.getManufacturers());
        assertEquals("hersteller1", herstellerListe.get(0).getName());
    }

    @Test
    void testListHerstellerWithValidCommand(){
        System.setOut(new PrintStream(outContent));

        automat.addManufacturer(new ManufacturerImpl("h1"));

        String userInput = ":r\nhersteller";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        CLI.setInputEventHandler(inputEventHandler);

        String expected= CLI.createStringOfCLIMenu().trim();
        expected=expected+automat.listManufacturersWithProductsCounted().toString().trim();

        CLI.printCLIMenuAndValidateFirstUserInputString();
        String actual=outContent.toString().trim();
        outContent.reset();
        CLI.handleValidUserInputCommands();
        actual=actual+outContent.toString().trim();

        assertEquals(expected, actual);

        System.setOut(originalOut);
    }
}
