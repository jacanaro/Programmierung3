package event_system;

import CLI.CLI;
import domain_logic.CakeImpl;

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
                            CLI c = (CLI) event.getSource();
                            if (c.getLog() != null)
                                c.getLog().logger.info("es wird versucht eine Inspektion durchzuführen");
                            CakeImpl inpectedKuchen = c.getObservableVendingMachine().doInspection(fachnummer);
                            if (inpectedKuchen != null && c.getLog() != null)
                                c.getLog().logger.info("Inspektion wurde durchgeführt");
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }
                }
            }
        }
    }
}
