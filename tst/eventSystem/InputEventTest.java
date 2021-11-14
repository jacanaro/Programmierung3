package eventSystem;

import CLI.ConsoleReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InputEventTest {
    @Test
    void testGetSourceOfInputEvent() {
        ConsoleReader consoleReader = mock(ConsoleReader.class);
        String optionString = "optionString";
        InputEvent inputEvent = new InputEvent(consoleReader, optionString);
        assertEquals(consoleReader, inputEvent.getSource());
    }

    @Test
    void testGetTextOfInputEvent() {
        ConsoleReader consoleReader = mock(ConsoleReader.class);
        String optionString = "optionString";
        InputEvent inputEvent = new InputEvent(consoleReader, optionString);
        assertEquals(optionString, inputEvent.getText());
    }

}