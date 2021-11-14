package CLI;

import eventSystem.InputEvent;
import eventSystem.InputEventHandler;
import log.Log;
import observerPattern.AutomatAllergeneObserver;
import observerPattern.AutomatCapacityObserver;
import observerPattern.ObservableAutomat;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

// Internetquelle: Java logging explained in 3 min. https://www.youtube.com/watch?v=4Bpg5i4tUFg&list=PLGNCbZkJtqa7W3LR101bjoYSI0ignYKCl&index=3
//aufgerufen am 07.10.2021

public class ConsoleReader {
    private InputEventHandler handler;
    private ObservableAutomat a;
    private AutomatAllergeneObserver o;
    private AutomatCapacityObserver o2;
    private Scanner scanner;
    private ArrayList<String> modi = new ArrayList<>();
    private String option = "";
    private String option2 = "";
    private Log my_log;

    public ConsoleReader(ObservableAutomat a, AutomatCapacityObserver o2, Scanner scanner) {
        this.a = a;
        this.o2 = o2;
        this.scanner = scanner;
        modi.add(":c");
        modi.add(":d");
        modi.add(":r");
        modi.add(":u");
        modi.add(":p");
    }

    public ConsoleReader(ObservableAutomat observableAutomat, AutomatAllergeneObserver allergeneObserver,
                         AutomatCapacityObserver capacityObserver, Scanner scanner) {
        this.a = observableAutomat;
        this.o = allergeneObserver;
        this.o2 = capacityObserver;
        this.scanner = scanner;
        modi.add(":c");
        modi.add(":d");
        modi.add(":r");
        modi.add(":u");
        modi.add(":p");
    }

    public ConsoleReader(ObservableAutomat observableAutomat, AutomatAllergeneObserver allergeneObserver,
                         AutomatCapacityObserver capacityObserver, Scanner scanner, String loggingLanguage) throws IOException {
        this.a = observableAutomat;
        this.o = allergeneObserver;
        this.o2 = capacityObserver;
        this.scanner = scanner;
        modi.add(":c");
        modi.add(":d");
        modi.add(":r");
        modi.add(":u");
        modi.add(":p");
        my_log = Log.getInstance("log.txt");
        my_log.setLanguage(loggingLanguage);
    }

    public ConsoleReader(ObservableAutomat a, AutomatCapacityObserver o2, Scanner scanner,String loggingLanguage) throws IOException{
        this.a = a;
        this.o2 = o2;
        this.scanner = scanner;
        modi.add(":c");
        modi.add(":d");
        modi.add(":r");
        modi.add(":u");
        modi.add(":p");
        my_log = Log.getInstance("log.txt");
        my_log.setLanguage(loggingLanguage);
    }


    public AutomatAllergeneObserver getAllergeneObserver() {
        return o;
    }

    public AutomatCapacityObserver getCapacityObserver() {
        return o2;
    }

    public Log getMy_log() {return my_log;}

    public Scanner getScanner() {
        return scanner;
    }

    public ObservableAutomat getObservableAutomat() {
        return a;
    }

    public void setObservableAutomat(ObservableAutomat a) {
        this.a = a;
    }

    public void setHandler(InputEventHandler handler) {
        this.handler = handler;
    }

    public void setO(AutomatAllergeneObserver o) {
        this.o = o;
    }

    public void setO2(AutomatCapacityObserver o2) {
        this.o2 = o2;
    }

    public String createStringOfCLIMenu() {
        StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator());
        builder.append("Console-Application: Beleg <Aaron> <Jacob> <s0570648>" + System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append(":c Wechsel in den Einfügemodus" + System.lineSeparator() +
                ":d Wechsel in den Löschmodus" + System.lineSeparator() +
                ":r Wechsel in den Anzeigemodus" + System.lineSeparator() +
                ":u Wechsel in den Änderungsmodus" + System.lineSeparator() +
                ":p Wechsel in den Persistenzmodus" + System.lineSeparator());

        return builder.toString();
    }

    public boolean checkUserInputForModi() {
        System.out.println(createStringOfCLIMenu());
        option = scanner.nextLine();
        if (modi.contains(option)) {
            return true;
        } else {
            return false;
        }
    }

    public String handleUserInputCommands() {
        option2 = scanner.nextLine();
        if (modi.contains(option2)) {
            option = option2;
            return option;
        }
        String stringForInputEvent = option + " " + option2;
        InputEvent e = new InputEvent(this, stringForInputEvent);
        if (null != this.handler) {
            try {
                handler.handle(e);
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
        boolean userEnteredValidModi = false;

        while (userEnteredValidModi == false) {
            userEnteredValidModi = checkUserInputForModi();
        }

        while (true) {
            String modus = handleUserInputCommands();
            if (modi.contains(modus)) {
                option = modus;
            }
        }
    }
}


