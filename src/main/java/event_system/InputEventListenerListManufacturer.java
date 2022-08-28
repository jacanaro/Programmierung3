package event_system;

import CLI.CLI;

public class InputEventListenerListManufacturer implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String userInput = event.getText();
            String mode = userInput.substring(0, 2);
            if (mode.equals(":r")) {
                try {
                    String[] userInputStrArr = userInput.substring(2).split(" ");
                    for (String userInputString : userInputStrArr)
                        if (userInputString.equals("hersteller")) {
                            CLI c = (CLI) event.getSource();
                            if (c.getLog() != null)
                                c.getLog().logger.info("es werden alle Hersteller, die im Automat vertreten sind, mit Anzahl der Kuchen ausgegeben");
                            System.out.println(c.getObservableVendingMachine().listManufacturersWithProductsCounted().toString());
                        }
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }
    }
}
