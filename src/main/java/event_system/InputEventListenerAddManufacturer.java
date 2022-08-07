package event_system;

import CLI.CLI;
import domain_logic.ManufacturerImpl;

public class InputEventListenerAddManufacturer implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":c")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                if (userInputStrArr.length < 3) {
                    CLI c = (CLI) event.getSource();
                    String herstellerName = "";
                    for (String str : userInputStrArr) if (!str.equals("")) herstellerName = str;

                    if(c.getLog()!=null)c.getLog().logger.info("es wird versucht einen Hersteller zu erzeugen");
                    boolean herstellerAdded=c.getObservableVendingMachine().addHersteller(new ManufacturerImpl(herstellerName));
                    if(herstellerAdded && c.getLog()!=null)c.getLog().logger.info("Hersteller wurde hinzugefügt");
                }
            }
        }
    }
}
