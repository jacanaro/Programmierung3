package ablauf;

import CLI.ConsoleReader;
import domainLogic.automat.Allergen;
import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;
import eventSystem.*;
import observerPattern.AutomatCapacityObserver;
import observerPattern.ObservableAutomat;
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

public class TestConsoleReaderAbläufe {
    private ObservableAutomat automat;
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
        automat = new ObservableAutomat(20);
        inputEventHandler = new InputEventHandler();
        lAddHersteller = new InputEventListenerAddHersteller();
        lListHersteller = new InputEventListenerListHersteller();
        lListProducts = new InputEventListenerListProducts();
        lAddObsttorte= new InputEventListenerAddObsttorte();
        lAddObstkuchen= new InputEventListenerAddObstkuchen();
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
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);
        consoleReader.setHandler(inputEventHandler);
        consoleReader.checkUserInputForModi();
        consoleReader.handleUserInputCommands();
        consoleReader.handleUserInputCommands();
        consoleReader.handleUserInputCommands();

        assertEquals(2, automat.getVerkaufsobjekte().size());

        consoleReader.checkUserInputForModi();
        consoleReader.handleUserInputCommands();

        assertEquals(1, automat.getVerkaufsobjekte().size());

        consoleReader.handleUserInputCommands();

        assertEquals(0, automat.getVerkaufsobjekte().size());
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
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);
        consoleReader.setHandler(inputEventHandler);
        consoleReader.checkUserInputForModi();
        consoleReader.handleUserInputCommands();
        consoleReader.handleUserInputCommands();
        consoleReader.handleUserInputCommands();
        consoleReader.checkUserInputForModi();
        String actualCommandHandled1=consoleReader.handleUserInputCommands();
        String actualCommandHandled2=consoleReader.handleUserInputCommands();

        String expectedCommandHandled1="user command handled: :r kuchen";
        assertEquals(expectedCommandHandled1, actualCommandHandled1);

        String expectedCommandHandled2="user command handled: :r hersteller";
        assertEquals(expectedCommandHandled2, actualCommandHandled2);

        ArrayList<HerstellerImplementierung> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals("hersteller1", herstellerListe.get(0).getName());

        ArrayList<KuchenImplementierung> kuchenListe = new ArrayList<>(automat.getVerkaufsobjekte());
        KuchenImplementierung actualObstTorte=kuchenListe.get(0);
        HashSet<Allergen>allergens= new HashSet<>();
        allergens.add(Allergen.Gluten);
        KuchenImplementierung expectedObstTorte=new KuchenImplementierung("Sahne", herstellerListe.get(0), allergens, 632,
                Duration.ofHours(24), "Apfel", new BigDecimal("7.50"));
        assertEquals(expectedObstTorte.getKuchentyp(), actualObstTorte.getKuchentyp());
        assertEquals(expectedObstTorte.getHersteller().getName(), actualObstTorte.getHersteller().getName());
        assertEquals(expectedObstTorte.getHaltbarkeit(), actualObstTorte.getHaltbarkeit());
        assertEquals(expectedObstTorte.getAllergene(), actualObstTorte.getAllergene());
        assertEquals(expectedObstTorte.getPreis(), actualObstTorte.getPreis());
        assertEquals(expectedObstTorte.getKremsorte(), actualObstTorte.getKremsorte());
        assertEquals(expectedObstTorte.getObstsorte(), actualObstTorte.getObstsorte());

        KuchenImplementierung actualObstKuchen=kuchenListe.get(1);
        KuchenImplementierung expectedObstKuchen=new KuchenImplementierung(herstellerListe.get(0), new HashSet<>(), 200,
                Duration.ofHours(20), "Erdbeere", new BigDecimal("2.50"));
        assertEquals(expectedObstKuchen.getKuchentyp(), actualObstKuchen.getKuchentyp());
        assertEquals(expectedObstKuchen.getHersteller().getName(), actualObstKuchen.getHersteller().getName());
        assertEquals(expectedObstKuchen.getHaltbarkeit(), actualObstKuchen.getHaltbarkeit());
        assertEquals(expectedObstKuchen.getAllergene(), actualObstKuchen.getAllergene());
        assertEquals(expectedObstKuchen.getPreis(), actualObstKuchen.getPreis());
        assertEquals(expectedObstKuchen.getObstsorte(), actualObstKuchen.getObstsorte());
    }

    @Test
    void testAddHerstellerWithWrongModi() {
        String userInput = ":p\nhersteller1";
        Scanner scanner = new Scanner(userInput);
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);
        consoleReader.setHandler(inputEventHandler);

        consoleReader.checkUserInputForModi();
        consoleReader.handleUserInputCommands();

        ArrayList<HerstellerImplementierung> herstellerListe = new ArrayList<>(automat.getHerstellerSet());

        assertTrue(herstellerListe.isEmpty());
    }

    @Test
    void testAddHerstellerWithValidConsoleCommand() {
        String userInput = ":c\nhersteller1";
        Scanner scanner = new Scanner(userInput);
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);
        consoleReader.setHandler(inputEventHandler);

        consoleReader.checkUserInputForModi();
        consoleReader.handleUserInputCommands();

        ArrayList<HerstellerImplementierung> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals("hersteller1", herstellerListe.get(0).getName());
    }

    @Test
    void testListHerstellerWithValidCommand(){
        System.setOut(new PrintStream(outContent));

        automat.addHersteller(new HerstellerImplementierung("h1"));

        String userInput = ":r\nhersteller";
        Scanner scanner = new Scanner(userInput);
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);
        consoleReader.setHandler(inputEventHandler);

        String expected=consoleReader.createStringOfCLIMenu().trim();
        expected=expected+automat.listHerstellerWithCakeCount().toString().trim();

        consoleReader.checkUserInputForModi();
        String actual=outContent.toString().trim();
        outContent.reset();
        consoleReader.handleUserInputCommands();
        actual=actual+outContent.toString().trim();

        assertEquals(expected, actual);

        System.setOut(originalOut);
    }
}
