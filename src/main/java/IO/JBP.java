package IO;

import domainLogic.automat.Allergen;

import domainLogic.automat.Automat;

import domainLogic.automat.HerstellerImplementierung;

import domainLogic.automat.KuchenImplementierung;

import observerPattern.ObservableAutomat;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class JBP {
    public boolean JBPSave(Automat automat) {
        IO.AutomatPOJO automatPOJO = new IO.AutomatPOJO();
        automatPOJO.setCapacity(automat.getCAPACITY());
        for (HerstellerImplementierung h : automat.getHerstellerSet()) {
            if (h != null) {
                IO.HerstellerImplementierungPojo herstellerPojo = new IO.HerstellerImplementierungPojo();
                herstellerPojo.setHerstellerName(h.getName());
                automatPOJO.herstellerSet.add(herstellerPojo);
            }
        }
        for (KuchenImplementierung k : automat.getVerkaufsobjekte()) {
            if (k != null) {
                IO.KuchenImplementierungPojo kuchenImplementierungPojo = new IO.KuchenImplementierungPojo();
                if (k.getAllergene() != null) kuchenImplementierungPojo.setAllergene(k.getAllergene());
                kuchenImplementierungPojo.setFachnummer(k.getFachnummer());
                if (k.getKuchentyp() != null) kuchenImplementierungPojo.setKuchentyp(k.getKuchentyp());
                if (k.getHaltbarkeit() != null) kuchenImplementierungPojo.setHaltbarkeit(k.getHaltbarkeit().toString());
                if (k.getHersteller() != null) kuchenImplementierungPojo.setHersteller(k.getHersteller().getName());
                if (k.getInspektionsdatum() != null)
                    kuchenImplementierungPojo.setInspektionsdatum(k.getInspektionsdatum());
                if (k.getKremsorte() != null) kuchenImplementierungPojo.setKremsorte(k.getKremsorte());
                if (k.getObstsorte() != null) kuchenImplementierungPojo.setObstsorte(k.getObstsorte());
                if (k.getNaehrwert() != 0) kuchenImplementierungPojo.setNaehrwert(k.getNaehrwert());
                if (k.getPreis() != null) kuchenImplementierungPojo.setPreis(k.getPreis().toString());
                automatPOJO.verkaufsobjektListe.add(kuchenImplementierungPojo);
            }
        }

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("beanItems.xml")));
        ) {
            encoder.setExceptionListener(new ExceptionListener() {
                @Override
                public void exceptionThrown(Exception e) {
                    System.out.println("got exception. e=" + e);
                    e.printStackTrace();
                }
            });
            encoder.writeObject(automatPOJO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Automat JBPLoad() {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("beanItems.xml")));) {
            decoder.setExceptionListener(new ExceptionListener() {
                @Override
                public void exceptionThrown(Exception e) {
                    System.out.println("got exception. e=" + e);
                    e.printStackTrace();
                }
            });

            IO.AutomatPOJO loadedAutomatPojo = (IO.AutomatPOJO) decoder.readObject();

            //reconstruct original automat
            Automat automatReloaded = new ObservableAutomat(loadedAutomatPojo.getCapacity());
            for (IO.HerstellerImplementierungPojo hPojo : loadedAutomatPojo.getHerstellerSet()) {
                automatReloaded.addHersteller(new HerstellerImplementierung(hPojo.getHerstellerName()));
            }
            List<IO.KuchenImplementierungPojo> kuchenpojoList = (List<IO.KuchenImplementierungPojo>) loadedAutomatPojo.getVerkaufsobjektListe();
            Collections.sort(kuchenpojoList, new IO.SortKuchen());
            ArrayList<KuchenImplementierung> reconstructedVerkaufsobjektliste = new ArrayList<>();
            for (IO.KuchenImplementierungPojo k : kuchenpojoList) {
                if (k != null) {
                    String kremsorte = null;
                    HerstellerImplementierung hersteller = null;
                    HashSet<Allergen> allergene = null;
                    int naehrwert = 0;
                    Duration haltbarkeit = null;
                    String obstsorte = null;
                    BigDecimal preis = null;
                    String kuchentyp = null;

                    kuchentyp = k.getKuchentyp();
                    hersteller = new HerstellerImplementierung(k.getHerstellerName());
                    preis = new BigDecimal(k.getPreis());
                    if (k.getNaehrwert() != 0) naehrwert = k.getNaehrwert();
                    if (k.getKremsorte() != null) kremsorte = k.getKremsorte();
                    if (k.getObstsorte() != null) obstsorte = k.getObstsorte();
                    if (k.getAllergene() != null) allergene = k.getAllergene();
                    if (k.getHaltbarkeit() != null) haltbarkeit = Duration.parse(k.getHaltbarkeit());

                    switch (kuchentyp) {
                        case "Kremkuchen":
                            KuchenImplementierung kremKuchen = new KuchenImplementierung(hersteller, allergene, kremsorte, naehrwert, haltbarkeit, preis);
                            kremKuchen.setFachnummer(k.getFachnummer());
                            kremKuchen.setInspektionsdatum(k.getInspektionsdatum());
                            reconstructedVerkaufsobjektliste.add(kremKuchen);
                            break;
                        case "Obstkuchen":
                            KuchenImplementierung obstKuchen = new KuchenImplementierung(hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis);
                            obstKuchen.setFachnummer(k.getFachnummer());
                            obstKuchen.setInspektionsdatum(k.getInspektionsdatum());
                            reconstructedVerkaufsobjektliste.add(obstKuchen);
                            break;
                        case "Obsttorte":
                            KuchenImplementierung obstTorte = new KuchenImplementierung(kremsorte, hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis);
                            obstTorte.setFachnummer(k.getFachnummer());
                            obstTorte.setInspektionsdatum(k.getInspektionsdatum());
                            reconstructedVerkaufsobjektliste.add(obstTorte);
                            break;
                        default:
                            break;
                    }
                }
            }

            for (int i = 0; i < reconstructedVerkaufsobjektliste.size(); i++) {
                automatReloaded.addVerkaufsobjekt(reconstructedVerkaufsobjektliste.get(i));
                automatReloaded.getVerkaufsobjekte().get(i).setInspektionsdatum(reconstructedVerkaufsobjektliste.get(i).getInspektionsdatum());
            }

            return automatReloaded;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
