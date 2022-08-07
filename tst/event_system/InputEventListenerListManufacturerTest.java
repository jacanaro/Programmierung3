package event_system;

import CLI.CLI;
import domain_logic.ManufacturerImpl;
import observer_pattern.ObservableVendingMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputEventListenerListManufacturerTest {

    private ObservableVendingMachine automat;
    private InputEventHandler inputEventHandler;
    private InputEventListener lListHersteller;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        automat = new ObservableVendingMachine(20);
        inputEventHandler = new InputEventHandler();
        lListHersteller = new InputEventListenerListManufacturer();
        inputEventHandler.add(lListHersteller);
    }

    @Test
    void testInputEventListenerListHerstellerHappyPath() throws ParseException, InterruptedException {
        System.setOut(new PrintStream(outContent));

        automat.addManufacturer(new ManufacturerImpl("h1"));
        String expected=automat.listManufacturersWithProductsCounted().toString();

        CLI CLI = mock(CLI.class);
        when(CLI.getObservableVendingMachine()).thenReturn(automat);

        String stringForInputEvent = ":r hersteller";
        InputEvent e = new InputEvent(CLI, stringForInputEvent);
        lListHersteller.onInputEvent(e);

        assertEquals(expected, outContent.toString().trim());

        System.setOut(originalOut);
    }

    @Test
    void listHerstellerWithEmptyList() throws ParseException, InterruptedException {
        System.setOut(new PrintStream(outContent));

        String expected=automat.listManufacturersWithProductsCounted().toString();

        CLI CLI = mock(CLI.class);
        when(CLI.getObservableVendingMachine()).thenReturn(automat);

        String stringForInputEvent = ":r hersteller";
        InputEvent e = new InputEvent(CLI, stringForInputEvent);
        lListHersteller.onInputEvent(e);

        assertEquals(expected, outContent.toString().trim());

        System.setOut(originalOut);
    }

    @Test
    void listHerstellerWithInvalidInputString() throws ParseException, InterruptedException {
        System.setOut(new PrintStream(outContent));

        String expected="";

        CLI CLI = mock(CLI.class);
        when(CLI.getObservableVendingMachine()).thenReturn(automat);

        String stringForInputEvent = "osdfbwoeuib!";
        InputEvent e = new InputEvent(CLI, stringForInputEvent);
        lListHersteller.onInputEvent(e);

        assertEquals(expected, outContent.toString().trim());

        System.setOut(originalOut);
    }

    @Test
    void listHerstellerTestCaseSensitivity() throws ParseException, InterruptedException {
        System.setOut(new PrintStream(outContent));

        String expected="";

        CLI CLI = mock(CLI.class);
        when(CLI.getObservableVendingMachine()).thenReturn(automat);

        String stringForInputEvent = ":c Hersteller";
        InputEvent e = new InputEvent(CLI, stringForInputEvent);
        lListHersteller.onInputEvent(e);

        assertEquals(expected, outContent.toString().trim());

        System.setOut(originalOut);
    }

}