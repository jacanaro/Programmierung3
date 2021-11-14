package eventSystem;

import CLI.ConsoleReader;
import IO.JBP;
import domainLogic.automat.Automat;
import observerPattern.AutomatAllergeneObserver;
import observerPattern.AutomatCapacityObserver;
import observerPattern.ObservableAutomat;

public class InputEventListenerSafeAndLoad implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) throws InterruptedException {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":p")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for (String str : userInputStrArr) {
                    switch (str) {
                        case "saveJOS":
                            ConsoleReader c = (ConsoleReader) event.getSource();
                            System.out.println("Serialisiert: " + c.getObservableAutomat());
                            ObservableAutomat oAutomat=c.getObservableAutomat();
                            oAutomat.removeObserver(c.getAllergeneObserver());
                            oAutomat.removeObserver(c.getCapacityObserver());
                            Automat automat=oAutomat;
                            automat.serialize("automaten.ser", automat);
                            if(c.getMy_log()!=null)c.getMy_log().logger.info("Der Zustand des Automaten wurde mittels JOS gespeichert");
                            break;
                        case "loadJOS":
                            Automat loadedAutomat = Automat.deserialize("automaten.ser");
                            System.out.println("Deserialisiert: " + loadedAutomat);
                            ConsoleReader c2 = (ConsoleReader) event.getSource();
                            ObservableAutomat o = (ObservableAutomat) loadedAutomat;
                            c2.setObservableAutomat(o);
                            c2.setO(new AutomatAllergeneObserver(o));
                            c2.setO2(new AutomatCapacityObserver(o));
                            if(c2.getMy_log()!=null)c2.getMy_log().logger.info("Der Zustand des Automaten wurde mittels JOS geladen");
                            break;
                        case "saveJBP":
                            JBP jbpObj = new JBP();
                            ConsoleReader c3 = (ConsoleReader) event.getSource();
                            System.out.println("Saved: " + c3.getObservableAutomat() + "\n" +
                                    "=" + jbpObj.JBPSave(c3.getObservableAutomat()));
                            if(c3.getMy_log()!=null)c3.getMy_log().logger.info("Der Zustand des Automaten wurde mittels JBP gespeichert");
                            break;
                        case "loadJBP":
                            JBP jbpObj2 = new JBP();
                            ConsoleReader c4 = (ConsoleReader) event.getSource();
                            System.out.println("Loaded: " + jbpObj2.JBPLoad());
                            ObservableAutomat o2=(ObservableAutomat) jbpObj2.JBPLoad();
                            c4.setObservableAutomat(o2);
                            c4.setO(new AutomatAllergeneObserver(o2));
                            c4.setO2(new AutomatCapacityObserver(o2));
                            if(c4.getMy_log()!=null)c4.getMy_log().logger.info("Der Zustand des Automaten wurde mittels JBP geladen");
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
