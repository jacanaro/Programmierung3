package eventSystem;

import CLI.ConsoleReader;
import domainLogic.automat.Allergen;
import domainLogic.automat.AutomatErrorCodes;
import domainLogic.automat.HerstellerImplementierung;
import domainLogic.automat.KuchenImplementierung;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputEventListenerAddKremkuchen implements InputEventListener {
    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getText()) {
            String userInput = event.getText();
            String modus = userInput.substring(0, 2);
            if (modus.equals(":c")) {
                String[] userInputStrArr = userInput.substring(2).split(" ");
                if (userInputStrArr.length > 2) {
                    for (int i = 0; i < userInputStrArr.length; i++) {
                        if (userInputStrArr[i].equals("Kremkuchen")) {
                            HerstellerImplementierung hersteller = null;
                            HashSet<Allergen> allergene = new HashSet<>();
                            int naehrwert = 0;
                            Duration haltbarkeit = null;
                            String kremsorte = null;
                            BigDecimal preis = null;
                            String kuchentyp = null;

                            kuchentyp = userInputStrArr[i];
                            hersteller = new HerstellerImplementierung(userInputStrArr[i + 1]);

                            String stringPreis = userInputStrArr[i + 2];
                            String[] vorUndNachkommaStellePreis = stringPreis.split(",");
                            String preisFormatiert="";
                            if (vorUndNachkommaStellePreis.length > 1) {
                                 preisFormatiert=vorUndNachkommaStellePreis[0]+".";
                                for (int k = 1; k < vorUndNachkommaStellePreis.length; k++) {
                                    preisFormatiert=preisFormatiert+vorUndNachkommaStellePreis[k];
                                }
                            }else {
                                preisFormatiert=vorUndNachkommaStellePreis[0];
                            }

                            preis = new BigDecimal(preisFormatiert);

                            try {
                                naehrwert = Integer.parseInt(userInputStrArr[i + 3]);
                            } catch (Exception e) {
                                System.out.println(e);
                            }

                            int haltbarkeitInStunden = Integer.parseInt(userInputStrArr[i + 4]);
                            haltbarkeit = Duration.ofHours(haltbarkeitInStunden);

                            String allergeneKommaSeparriert = userInputStrArr[i + 5];
                            String[] allergeneArray = allergeneKommaSeparriert.split(",");
                            for (String j : allergeneArray) {
                                StringBuffer sb = new StringBuffer();
                                Matcher m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(j);
                                while (m.find()) {
                                    m.appendReplacement(sb, m.group(1).toUpperCase() + m.group(2).toLowerCase());
                                }
                                String formattedAllergen = m.appendTail(sb).toString();
                                if (Allergen.valueOf(formattedAllergen) != null)
                                    allergene.add(Allergen.valueOf(formattedAllergen));
                            }
                            kremsorte = userInputStrArr[i + 6];
                            ConsoleReader c = (ConsoleReader) event.getSource();
                            if(c.getMy_log()!=null)c.getMy_log().logger.info("es wird versucht, dem Automat einen Kremkuchen hinzuzufügen");
                            AutomatErrorCodes automatErrorCodes=c.getObservableAutomat().addVerkaufsobjekt(new KuchenImplementierung
                                    (hersteller, allergene, kremsorte, naehrwert, haltbarkeit, preis));
                            if(automatErrorCodes==null && c.getMy_log()!=null)c.getMy_log().logger.info("Kremkuchen wurde hinzugefügt");
                        }
                    }
                }
            }
        }
    }
}
