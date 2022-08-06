package eventSystem;

import CLI.ConsoleReader;
import domainLogic.automat.KuchenImplementierung;

public class InputEventListenerDoInspection implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) throws InterruptedException {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":u")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for (String str : userInputStrArr) {
                    if (str.matches("[0-9]+")) {
                        try {
                            int fachnummer = Integer.parseInt(str);
                            ConsoleReader c = (ConsoleReader) event.getSource();
                            if (c.getMy_log() != null)
                                c.getMy_log().logger.info("es wird versucht eine Inspektion durchzuführen");
                            KuchenImplementierung inpectedKuchen = c.getObservableAutomat().doInspection(fachnummer);
                            if (inpectedKuchen != null && c.getMy_log() != null)
                                c.getMy_log().logger.info("Inspektion wurde durchgeführt");
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }
                }
            }
        }
    }
}
