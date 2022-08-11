package event_system;
import CLI.CLI;
import domain_logic.Allergen;
import domain_logic.VendingMachineErrorCodes;
import domain_logic.ManufacturerImpl;
import domain_logic.CakeImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputEventListenerAddFruitFlan implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String userInput = event.getText();
            String cliMode = userInput.substring(0, 2);
            if (cliMode.equals(":c")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                if (userInputStrArr.length > 2) {
                    for(int i=0; i< userInputStrArr.length;i++){
                        if(userInputStrArr[i].equals("Obsttorte")){
                            CLI cli = (CLI) event.getSource();
                            if(cli.getLog()!=null)cli.getLog().logger.info("es wird versucht, dem Automat eine Obsttorte hinzuzufügen");
                            VendingMachineErrorCodes vendingMachineErrorCodes =cli.getObservableVendingMachine().addProduct(new CakeImpl(
                                    userInputStrArr[i+7],
                                    new ManufacturerImpl(userInputStrArr[i + 1]),
                                    cli.getFormatter().formatCommaSeperatedAllergens(userInputStrArr[i + 5]),
                                    cli.getFormatter().parseStringToInt(userInputStrArr[i + 3]),
                                    Duration.ofHours(cli.getFormatter().parseStringToInt(userInputStrArr[i + 4])),
                                    userInputStrArr[i+6],
                                    cli.getFormatter().formatPrice(userInputStrArr[i + 2])));
                            if(vendingMachineErrorCodes ==null && cli.getLog()!=null)cli.getLog().logger.info("Obsttorte wurde hinzugefügt");
                        }
                    }
                }
            }
        }
    }
}
