package event_system;

import CLI.CLI;

public class InputEventListenerListAllergens implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":r")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for (int i = 0; i < userInputStrArr.length; i++) {
                    if (userInputStrArr[i].equals("allergene")) {
                        CLI c = (CLI) event.getSource();
                        if (userInputStrArr[i+1].equals("i")) {
                            if(c.getLog()!=null)c.getLog().logger.info("es werden alle Allergene die im Automat existieren ausgegeben");
                            System.out.println(c.getObservableVendingMachine().getAllergens(true));
                        } else if(userInputStrArr[i+1].equals("e")){
                            if(c.getLog()!=null)c.getLog().logger.info("es werden alle Allergene die nicht im Automat existieren ausgegeben");
                            System.out.println(c.getObservableVendingMachine().getAllergens(false));
                        }
                    }
                }
            }
        }
    }
}
