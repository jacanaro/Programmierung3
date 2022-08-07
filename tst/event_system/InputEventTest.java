package event_system;

import CLI.CLI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InputEventTest {
    @Test
    void testGetSourceOfInputEvent() {
        CLI CLI = mock(CLI.class);
        String optionString = "optionString";
        InputEvent inputEvent = new InputEvent(CLI, optionString);
        assertEquals(CLI, inputEvent.getSource());
    }

    @Test
    void testGetTextOfInputEvent() {
        CLI CLI = mock(CLI.class);
        String optionString = "optionString";
        InputEvent inputEvent = new InputEvent(CLI, optionString);
        assertEquals(optionString, inputEvent.getText());
    }

}