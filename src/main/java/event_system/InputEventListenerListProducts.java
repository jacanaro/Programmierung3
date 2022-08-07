package event_system;

import CLI.CLI;

public class InputEventListenerListProducts implements InputEventListener{
    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":r")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for(int i=0; i<userInputStrArr.length;i++){
                    if(userInputStrArr[i].equals("kuchen")){
                        CLI c = (CLI) event.getSource();
                        if(i==userInputStrArr.length-1){
                            System.out.println(c.getObservableVendingMachine().getVerkaufsobjekte().toString());
                            if(c.getLog()!=null)c.getLog().logger.info("es werden alle Kuchen die im Automat existieren ausgegeben");
                        }else {
                            System.out.println(c.getObservableVendingMachine().getVerkaufsobjekteOfType(userInputStrArr[i+1]).toString());
                            if(c.getLog()!=null)c.getLog().logger.info("es werden alle Kuchen vom Typ"+userInputStrArr[i+1]+", die im Automat existieren ausgegeben");
                        }
                    }
                }
            }
        }
    }
}
