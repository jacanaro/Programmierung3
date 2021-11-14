package eventSystem;

import CLI.ConsoleReader;

public class InputEventListenerListAllergene implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":r")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for (int i = 0; i < userInputStrArr.length; i++) {
                    if (userInputStrArr[i].equals("allergene")) {
                        ConsoleReader c = (ConsoleReader) event.getSource();
                        if (userInputStrArr[i+1].equals("i")) {
                            if(c.getMy_log()!=null)c.getMy_log().logger.info("es werden alle Allergene die im Automat existieren ausgegeben");
                            System.out.println(c.getObservableAutomat().getAllergene(true));
                        } else if(userInputStrArr[i+1].equals("e")){
                            if(c.getMy_log()!=null)c.getMy_log().logger.info("es werden alle Allergene die nicht im Automat existieren ausgegeben");
                            System.out.println(c.getObservableAutomat().getAllergene(false));
                        }
                    }
                }
            }
        }
    }
}
