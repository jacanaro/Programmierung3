package event_system;
import CLI.CLI;
import observer_pattern.VendingMachineAllergensObserver;
import observer_pattern.VendingMachineCapacityObserver;
import observer_pattern.ObservableVendingMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventCLITest {
    private ObservableVendingMachine automat;

    @BeforeEach
    void setUp() {
        automat = new ObservableVendingMachine(20);
    }

    @Test
    void testConsoleReaderConstructor1(){
        VendingMachineCapacityObserver capacityObserver= new VendingMachineCapacityObserver(automat);
        Scanner scanner= new Scanner("bla");
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        assertEquals(automat, CLI.getObservableVendingMachine());
        assertEquals(capacityObserver, CLI.getCapacityObserver());
        assertEquals(scanner, CLI.getScanner());
    }

    @Test
    void testConsoleReaderConstructor2(){
        VendingMachineAllergensObserver allergeneObserver= new VendingMachineAllergensObserver(automat);
        VendingMachineCapacityObserver capacityObserver= new VendingMachineCapacityObserver(automat);
        Scanner scanner= new Scanner("bla");
        CLI CLI = new CLI(automat, allergeneObserver, capacityObserver, scanner);
        assertEquals(automat, CLI.getObservableVendingMachine());
        assertEquals(allergeneObserver, CLI.getAllergensObserver());
        assertEquals(capacityObserver, CLI.getCapacityObserver());
        assertEquals(scanner, CLI.getScanner());
    }

    @Test
    void checkIfConsoleReaderRecognizesModiHappyPath() {
        String userInput = ":c";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        assertEquals(true, CLI.printCLIMenuAndValidateFirstUserInputString());
    }

    @Test
    void  checkIfConsoleReaderRecognizesModiWithoutValidInput() {
        String userInput = "sdfacEgb348745g!";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        assertEquals(false, CLI.printCLIMenuAndValidateFirstUserInputString());
    }

    @Test
    void testHandleUserInputCommandsMethodWithValidCommand() {
        String userInput = ":c\nhersteller1";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        InputEventHandler mockedHandler = mock(InputEventHandler.class);
        CLI.setInputEventHandler(mockedHandler);

        CLI.printCLIMenuAndValidateFirstUserInputString();
        assertEquals("user command handled: :c hersteller1", CLI.handleValidUserInputCommands());
    }

    @Test
    void testHandleUserInputCommandsMethodWithModiSwap() {
        String userInput = ":c\n:d";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);
        InputEventHandler mockedHandler = mock(InputEventHandler.class);
        CLI.setInputEventHandler(mockedHandler);

        CLI.printCLIMenuAndValidateFirstUserInputString();
        assertEquals(":d", CLI.handleValidUserInputCommands());
    }

    @Test
    void testCreateStringOfCLIMenu() {
        String userInput = ":c\nhersteller1";
        Scanner scanner = new Scanner(userInput);
        VendingMachineCapacityObserver capacityObserver = new VendingMachineCapacityObserver(automat);
        CLI CLI = new CLI(automat, capacityObserver, scanner);

        assertTrue(CLI.createStringOfCLIMenu() instanceof String);
    }
}