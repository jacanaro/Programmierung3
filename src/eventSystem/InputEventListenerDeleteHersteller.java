package eventSystem;


import CLI.ConsoleReader;
import domainLogic.automat.HerstellerImplementierung;

public class InputEventListenerDeleteHersteller implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) throws InterruptedException {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":d")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for (String str : userInputStrArr) {
                    if (!str.equals("")) {
                        ConsoleReader c = (ConsoleReader) event.getSource();
                        if (str.matches("[0-9]+")) {
                            try {
                                for (HerstellerImplementierung hersteller : c.getObservableAutomat().getHerstellerSet()) {
                                    if (hersteller.getName().equals(str)) {
                                        boolean herstellerGelöscht = c.getObservableAutomat().deleteHersteller(str);
                                        if (herstellerGelöscht && c.getMy_log() != null)
                                            c.getMy_log().logger.info("Hersteller wurde gelöscht");
                                        System.out.println("Hersteller mit dem Namen: " + str + " wurde gelöscht" + "\n"
                                                + "Wählen Sie Herstellernamen die nicht auschließlich aus Ziffern bestehen!");
                                    }
                                }
                                if (c.getMy_log() != null)
                                    c.getMy_log().logger.info("es wird versucht, ein Hersteller zu löschen");
                                boolean herstellerGelöscht = c.getObservableAutomat().deleteHersteller(str);
                                if (herstellerGelöscht && c.getMy_log() != null)
                                    c.getMy_log().logger.info("Hersteller wurde gelöscht");
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
