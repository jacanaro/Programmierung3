package eventSystem;

import CLI.ConsoleReader;
import domainLogic.automat.HerstellerImplementierung;
import observerPattern.ObservableAutomat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputEventListenerAddHerstellerTest {
    private ObservableAutomat automat;
    private InputEventHandler inputEventHandler;
    private InputEventListener lAddHersteller;

    @BeforeEach
    void setUp() {
        automat = new ObservableAutomat(20);
        inputEventHandler = new InputEventHandler();
        lAddHersteller = new InputEventListenerAddHersteller();
        inputEventHandler.add(lAddHersteller);
    }

    @Test
    void testInputEventListenerAddHerstellerHappyPath() throws ParseException, InterruptedException {
        ConsoleReader consoleReader = mock(ConsoleReader.class);
        when(consoleReader.getObservableAutomat()).thenReturn(automat);

        String stringForInputEvent = ":c hersteller1";
        InputEvent e = new InputEvent(consoleReader, stringForInputEvent);
        lAddHersteller.onInputEvent(e);
        ArrayList<HerstellerImplementierung> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals("hersteller1", herstellerListe.get(0).getName());
    }

    @Test
    void testInputEventListenerAddHerstellerWithInvalidInputString() throws ParseException, InterruptedException {
        ConsoleReader consoleReader = mock(ConsoleReader.class);
        when(consoleReader.getObservableAutomat()).thenReturn(automat);

        String stringForInputEvent = "asdöfljhbniujg!";
        InputEvent e = new InputEvent(consoleReader, stringForInputEvent);
        lAddHersteller.onInputEvent(e);
        ArrayList<HerstellerImplementierung> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals(true, herstellerListe.isEmpty());
    }

    @Test
    void addMoreThanOneAtOnce() throws ParseException, InterruptedException {
        ConsoleReader consoleReader = mock(ConsoleReader.class);
        when(consoleReader.getObservableAutomat()).thenReturn(automat);

        String stringForInputEvent = ":c hersteller1 hersteller2 hersteller3";
        InputEvent e = new InputEvent(consoleReader, stringForInputEvent);
        lAddHersteller.onInputEvent(e);
        ArrayList<HerstellerImplementierung> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals(true, herstellerListe.isEmpty());
    }

    @Test
    void addHerstellerWithInvalidSpaces() throws ParseException, InterruptedException {
        ConsoleReader consoleReader = mock(ConsoleReader.class);
        when(consoleReader.getObservableAutomat()).thenReturn(automat);

        String stringForInputEvent = ":c Dr. Oetker";
        InputEvent e = new InputEvent(consoleReader, stringForInputEvent);
        lAddHersteller.onInputEvent(e);
        ArrayList<HerstellerImplementierung> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals(true, herstellerListe.isEmpty());
    }

}