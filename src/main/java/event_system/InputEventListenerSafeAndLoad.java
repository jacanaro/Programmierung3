package event_system;

import CLI.CLI;
import IO.JBP;
import domain_logic.VendingMachine;
import observer_pattern.VendingMachineAllergensObserver;
import observer_pattern.VendingMachineCapacityObserver;
import observer_pattern.ObservableVendingMachine;

public class InputEventListenerSafeAndLoad implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) throws InterruptedException {
        if (null != event.getText()) {
            String userInput = event.getText();
            String mode = userInput.substring(0, 2);
            if (mode.equals(":p")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for (String userInputString : userInputStrArr) {
                    switch (userInputString) {
                        case "saveJOS":
                            CLI c = (CLI) event.getSource();
                            System.out.println("Serialisiert: " + c.getObservableVendingMachine());
                            ObservableVendingMachine observableVendingMachine=c.getObservableVendingMachine();
                            observableVendingMachine.removeObserver(c.getAllergensObserver());
                            observableVendingMachine.removeObserver(c.getCapacityObserver());
                            VendingMachine vendingMachine=observableVendingMachine;
                            vendingMachine.serializeVendingMachine("automaten.ser", vendingMachine);
                            if(c.getLog()!=null)c.getLog().logger.info("Der Zustand des Automaten wurde mittels JOS gespeichert");
                            break;
                        case "loadJOS":
                            VendingMachine loadedVendingMachine = VendingMachine.deserializeVendingMachine("automaten.ser");
                            System.out.println("Deserialisiert: " + loadedVendingMachine);
                            CLI c2 = (CLI) event.getSource();
                            ObservableVendingMachine o = (ObservableVendingMachine) loadedVendingMachine;
                            c2.setObservableAutomat(o);
                            c2.setVendingMachineAllergensObserver(new VendingMachineAllergensObserver(o));
                            c2.setVendingMachineCapacityObserver(new VendingMachineCapacityObserver(o));
                            if(c2.getLog()!=null)c2.getLog().logger.info("Der Zustand des Automaten wurde mittels JOS geladen");
                            break;
                        case "saveJBP":
                            JBP jbpObj = new JBP();
                            CLI c3 = (CLI) event.getSource();
                            System.out.println("Saved: " + c3.getObservableVendingMachine() + "\n" +
                                    "=" + jbpObj.JBPSave(c3.getObservableVendingMachine()));
                            if(c3.getLog()!=null)c3.getLog().logger.info("Der Zustand des Automaten wurde mittels JBP gespeichert");
                            break;
                        case "loadJBP":
                            JBP jbpObj2 = new JBP();
                            CLI c4 = (CLI) event.getSource();
                            System.out.println("Loaded: " + jbpObj2.JBPLoad());
                            ObservableVendingMachine o2=(ObservableVendingMachine) jbpObj2.JBPLoad();
                            c4.setObservableAutomat(o2);
                            c4.setVendingMachineAllergensObserver(new VendingMachineAllergensObserver(o2));
                            c4.setVendingMachineCapacityObserver(new VendingMachineCapacityObserver(o2));
                            if(c4.getLog()!=null)c4.getLog().logger.info("Der Zustand des Automaten wurde mittels JBP geladen");
                            break;
                        case "exit":
                            System.exit(0);
                        default:
                            break;
                    }
                }
            }
        }
    }
}
