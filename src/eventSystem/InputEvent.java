package eventSystem;

import java.util.EventObject;

public class InputEvent extends EventObject {
    private String option;
    public InputEvent(Object source,String option) {
        super(source);
        this.option=option;
    }
    public String getText(){
        return this.option;
    }
}
