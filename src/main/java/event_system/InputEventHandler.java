package event_system;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class InputEventHandler {
    private List<InputEventListener> listenerList = new LinkedList<>();

    public void add(InputEventListener listener) {
        this.listenerList.add(listener);
    }

    public void handle(InputEvent event) throws InterruptedException, ParseException, StringIndexOutOfBoundsException {
        for (InputEventListener listener : listenerList) listener.onInputEvent(event);}

    public List<InputEventListener> getListenerList(){return listenerList;}
}
