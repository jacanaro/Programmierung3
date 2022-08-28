package event_system;

import CLI.CLI;
import domain_logic.CakeImpl;

public class InputEventListenerDoInspection implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) throws InterruptedException {
        if (null != event.getText()) {
            String userInput = event.getText();
            String mode = userInput.substring(0, 2);
            if (mode.equals(":u")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for (String userInputString : userInputStrArr) {
                    if (userInputString.matches("[0-9]+")) {
                        try {
                            int vendingMachineSlot = Integer.parseInt(userInputString);
                            CLI c = (CLI) event.getSource();
                            if (c.getLog() != null)
                                c.getLog().logger.info("es wird versucht eine Inspektion durchzuführen");
                            CakeImpl inspectedProduct = c.getObservableVendingMachine().doInspection(vendingMachineSlot);
                            if (inspectedProduct != null && c.getLog() != null)
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
