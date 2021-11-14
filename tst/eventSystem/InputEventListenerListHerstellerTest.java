package eventSystem;

import CLI.ConsoleReader;
import domainLogic.automat.HerstellerImplementierung;
import observerPattern.ObservableAutomat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputEventListenerListHerstellerTest {

    private ObservableAutomat automat;
    private InputEventHandler inputEventHandler;
    private InputEventListener lListHersteller;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        automat = new ObservableAutomat(20);
        inputEventHandler = new InputEventHandler();
        lListHersteller = new InputEventListenerListHersteller();
        inputEventHandler.add(lListHersteller);
    }

    @Test
    void testInputEventListenerListHerstellerHappyPath() throws ParseException, InterruptedException {
        System.setOut(new PrintStream(outContent));

        automat.addHersteller(new HerstellerImplementierung("h1"));
        String expected=automat.listHerstellerWithCakeCount().toString();

        ConsoleReader consoleReader = mock(ConsoleReader.class);
        when(consoleReader.getObservableAutomat()).thenReturn(automat);

        String stringForInputEvent = ":r hersteller";
        InputEvent e = new InputEvent(consoleReader, stringForInputEvent);
        lListHersteller.onInputEvent(e);

        assertEquals(expected, outContent.toString().trim());

        System.setOut(originalOut);
    }

    @Test
    void listHerstellerWithEmptyList() throws ParseException, InterruptedException {
        System.setOut(new PrintStream(outContent));

        String expected=automat.listHerstellerWithCakeCount().toString();

        ConsoleReader consoleReader = mock(ConsoleReader.class);
        when(consoleReader.getObservableAutomat()).thenReturn(automat);

        String stringForInputEvent = ":r hersteller";
        InputEvent e = new InputEvent(consoleReader, stringForInputEvent);
        lListHersteller.onInputEvent(e);

        assertEquals(expected, outContent.toString().trim());

        System.setOut(originalOut);
    }

    @Test
    void listHerstellerWithInvalidInputString() throws ParseException, InterruptedException {
        System.setOut(new PrintStream(outContent));

        String expected="";

        ConsoleReader consoleReader = mock(ConsoleReader.class);
        when(consoleReader.getObservableAutomat()).thenReturn(automat);

        String stringForInputEvent = "osdfbwoeuib!";
        InputEvent e = new InputEvent(consoleReader, stringForInputEvent);
        lListHersteller.onInputEvent(e);

        assertEquals(expected, outContent.toString().trim());

        System.setOut(originalOut);
    }

    @Test
    void listHerstellerTestCaseSensitivity() throws ParseException, InterruptedException {
        System.setOut(new PrintStream(outContent));

        String expected="";

        ConsoleReader consoleReader = mock(ConsoleReader.class);
        when(consoleReader.getObservableAutomat()).thenReturn(automat);

        String stringForInputEvent = ":c Hersteller";
        InputEvent e = new InputEvent(consoleReader, stringForInputEvent);
        lListHersteller.onInputEvent(e);

        assertEquals(expected, outContent.toString().trim());

        System.setOut(originalOut);
    }

}