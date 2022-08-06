package eventSystem;

import CLI.ConsoleReader;

public class InputEventListenerListHersteller implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":r")) {
                try {
                    String[] userInputStrArr = userInput.substring(2).split(" ");
                    for (String str : userInputStrArr)
                        if (str.equals("hersteller")) {
                            ConsoleReader c = (ConsoleReader) event.getSource();
                            if (c.getMy_log() != null)
                                c.getMy_log().logger.info("es werden alle Hersteller, die im Automat vertreten sind, mit Anzahl der Kuchen ausgegeben");
                            System.out.println(c.getObservableAutomat().listHerstellerWithCakeCount().toString());
                        }
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }
    }
}
