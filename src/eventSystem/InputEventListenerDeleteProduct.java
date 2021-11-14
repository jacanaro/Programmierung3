package eventSystem;

import CLI.ConsoleReader;

public class InputEventListenerDeleteProduct implements InputEventListener{
    @Override
    public void onInputEvent(InputEvent event) throws InterruptedException {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":d")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for(String str: userInputStrArr){
                    if(str.matches("[0-9]+")){
                        try{
                        ConsoleReader c = (ConsoleReader) event.getSource();
                        int fachnummer=Integer.parseInt(str);
                            if(c.getMy_log()!=null)c.getMy_log().logger.info("es wird versucht, ein Kuchen zu löschen");
                            boolean kucheGelöscht=c.getObservableAutomat().deleteVerkaufsobjekt(fachnummer);
                            if(kucheGelöscht && c.getMy_log()!=null)c.getMy_log().logger.info("Kuchen wurde gelöscht");
                        }catch (IndexOutOfBoundsException e){
                            System.out.println(e);
                        }
                    }
                }
            }
        }
    }
}
