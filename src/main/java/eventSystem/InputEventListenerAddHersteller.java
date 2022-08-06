package eventSystem;

import CLI.ConsoleReader;
import domainLogic.automat.HerstellerImplementierung;

public class InputEventListenerAddHersteller implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":c")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                if (userInputStrArr.length < 3) {
                    ConsoleReader c = (ConsoleReader) event.getSource();
                    String herstellerName = "";
                    for (String str : userInputStrArr) if (!str.equals("")) herstellerName = str;

                    if(c.getMy_log()!=null)c.getMy_log().logger.info("es wird versucht einen Hersteller zu erzeugen");
                    boolean herstellerAdded=c.getObservableAutomat().addHersteller(new HerstellerImplementierung(herstellerName));
                    if(herstellerAdded && c.getMy_log()!=null)c.getMy_log().logger.info("Hersteller wurde hinzugefügt");
                }
            }
        }
    }
}
