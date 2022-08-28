package event_system;

import CLI.CLI;

public class InputEventListenerDeleteProduct implements InputEventListener{
    @Override
    public void onInputEvent(InputEvent event) throws InterruptedException {
        if (null != event.getText()) {
            String userInput = event.getText();
            String mode = userInput.substring(0, 2);
            if (mode.equals(":d")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                for(String userInputString: userInputStrArr){
                    if(userInputString.matches("[0-9]+")){
                        try{
                        CLI c = (CLI) event.getSource();
                        int vendingMachineSlot=Integer.parseInt(userInputString);
                            if(c.getLog()!=null)c.getLog().logger.info("es wird versucht, ein Kuchen zu löschen");
                            boolean productDeleted=c.getObservableVendingMachine().deleteProduct(vendingMachineSlot);
                            if(productDeleted && c.getLog()!=null)c.getLog().logger.info("Kuchen wurde gelöscht");
                        }catch (IndexOutOfBoundsException e){
                            System.out.println(e);
                        }
                    }
                }
            }
        }
    }
}
