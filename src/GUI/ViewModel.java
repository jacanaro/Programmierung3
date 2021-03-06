package GUI;


import IO.JBP;
import log.Log;
import domainLogic.automat.*;
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
    private Automat automat;
    private Log my_log;

    public ViewModel() {
        this.automatInfoProperty.set("");
        this.automatKuchenProperty.set("Kuchen im Automat:\n");
        this.automatHerstellerProperty.set("Hersteller im Automat:\n");
    }

    public void setLogger(String loggingLanguage) throws IOException {
        this.my_log = Log.getInstance("log.txt");
        this.my_log.setLanguage(loggingLanguage);
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
        this.updateProperties();
    }

    public StringProperty inputProperty() {
        return this.inputProperty;
    }

    public void setInput(String value) {
        this.inputProperty.set(value);
    }

    public String getInput() {
        return this.inputProperty.get();
    }

    public StringProperty automatKuchenProperty() {
        return this.automatKuchenProperty;
    }

    public String getAutomatKuchen() {
        return this.automatKuchenProperty.get();
    }

    public StringProperty automatHerstellerProperty() {
        return this.automatHerstellerProperty;
    }

    public String getAutomatHersteller() {
        return this.automatHerstellerProperty.get();
    }

    public StringProperty automatInfoProperty() {
        return this.automatInfoProperty;
    }

    public String getAutomatInfo() {
        return this.automatInfoProperty.get();
    }


    public void buttonClickHerstellerHinzufuegen(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        if (my_log != null) my_log.logger.info("es wird versucht, dem Automat einen Hersteller hinzuzuf??gen");
        boolean herstellerHinzugef??gt = this.automat.addHersteller(new HerstellerImplementierung(userInputStr));
        if (herstellerHinzugef??gt && my_log != null) my_log.logger.info("Hersteller wurde hinzugef??gt");
        this.updateProperties();
    }

    public void buttonClickKuchenLoeschen(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        try {
            int userInputInt = Integer.parseInt(userInputStr);
            if (my_log != null) my_log.logger.info("es wird versucht, einen Kuchen zu l??schen");
            boolean kuchenGel??scht = this.automat.deleteVerkaufsobjekt(userInputInt);
            if (kuchenGel??scht && my_log != null) my_log.logger.info("Kuchen wurde gel??scht");
            this.updateProperties();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void buttonClickKuchenSortieren(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        if (my_log != null) my_log.logger.info("alle Kuchen werden abgerufen");
        ArrayList<KuchenImplementierung> kuchenliste = this.automat.getVerkaufsobjekte();

        switch (userInputStr) {
            case "Fachnummer":
                updateProperties();
                break;
            case "Hersteller":
                Collections.sort(kuchenliste, new SortKuchenHersteller());

                String kuchenlisteHerstellerSort = "";
                for (KuchenImplementierung kuchen : kuchenliste) {
                    if (kuchen != null) kuchenlisteHerstellerSort += kuchen + "\n";
                }
                this.automatKuchenProperty.set("Kuchen im Automat:\n" + kuchenlisteHerstellerSort);
                break;
            case "Inspektionsdatum":
                Collections.sort(kuchenliste, (o1, o2) -> {
                    if (o1.getInspektionsdatum() == null || o2.getInspektionsdatum() == null)
                        return 0;
                    return o1.getInspektionsdatum().compareTo(o2.getInspektionsdatum());
                });
                String kuchenlisteStringInspektionsdatumSorted = "";
                for (KuchenImplementierung k : kuchenliste)
                    if (k != null) kuchenlisteStringInspektionsdatumSorted += k + "\n";
                this.automatKuchenProperty.set("Kuchen im Automat:\n" + kuchenlisteStringInspektionsdatumSorted);
                break;
            case "Haltbarkeit":
                Collections.sort(kuchenliste, (o1, o2) -> {
                    if (o1.getHaltbarkeit() == null || o2.getHaltbarkeit() == null)
                        return 0;
                    return o1.getHaltbarkeit().compareTo(o2.getHaltbarkeit());
                });
                String kuchenlisteStringHaltbarkeitSorted = "";
                for (KuchenImplementierung k : kuchenliste)
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
        HerstellerImplementierung hersteller = null;
        HashSet<Allergen> allergene = new HashSet<>();
        int naehrwert = 0;
        Duration haltbarkeit = null;
        String obstsorte = null;
        BigDecimal preis = null;
        String kuchentyp = null;
        try {
        kuchentyp = userInputStrArr[0];

        hersteller = new HerstellerImplementierung(userInputStrArr[1]);

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
                if (my_log != null) my_log.logger.info("es wird versucht, dem Automat einen Kremkuchen hinzuzuf??gen");
                AutomatErrorCodes automatErrorCodes = this.automat.addVerkaufsobjekt(new KuchenImplementierung(hersteller, allergene, kremsorte, naehrwert, haltbarkeit, preis));
                if (automatErrorCodes == null && my_log != null) my_log.logger.info("Kremkuchen wurde hinzugef??gt");
                break;
            case "Obstkuchen":
                obstsorte = userInputStrArr[6];
                if (my_log != null) my_log.logger.info("es wird versucht, dem Automat einen Obstkuchen hinzuzuf??gen");
                AutomatErrorCodes automatErrorCodes2 = this.automat.addVerkaufsobjekt(new KuchenImplementierung(hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis));
                if (automatErrorCodes2 == null && my_log != null) my_log.logger.info("Obstkuchen wurde hinzugef??gt");
                break;
            case "Obsttorte":
                obstsorte = userInputStrArr[6];
                kremsorte = userInputStrArr[7];
                if (my_log != null) my_log.logger.info("es wird versucht, dem Automat eine Obsttorte hinzuzuf??gen");
                AutomatErrorCodes automatErrorCodes3 = this.automat.addVerkaufsobjekt(new KuchenImplementierung(kremsorte, hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis));
                if (automatErrorCodes3 == null && my_log != null) my_log.logger.info("Obsttorte wurde hinzugef??gt");
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
            if (my_log != null) my_log.logger.info("es wird versucht, eine Inspektion durchzuf??hren");
            KuchenImplementierung kuchenImplementierung = this.automat.doInspection(fachnummer);
            if (kuchenImplementierung != null && my_log != null) my_log.logger.info("Inspektion wurde durchgef??hrt");
            this.updateProperties();
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    public void buttonClickKuchenPersistieren(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        switch (userInputStr) {
            case "saveJOS":
                this.automat.serialize("automaten.ser", this.automat);
                this.automatInfoProperty.set("Der Zustand des Automaten wurde mittels JOS gespeichert");
                if (my_log != null) my_log.logger.info("Der Zustand des Automaten wurde mittels JOS gespeichert");
                break;
            case "loadJOS":
                Automat loadedAutomat = Automat.deserialize("automaten.ser");
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
                this.automatInfoProperty.set("Allergene die im Automat vertreten sind: \n" + this.automat.getAllergene(true).toString());
            } else if (userInputStrArr[1].equals("e")) {
                if (my_log != null)
                    my_log.logger.info("es werden alle Allergene die nicht im Automat existieren ausgegeben");
                this.automatInfoProperty.set("Allergene die nicht im Automat vertreten sind: \n" + this.automat.getAllergene(false).toString());
            }
        }
    }

    public void buttonClickHerstellerL??schen(ActionEvent actionEvent) {
        String userInputStr = this.inputProperty.get();
        if (my_log != null) my_log.logger.info("es wird versucht, einen Hersteller zu l??schen");
        boolean herstellerGel??scht = this.automat.deleteHersteller(userInputStr);
        if (herstellerGel??scht && my_log != null) my_log.logger.info("Hersteller wurde gel??scht");
        this.updateProperties();
    }

    private void updateProperties() {
        this.automatInfoProperty.set("");
        String kuchenliste = "";
        if (my_log != null) my_log.logger.info("alle Kuchen werden abgerufen");
        for (KuchenImplementierung kuchen : this.automat.getVerkaufsobjekte()) {
            if (kuchen != null) kuchenliste += kuchen + "\n";
        }
        this.automatKuchenProperty.set("Kuchen im Automat:\n" + kuchenliste);
        this.automatHerstellerProperty.set("Hersteller im Automat:\n" + this.automat.listHerstellerWithCakeCount());
    }

    @FXML
    private TextField input;

    @FXML
    private void initialize() {
        this.input.textProperty().bindBidirectional(this.inputProperty);
        this.input.setPromptText("[Kuchen-Typ] [Herstellername] [Preis] [N??hrwert] [Haltbarkeit] [kommaseparierte Allergene] [Obstsorte] [Kremsorte]");
    }
}
