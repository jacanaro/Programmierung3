package event_system;

import CLI.CLI;
import domain_logic.ManufacturerImpl;
import observer_pattern.ObservableVendingMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputEventListenerAddManufacturerTest {
    private ObservableVendingMachine automat;
    private InputEventHandler inputEventHandler;
    private InputEventListener lAddHersteller;

    @BeforeEach
    void setUp() {
        automat = new ObservableVendingMachine(20);
        inputEventHandler = new InputEventHandler();
        lAddHersteller = new InputEventListenerAddManufacturer();
        inputEventHandler.add(lAddHersteller);
    }

    @Test
    void testInputEventListenerAddHerstellerHappyPath() throws ParseException, InterruptedException {
        CLI CLI = mock(CLI.class);
        when(CLI.getObservableVendingMachine()).thenReturn(automat);

        String stringForInputEvent = ":c hersteller1";
        InputEvent e = new InputEvent(CLI, stringForInputEvent);
        lAddHersteller.onInputEvent(e);
        ArrayList<ManufacturerImpl> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals("hersteller1", herstellerListe.get(0).getName());
    }

    @Test
    void testInputEventListenerAddHerstellerWithInvalidInputString() throws ParseException, InterruptedException {
        CLI CLI = mock(CLI.class);
        when(CLI.getObservableVendingMachine()).thenReturn(automat);

        String stringForInputEvent = "asdöfljhbniujg!";
        InputEvent e = new InputEvent(CLI, stringForInputEvent);
        lAddHersteller.onInputEvent(e);
        ArrayList<ManufacturerImpl> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals(true, herstellerListe.isEmpty());
    }

    @Test
    void addMoreThanOneAtOnce() throws ParseException, InterruptedException {
        CLI CLI = mock(CLI.class);
        when(CLI.getObservableVendingMachine()).thenReturn(automat);

        String stringForInputEvent = ":c hersteller1 hersteller2 hersteller3";
        InputEvent e = new InputEvent(CLI, stringForInputEvent);
        lAddHersteller.onInputEvent(e);
        ArrayList<ManufacturerImpl> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals(true, herstellerListe.isEmpty());
    }

    @Test
    void addHerstellerWithInvalidSpaces() throws ParseException, InterruptedException {
        CLI CLI = mock(CLI.class);
        when(CLI.getObservableVendingMachine()).thenReturn(automat);

        String stringForInputEvent = ":c Dr. Oetker";
        InputEvent e = new InputEvent(CLI, stringForInputEvent);
        lAddHersteller.onInputEvent(e);
        ArrayList<ManufacturerImpl> herstellerListe = new ArrayList<>(automat.getHerstellerSet());
        assertEquals(true, herstellerListe.isEmpty());
    }

}