package event_system;

import CLI.CLI;

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
                        CLI c = (CLI) event.getSource();
                        int fachnummer=Integer.parseInt(str);
                            if(c.getLog()!=null)c.getLog().logger.info("es wird versucht, ein Kuchen zu löschen");
                            boolean kucheGelöscht=c.getObservableVendingMachine().deleteProduct(fachnummer);
                            if(kucheGelöscht && c.getLog()!=null)c.getLog().logger.info("Kuchen wurde gelöscht");
                        }catch (IndexOutOfBoundsException e){
                            System.out.println(e);
                        }
                    }
                }
            }
        }
    }
}
