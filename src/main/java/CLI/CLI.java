package CLI;

import event_system.InputEvent;
import event_system.InputEventHandler;
import log.Log;
import observer_pattern.VendingMachineAllergensObserver;
import observer_pattern.VendingMachineCapacityObserver;
import observer_pattern.ObservableVendingMachine;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private InputEventHandler inputEventHandler;
    private ObservableVendingMachine observableVendingMachine;
    private VendingMachineAllergensObserver vendingMachineAllergensObserver;
    private VendingMachineCapacityObserver vendingMachineCapacityObserver;
    private Scanner scanner;
    private ArrayList<String> cliModesList = new ArrayList<>();
    private String firstUserInputString = "";
    private String secondUserInputString = "";
    private Log log;

    public CLI(ObservableVendingMachine observableVendingMachine, VendingMachineCapacityObserver vendingMachineCapacityObserver, Scanner scanner) {
        this.observableVendingMachine = observableVendingMachine;
        this.vendingMachineCapacityObserver = vendingMachineCapacityObserver;
        this.scanner = scanner;
        cliModesList.add(":c");
        cliModesList.add(":d");
        cliModesList.add(":r");
        cliModesList.add(":u");
        cliModesList.add(":p");
    }

    public CLI(ObservableVendingMachine observableVendingMachine, VendingMachineAllergensObserver allergensObserver,
               VendingMachineCapacityObserver capacityObserver, Scanner scanner) {
        this.observableVendingMachine = observableVendingMachine;
        this.vendingMachineAllergensObserver = allergensObserver;
        this.vendingMachineCapacityObserver = capacityObserver;
        this.scanner = scanner;
        cliModesList.add(":c");
        cliModesList.add(":d");
        cliModesList.add(":r");
        cliModesList.add(":u");
        cliModesList.add(":p");
    }

    public CLI(ObservableVendingMachine observableVendingMachine, VendingMachineAllergensObserver allergensObserver,
               VendingMachineCapacityObserver capacityObserver, Scanner scanner, String loggingLanguage) throws IOException {
        this.observableVendingMachine = observableVendingMachine;
        this.vendingMachineAllergensObserver = allergensObserver;
        this.vendingMachineCapacityObserver = capacityObserver;
        this.scanner = scanner;
        cliModesList.add(":c");
        cliModesList.add(":d");
        cliModesList.add(":r");
        cliModesList.add(":u");
        cliModesList.add(":p");
        log = Log.getInstance("log.txt");
        log.setLanguage(loggingLanguage);
    }

    public CLI(ObservableVendingMachine observableVendingMachine, VendingMachineCapacityObserver vendingMachineCapacityObserver, Scanner scanner, String loggingLanguage) throws IOException{
        this.observableVendingMachine = observableVendingMachine;
        this.vendingMachineCapacityObserver = vendingMachineCapacityObserver;
        this.scanner = scanner;
        cliModesList.add(":c");
        cliModesList.add(":d");
        cliModesList.add(":r");
        cliModesList.add(":u");
        cliModesList.add(":p");
        log = Log.getInstance("log.txt");
        log.setLanguage(loggingLanguage);
    }

    public String createStringOfCLIMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Console-Application: <Aaron> <Jacob> <s0570648>" + System.lineSeparator());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(":c Wechsel in den Einfügemodus" + System.lineSeparator() +
                ":d Wechsel in den Löschmodus" + System.lineSeparator() +
                ":r Wechsel in den Anzeigemodus" + System.lineSeparator() +
                ":u Wechsel in den Änderungsmodus" + System.lineSeparator() +
                ":p Wechsel in den Persistenzmodus" + System.lineSeparator());

        return stringBuilder.toString();
    }

    public boolean printCLIMenuAndValidateFirstUserInputString() {
        System.out.println(createStringOfCLIMenu());
        firstUserInputString = scanner.nextLine();
        if (cliModesList.contains(firstUserInputString)) {
            return true;
        } else {
            return false;
        }
    }

    public String handleValidUserInputCommands() {
        secondUserInputString = scanner.nextLine();
        if (cliModesList.contains(secondUserInputString)) {
            firstUserInputString = secondUserInputString;
            return firstUserInputString;
        }
        String stringForInputEvent = firstUserInputString + " " + secondUserInputString;
        InputEvent e = new InputEvent(this, stringForInputEvent);
        if (null != this.inputEventHandler) {
            try {
                inputEventHandler.handle(e);
            } catch (StringIndexOutOfBoundsException exception) {
                System.out.println(exception);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        return "user command handled: " + stringForInputEvent;
    }

    public void start() {
        boolean userEnteredValidMode = false;

        while (userEnteredValidMode == false) {
            userEnteredValidMode = printCLIMenuAndValidateFirstUserInputString();
        }

        while (true) {
            String cliMode = handleValidUserInputCommands();
            if (cliModesList.contains(cliMode)) {
                firstUserInputString = cliMode;
            }
        }
    }
    
    public VendingMachineAllergensObserver getAllergensObserver() {
        return vendingMachineAllergensObserver;
    }

    public VendingMachineCapacityObserver getCapacityObserver() {
        return vendingMachineCapacityObserver;
    }

    public Log getLog() {return log;}

    public Scanner getScanner() {
        return scanner;
    }

    public ObservableVendingMachine getObservableVendingMachine() {
        return observableVendingMachine;
    }

    public void setObservableAutomat(ObservableVendingMachine v) {
        this.observableVendingMachine = v;
    }

    public void setInputEventHandler(InputEventHandler inputEventHandler) {
        this.inputEventHandler = inputEventHandler;
    }

    public void setVendingMachineAllergensObserver(VendingMachineAllergensObserver vendingMachineAllergensObserver) {
        this.vendingMachineAllergensObserver = vendingMachineAllergensObserver;
    }

    public void setVendingMachineCapacityObserver(VendingMachineCapacityObserver vendingMachineCapacityObserver) {
        this.vendingMachineCapacityObserver = vendingMachineCapacityObserver;
    }
}


