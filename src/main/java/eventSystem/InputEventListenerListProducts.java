package eventSystem;

import CLI.ConsoleReader;

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
                        ConsoleReader c = (ConsoleReader) event.getSource();
                        if(i==userInputStrArr.length-1){
                            System.out.println(c.getObservableAutomat().getVerkaufsobjekte().toString());
                            if(c.getMy_log()!=null)c.getMy_log().logger.info("es werden alle Kuchen die im Automat existieren ausgegeben");
                        }else {
                            System.out.println(c.getObservableAutomat().getVerkaufsobjekteOfType(userInputStrArr[i+1]).toString());
                            if(c.getMy_log()!=null)c.getMy_log().logger.info("es werden alle Kuchen vom Typ"+userInputStrArr[i+1]+", die im Automat existieren ausgegeben");
                        }
                    }
                }
            }
        }
    }
}
