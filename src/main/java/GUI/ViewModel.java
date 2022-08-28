package GUI;


import IO.JBP;
import domain_logic.*;
import log.Log;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewModel {
    private StringProperty inputProperty = new SimpleStringProperty();
    private StringProperty vendingMachineProductsProperty = new SimpleStringProperty();
    private StringProperty manufacturerProperty = new SimpleStringProperty();
    private StringProperty vendingMachineInfoProperty = new SimpleStringProperty();
    private VendingMachine vendingMachine;
    private Log my_log;

    public ViewModel() {
        this.vendingMachineInfoProperty.set("");
        this.vendingMachineProductsProperty.set("Kuchen im Automat:\n");
        this.manufacturerProperty.set("Hersteller im Automat:\n");
    }

    public void addManufacturerOnButtonClick(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        if (my_log != null) my_log.logger.info("es wird versucht, dem Automat einen Hersteller hinzuzufügen");
        boolean manufacturerAdded = this.vendingMachine.addManufacturer(new ManufacturerImpl(userInputStr));
        if (manufacturerAdded && my_log != null) my_log.logger.info("Hersteller wurde hinzugefügt");
        this.updateProperties();
    }

    public void deleteProductOnButtonClick(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        try {
            int userInputInt = Integer.parseInt(userInputStr);
            if (my_log != null) my_log.logger.info("es wird versucht, einen Kuchen zu löschen");
            boolean productDeleted = this.vendingMachine.deleteProduct(userInputInt);
            if (productDeleted && my_log != null) my_log.logger.info("Kuchen wurde gelöscht");
            this.updateProperties();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sortProductsOnButtonClick(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        if (my_log != null) my_log.logger.info("alle Kuchen werden abgerufen");
        ArrayList<CakeImpl> products = this.vendingMachine.getProducts();

        switch (userInputStr) {
            case "Fachnummer":
                updateProperties();
                break;
            case "Hersteller":
                Collections.sort(products, new ProductsManufacturerComparator());

                String productsSortedByManufacturer = "";
                for (CakeImpl product : products) {
                    if (product != null) productsSortedByManufacturer += product + "\n";
                }
                this.vendingMachineProductsProperty.set("Kuchen im Automat:\n" + productsSortedByManufacturer);
                break;
            case "Inspektionsdatum":
                Collections.sort(products, (o1, o2) -> {
                    if (o1.getDateOfInspection() == null || o2.getDateOfInspection() == null)
                        return 0;
                    return o1.getDateOfInspection().compareTo(o2.getDateOfInspection());
                });
                String productsSortedByInspectiondate = "";
                for (CakeImpl product : products)
                    if (product != null) productsSortedByInspectiondate += product + "\n";
                this.vendingMachineProductsProperty.set("Kuchen im Automat:\n" + productsSortedByInspectiondate);
                break;
            case "Haltbarkeit":
                Collections.sort(products, (o1, o2) -> {
                    if (o1.getShelfLife() == null || o2.getShelfLife() == null)
                        return 0;
                    return o1.getShelfLife().compareTo(o2.getShelfLife());
                });
                String productsSortedByShelfLife = "";
                for (CakeImpl product : products)
                    if (product != null) productsSortedByShelfLife += product + "\n";
                this.vendingMachineProductsProperty.set("Kuchen im Automat:\n" + productsSortedByShelfLife);
                break;
            default:
                break;
        }
    }

    public void addProductOnButtonClick(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        String[] userInputStrArr = userInputStr.split(" ");
        String creamFlavor = null;
        ManufacturerImpl manufacturer = null;
        HashSet<Allergen> allergens = new HashSet<>();
        int nutritionalScore = 0;
        Duration shelfLife = null;
        String typeOfFruit = null;
        BigDecimal price = null;
        String typeOfProduct = null;
        try {
        typeOfProduct = userInputStrArr[0];

        manufacturer = new ManufacturerImpl(userInputStrArr[1]);

        String[] preAndPostCommaDigit = userInputStrArr[2].split(",");
        String formattedPrice = "";
        if (preAndPostCommaDigit.length > 1) {
            formattedPrice = preAndPostCommaDigit[0] + ".";
            for (int k = 1; k < preAndPostCommaDigit.length; k++) {
                formattedPrice = formattedPrice + preAndPostCommaDigit[k];
            }
        } else {
            formattedPrice = preAndPostCommaDigit[0];
        }


        price = new BigDecimal(formattedPrice);

            nutritionalScore = Integer.parseInt(userInputStrArr[3]);

            int shelfLifeInHours = Integer.parseInt(userInputStrArr[4]);
            shelfLife = Duration.ofHours(shelfLifeInHours);

        String commaSeperatedAllergens = userInputStrArr[5];
        String[] allergensArray = commaSeperatedAllergens.split(",");
        for (String allergenString : allergensArray) {
            StringBuffer stringBuffer = new StringBuffer();
            Matcher matcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(allergenString);
            while (matcher.find()) {
                matcher.appendReplacement(stringBuffer, matcher.group(1).toUpperCase() + matcher.group(2).toLowerCase());
            }
            String formattedAllergen = matcher.appendTail(stringBuffer).toString();
            if (Allergen.valueOf(formattedAllergen) != null) allergens.add(Allergen.valueOf(formattedAllergen));
        }

        switch (typeOfProduct) {
            case "Kremkuchen":
                creamFlavor = userInputStrArr[6];
                if (my_log != null) my_log.logger.info("es wird versucht, dem Automat einen Kremkuchen hinzuzufügen");
                VendingMachineErrorCodes vendingMachineErrorCodes = this.vendingMachine.addProduct(new CakeImpl(manufacturer, allergens, creamFlavor, nutritionalScore, shelfLife, price));
                if (vendingMachineErrorCodes == null && my_log != null) my_log.logger.info("Kremkuchen wurde hinzugefügt");
                break;
            case "Obstkuchen":
                typeOfFruit = userInputStrArr[6];
                if (my_log != null) my_log.logger.info("es wird versucht, dem Automat einen Obstkuchen hinzuzufügen");
                VendingMachineErrorCodes vendingMachineErrorCodes2 = this.vendingMachine.addProduct(new CakeImpl(manufacturer, allergens, nutritionalScore, shelfLife, typeOfFruit, price));
                if (vendingMachineErrorCodes2 == null && my_log != null) my_log.logger.info("Obstkuchen wurde hinzugefügt");
                break;
            case "Obsttorte":
                typeOfFruit = userInputStrArr[6];
                creamFlavor = userInputStrArr[7];
                if (my_log != null) my_log.logger.info("es wird versucht, dem Automat eine Obsttorte hinzuzufügen");
                VendingMachineErrorCodes vendingMachineErrorCodes3 = this.vendingMachine.addProduct(new CakeImpl(creamFlavor, manufacturer, allergens, nutritionalScore, shelfLife, typeOfFruit, price));
                if (vendingMachineErrorCodes3 == null && my_log != null) my_log.logger.info("Obsttorte wurde hinzugefügt");
                break;
            default:
                break;
        }
        this.updateProperties();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void inspectProductOnButtonClick(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        try {
            int vendingMachineSlot = Integer.parseInt(userInputStr);
            if (my_log != null) my_log.logger.info("es wird versucht, eine Inspektion durchzuführen");
            CakeImpl product = this.vendingMachine.doInspection(vendingMachineSlot);
            if (product != null && my_log != null) my_log.logger.info("Inspektion wurde durchgeführt");
            this.updateProperties();
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    public void saveVendingMachineToFile(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        switch (userInputStr) {
            case "saveJOS":
                this.vendingMachine.serializeVendingMachine("automaten.ser", this.vendingMachine);
                this.vendingMachineInfoProperty.set("Der Zustand des Automaten wurde mittels JOS gespeichert");
                if (my_log != null) my_log.logger.info("Der Zustand des Automaten wurde mittels JOS gespeichert");
                break;
            case "loadJOS":
                VendingMachine loadedVendingMachine = VendingMachine.deserializeVendingMachine("automaten.ser");
                this.setVendingMachine(loadedVendingMachine);
                this.vendingMachineInfoProperty.set("Der Zustand des Automaten wurde mittels JOS geladen");
                if (my_log != null) my_log.logger.info("Der Zustand des Automaten wurde mittels JOS geladen");
                break;
            case "saveJBP":
                JBP jbpObj = new JBP();
                jbpObj.JBPSave(this.vendingMachine);
                this.vendingMachineInfoProperty.set("Der Zustand des Automaten wurde mittels JBP gespeichert");
                if (my_log != null) my_log.logger.info("Der Zustand des Automaten wurde mittels JBP gespeichert");
                break;
            case "loadJBP":
                JBP jbpObj2 = new JBP();
                jbpObj2.JBPLoad();
                this.setVendingMachine(jbpObj2.JBPLoad());
                this.vendingMachineInfoProperty.set("Der Zustand des Automaten wurde mittels JBP geladen");
                if (my_log != null) my_log.logger.info("Der Zustand des Automaten wurde mittels JBP geladen");
                break;
            default:
                break;
        }
    }

    public void showAllergensOnButtonClick(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        String[] userInputStrArr = userInputStr.split(" ");
        if (userInputStrArr[0].equals("allergene")) {
            if (userInputStrArr[1].equals("i")) {
                if (my_log != null) my_log.logger.info("es werden alle Allergene die im Automat existieren ausgegeben");
                this.vendingMachineInfoProperty.set("Allergene die im Automat vertreten sind: \n" + this.vendingMachine.getAllergens(true).toString());
            } else if (userInputStrArr[1].equals("e")) {
                if (my_log != null)
                    my_log.logger.info("es werden alle Allergene die nicht im Automat existieren ausgegeben");
                this.vendingMachineInfoProperty.set("Allergene die nicht im Automat vertreten sind: \n" + this.vendingMachine.getAllergens(false).toString());
            }
        }
    }

    public void deleteManufacturerOnButtonClick(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        if (my_log != null) my_log.logger.info("es wird versucht, einen Hersteller zu löschen");
        boolean manufacturerDeleted = this.vendingMachine.deleteManufacturer(userInputStr);
        if (manufacturerDeleted && my_log != null) my_log.logger.info("Hersteller wurde gelöscht");
        this.updateProperties();
    }

    private void updateProperties() {
        this.vendingMachineInfoProperty.set("");
        String products = "";
        if (my_log != null) my_log.logger.info("alle Kuchen werden abgerufen");
        for (CakeImpl product : this.vendingMachine.getProducts()) {
            if (product != null) products += product + "\n";
        }
        this.vendingMachineProductsProperty.set("Kuchen im Automat:\n" + products);
        this.manufacturerProperty.set("Hersteller im Automat:\n" + this.vendingMachine.listManufacturersWithProductsCounted());
    }

    public String getInput() {
        return this.inputProperty.get();
    }

    public String getVendingMachineProductsProperty() {
        return this.vendingMachineProductsProperty.get();
    }

    public String getManufacturerProperty() {
        return this.manufacturerProperty.get();
    }

    public String getVendingMachineInfoProperty() {
        return this.vendingMachineInfoProperty.get();
    }

    public void setLogger(String loggingLanguage) throws IOException {
        this.my_log = Log.getInstance("log.txt");
        this.my_log.setLanguage(loggingLanguage);
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.updateProperties();
    }

    public void setInput(String value) {
        this.inputProperty.set(value);
    }

    @FXML
    private TextField input;

    @FXML
    private void initialize() {
        this.input.textProperty().bindBidirectional(this.inputProperty);
        this.input.setPromptText("[Kuchen-Typ] [Herstellername] [Preis] [Nährwert] [Haltbarkeit] [kommaseparierte Allergene] [Obstsorte] [Kremsorte]");
    }
}
