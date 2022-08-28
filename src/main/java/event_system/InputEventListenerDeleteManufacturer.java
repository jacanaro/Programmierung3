package event_system;

import CLI.CLI;
import domain_logic.ManufacturerImpl;

public class InputEventListenerDeleteManufacturer implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) throws InterruptedException {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":d")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for (String userInputString : userInputStrArr) {
                    if (!userInputString.equals("")) {
                        CLI c = (CLI) event.getSource();
                        if (userInputString.matches("[0-9]+")) {
                            try {
                                for (ManufacturerImpl manufacturer : c.getObservableVendingMachine().getManufacturers()) {
                                    if (manufacturer.getName().equals(userInputString)) {
                                        boolean manufacturerDeleted = c.getObservableVendingMachine().deleteManufacturer(userInputString);
                                        if (manufacturerDeleted && c.getLog() != null)
                                            c.getLog().logger.info("Hersteller wurde gelöscht");
                                        System.out.println("Hersteller mit dem Namen: " + userInputString + " wurde gelöscht" + "\n"
                                                + "Wählen Sie Herstellernamen die nicht auschließlich aus Ziffern bestehen!");
                                    }
                                }
                                if (c.getLog() != null)
                                    c.getLog().logger.info("es wird versucht, ein Hersteller zu löschen");
                                boolean manufacturerDeleted = c.getObservableVendingMachine().deleteManufacturer(userInputString);
                                if (manufacturerDeleted && c.getLog() != null)
                                    c.getLog().logger.info("Hersteller wurde gelöscht");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                }
            }
        }
    }
}
