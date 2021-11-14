package eventSystem;
import CLI.ConsoleReader;
import observerPattern.AutomatAllergeneObserver;
import observerPattern.AutomatCapacityObserver;
import observerPattern.ObservableAutomat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventConsoleReaderTest {
    private ObservableAutomat automat;

    @BeforeEach
    void setUp() {
        automat = new ObservableAutomat(20);
    }

    @Test
    void testConsoleReaderConstructor1(){
        AutomatCapacityObserver capacityObserver= new AutomatCapacityObserver(automat);
        Scanner scanner= new Scanner("bla");
        ConsoleReader consoleReader= new ConsoleReader(automat, capacityObserver, scanner);
        assertEquals(automat, consoleReader.getObservableAutomat());
        assertEquals(capacityObserver, consoleReader.getCapacityObserver());
        assertEquals(scanner, consoleReader.getScanner());
    }

    @Test
    void testConsoleReaderConstructor2(){
        AutomatAllergeneObserver allergeneObserver= new AutomatAllergeneObserver(automat);
        AutomatCapacityObserver capacityObserver= new AutomatCapacityObserver(automat);
        Scanner scanner= new Scanner("bla");
        ConsoleReader consoleReader= new ConsoleReader(automat, allergeneObserver, capacityObserver, scanner);
        assertEquals(automat, consoleReader.getObservableAutomat());
        assertEquals(allergeneObserver, consoleReader.getAllergeneObserver());
        assertEquals(capacityObserver, consoleReader.getCapacityObserver());
        assertEquals(scanner, consoleReader.getScanner());
    }

    @Test
    void checkIfConsoleReaderRecognizesModiHappyPath() {
        String userInput = ":c";
        Scanner scanner = new Scanner(userInput);
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);
        assertEquals(true, consoleReader.checkUserInputForModi());
    }

    @Test
    void  checkIfConsoleReaderRecognizesModiWithoutValidInput() {
        String userInput = "sdfacEgb348745g!";
        Scanner scanner = new Scanner(userInput);
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);
        assertEquals(false, consoleReader.checkUserInputForModi());
    }

    @Test
    void testHandleUserInputCommandsMethodWithValidCommand() {
        String userInput = ":c\nhersteller1";
        Scanner scanner = new Scanner(userInput);
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);
        InputEventHandler mockedHandler = mock(InputEventHandler.class);
        consoleReader.setHandler(mockedHandler);

        consoleReader.checkUserInputForModi();
        assertEquals("user command handled: :c hersteller1", consoleReader.handleUserInputCommands());
    }

    @Test
    void testHandleUserInputCommandsMethodWithModiSwap() {
        String userInput = ":c\n:d";
        Scanner scanner = new Scanner(userInput);
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);
        InputEventHandler mockedHandler = mock(InputEventHandler.class);
        consoleReader.setHandler(mockedHandler);

        consoleReader.checkUserInputForModi();
        assertEquals(":d", consoleReader.handleUserInputCommands());
    }

    @Test
    void testCreateStringOfCLIMenu() {
        String userInput = ":c\nhersteller1";
        Scanner scanner = new Scanner(userInput);
        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        ConsoleReader consoleReader = new ConsoleReader(automat, capacityObserver, scanner);

        assertTrue(consoleReader.createStringOfCLIMenu() instanceof String);
    }
}