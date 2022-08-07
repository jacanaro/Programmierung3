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
    private StringProperty automatKuchenProperty = new SimpleStringProperty();
    private StringProperty automatHerstellerProperty = new SimpleStringProperty();
    private StringProperty automatInfoProperty = new SimpleStringProperty();
    private VendingMachine automat;
    private Log my_log;

    public ViewModel() {
        this.automatInfoProperty.set("");
        this.automatKuchenProperty.set("Kuchen im Automat:\n");
        this.automatHerstellerProperty.set("Hersteller im Automat:\n");
    }

    public StringProperty inputProperty() {
        return this.inputProperty;
    }

    public StringProperty automatKuchenProperty() {
        return this.automatKuchenProperty;
    }

    public StringProperty automatHerstellerProperty() {
        return this.automatHerstellerProperty;
    }

    public StringProperty automatInfoProperty() {
        return this.automatInfoProperty;
    }

    public void buttonClickHerstellerHinzufuegen(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        if (my_log != null) my_log.logger.info("es wird versucht, dem Automat einen Hersteller hinzuzufügen");
        boolean herstellerHinzugefügt = this.automat.addManufacturer(new ManufacturerImpl(userInputStr));
        if (herstellerHinzugefügt && my_log != null) my_log.logger.info("Hersteller wurde hinzugefügt");
        this.updateProperties();
    }

    public void buttonClickKuchenLoeschen(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        try {
            int userInputInt = Integer.parseInt(userInputStr);
            if (my_log != null) my_log.logger.info("es wird versucht, einen Kuchen zu löschen");
            boolean kuchenGelöscht = this.automat.deleteProduct(userInputInt);
            if (kuchenGelöscht && my_log != null) my_log.logger.info("Kuchen wurde gelöscht");
            this.updateProperties();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void buttonClickKuchenSortieren(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        if (my_log != null) my_log.logger.info("alle Kuchen werden abgerufen");
        ArrayList<CakeImpl> kuchenliste = this.automat.getProducts();

        switch (userInputStr) {
            case "Fachnummer":
                updateProperties();
                break;
            case "Hersteller":
                Collections.sort(kuchenliste, new ProductsManufacturerComparator());

                String kuchenlisteHerstellerSort = "";
                for (CakeImpl kuchen : kuchenliste) {
                    if (kuchen != null) kuchenlisteHerstellerSort += kuchen + "\n";
                }
                this.automatKuchenProperty.set("Kuchen im Automat:\n" + kuchenlisteHerstellerSort);
                break;
            case "Inspektionsdatum":
                Collections.sort(kuchenliste, (o1, o2) -> {
                    if (o1.getDateOfInspection() == null || o2.getDateOfInspection() == null)
                        return 0;
                    return o1.getDateOfInspection().compareTo(o2.getDateOfInspection());
                });
                String kuchenlisteStringInspektionsdatumSorted = "";
                for (CakeImpl k : kuchenliste)
                    if (k != null) kuchenlisteStringInspektionsdatumSorted += k + "\n";
                this.automatKuchenProperty.set("Kuchen im Automat:\n" + kuchenlisteStringInspektionsdatumSorted);
                break;
            case "Haltbarkeit":
                Collections.sort(kuchenliste, (o1, o2) -> {
                    if (o1.getShelfLive() == null || o2.getShelfLive() == null)
                        return 0;
                    return o1.getShelfLive().compareTo(o2.getShelfLive());
                });
                String kuchenlisteStringHaltbarkeitSorted = "";
                for (CakeImpl k : kuchenliste)
                    if (k != null) kuchenlisteStringHaltbarkeitSorted += k + "\n";
                this.automatKuchenProperty.set("Kuchen im Automat:\n" + kuchenlisteStringHaltbarkeitSorted);
                break;
            default:
                break;
        }
    }

    public void buttonClickKuchenHinzufuegen(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        String[] userInputStrArr = userInputStr.split(" ");
        String kremsorte = null;
        ManufacturerImpl hersteller = null;
        HashSet<Allergen> allergene = new HashSet<>();
        int naehrwert = 0;
        Duration haltbarkeit = null;
        String obstsorte = null;
        BigDecimal preis = null;
        String kuchentyp = null;
        try {
        kuchentyp = userInputStrArr[0];

        hersteller = new ManufacturerImpl(userInputStrArr[1]);

        String[] vorUndNachkommaStellePreis = userInputStrArr[2].split(",");
        String preisFormatiert = "";
        if (vorUndNachkommaStellePreis.length > 1) {
            preisFormatiert = vorUndNachkommaStellePreis[0] + ".";
            for (int k = 1; k < vorUndNachkommaStellePreis.length; k++) {
                preisFormatiert = preisFormatiert + vorUndNachkommaStellePreis[k];
            }
        } else {
            preisFormatiert = vorUndNachkommaStellePreis[0];
        }


        preis = new BigDecimal(preisFormatiert);

            naehrwert = Integer.parseInt(userInputStrArr[3]);

            int haltbarkeitInStunden = Integer.parseInt(userInputStrArr[4]);
            haltbarkeit = Duration.ofHours(haltbarkeitInStunden);

        String allergeneKommaSeparriert = userInputStrArr[5];
        String[] allergeneArray = allergeneKommaSeparriert.split(",");
        for (String j : allergeneArray) {
            StringBuffer sb = new StringBuffer();
            Matcher m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(j);
            while (m.find()) {
                m.appendReplacement(sb, m.group(1).toUpperCase() + m.group(2).toLowerCase());
            }
            String formattedAllergen = m.appendTail(sb).toString();
            if (Allergen.valueOf(formattedAllergen) != null) allergene.add(Allergen.valueOf(formattedAllergen));
        }

        switch (kuchentyp) {
            case "Kremkuchen":
                kremsorte = userInputStrArr[6];
                if (my_log != null) my_log.logger.info("es wird versucht, dem Automat einen Kremkuchen hinzuzufügen");
                VendingMachineErrorCodes vendingMachineErrorCodes = this.automat.addProduct(new CakeImpl(hersteller, allergene, kremsorte, naehrwert, haltbarkeit, preis));
                if (vendingMachineErrorCodes == null && my_log != null) my_log.logger.info("Kremkuchen wurde hinzugefügt");
                break;
            case "Obstkuchen":
                obstsorte = userInputStrArr[6];
                if (my_log != null) my_log.logger.info("es wird versucht, dem Automat einen Obstkuchen hinzuzufügen");
                VendingMachineErrorCodes vendingMachineErrorCodes2 = this.automat.addProduct(new CakeImpl(hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis));
                if (vendingMachineErrorCodes2 == null && my_log != null) my_log.logger.info("Obstkuchen wurde hinzugefügt");
                break;
            case "Obsttorte":
                obstsorte = userInputStrArr[6];
                kremsorte = userInputStrArr[7];
                if (my_log != null) my_log.logger.info("es wird versucht, dem Automat eine Obsttorte hinzuzufügen");
                VendingMachineErrorCodes vendingMachineErrorCodes3 = this.automat.addProduct(new CakeImpl(kremsorte, hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis));
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

    public void buttonClickKuchenInspizieren(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        try {
            int fachnummer = Integer.parseInt(userInputStr);
            if (my_log != null) my_log.logger.info("es wird versucht, eine Inspektion durchzuführen");
            CakeImpl kuchenImplementierung = this.automat.doInspection(fachnummer);
            if (kuchenImplementierung != null && my_log != null) my_log.logger.info("Inspektion wurde durchgeführt");
            this.updateProperties();
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    public void buttonClickKuchenPersistieren(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        switch (userInputStr) {
            case "saveJOS":
                this.automat.serializeVendingMachine("automaten.ser", this.automat);
                this.automatInfoProperty.set("Der Zustand des Automaten wurde mittels JOS gespeichert");
                if (my_log != null) my_log.logger.info("Der Zustand des Automaten wurde mittels JOS gespeichert");
                break;
            case "loadJOS":
                VendingMachine loadedAutomat = VendingMachine.deserializeVendingMachine("automaten.ser");
                this.setAutomat(loadedAutomat);
                this.automatInfoProperty.set("Der Zustand des Automaten wurde mittels JOS geladen");
                if (my_log != null) my_log.logger.info("Der Zustand des Automaten wurde mittels JOS geladen");
                break;
            case "saveJBP":
                JBP jbpObj = new JBP();
                jbpObj.JBPSave(this.automat);
                this.automatInfoProperty.set("Der Zustand des Automaten wurde mittels JBP gespeichert");
                if (my_log != null) my_log.logger.info("Der Zustand des Automaten wurde mittels JBP gespeichert");
                break;
            case "loadJBP":
                JBP jbpObj2 = new JBP();
                jbpObj2.JBPLoad();
                this.setAutomat(jbpObj2.JBPLoad());
                this.automatInfoProperty.set("Der Zustand des Automaten wurde mittels JBP geladen");
                if (my_log != null) my_log.logger.info("Der Zustand des Automaten wurde mittels JBP geladen");
                break;
            default:
                break;
        }
    }

    public void buttonClickAllergeneAnzeigen(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        String[] userInputStrArr = userInputStr.split(" ");
        if (userInputStrArr[0].equals("allergene")) {
            if (userInputStrArr[1].equals("i")) {
                if (my_log != null) my_log.logger.info("es werden alle Allergene die im Automat existieren ausgegeben");
                this.automatInfoProperty.set("Allergene die im Automat vertreten sind: \n" + this.automat.getAllergens(true).toString());
            } else if (userInputStrArr[1].equals("e")) {
                if (my_log != null)
                    my_log.logger.info("es werden alle Allergene die nicht im Automat existieren ausgegeben");
                this.automatInfoProperty.set("Allergene die nicht im Automat vertreten sind: \n" + this.automat.getAllergens(false).toString());
            }
        }
    }

    public void buttonClickHerstellerLöschen(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        if (my_log != null) my_log.logger.info("es wird versucht, einen Hersteller zu löschen");
        boolean herstellerGelöscht = this.automat.deleteManufacturer(userInputStr);
        if (herstellerGelöscht && my_log != null) my_log.logger.info("Hersteller wurde gelöscht");
        this.updateProperties();
    }

    private void updateProperties() {
        this.automatInfoProperty.set("");
        String kuchenliste = "";
        if (my_log != null) my_log.logger.info("alle Kuchen werden abgerufen");
        for (CakeImpl kuchen : this.automat.getProducts()) {
            if (kuchen != null) kuchenliste += kuchen + "\n";
        }
        this.automatKuchenProperty.set("Kuchen im Automat:\n" + kuchenliste);
        this.automatHerstellerProperty.set("Hersteller im Automat:\n" + this.automat.listManufacturersWithProductsCounted());
    }

    public String getInput() {
        return this.inputProperty.get();
    }

    public String getAutomatKuchen() {
        return this.automatKuchenProperty.get();
    }

    public String getAutomatHersteller() {
        return this.automatHerstellerProperty.get();
    }

    public String getAutomatInfo() {
        return this.automatInfoProperty.get();
    }
    public void setLogger(String loggingLanguage) throws IOException {
        this.my_log = Log.getInstance("log.txt");
        this.my_log.setLanguage(loggingLanguage);
    }

    public void setAutomat(VendingMachine automat) {
        this.automat = automat;
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
