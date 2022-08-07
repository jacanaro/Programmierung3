package event_system;

import java.text.ParseException;
import java.util.EventListener;

public interface InputEventListener extends EventListener {
    void onInputEvent(InputEvent event) throws InterruptedException, ParseException;
}
