package event_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputEventHandlerTest {
    private InputEventHandler inputEventHandler;


    @BeforeEach
    void setUp() {
        inputEventHandler = new InputEventHandler();
    }

    @Test
    void testAddListener() {
        InputEventListenerAddManufacturer tester = new InputEventListenerAddManufacturer();
        inputEventHandler.add(tester);
        assertEquals(true, inputEventHandler.getListenerList().contains(tester));
    }

    @Test
    void testEventHandle() throws ParseException, InterruptedException {
        InputEventListener listenerMock = mock(InputEventListener.class);
        InputEvent eventMock = mock(InputEvent.class);
        inputEventHandler.add(listenerMock);
        inputEventHandler.handle(eventMock);
        verify(listenerMock, times(1)).onInputEvent(eventMock);
    }

}