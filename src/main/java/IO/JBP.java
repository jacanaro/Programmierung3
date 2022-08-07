package IO;

import domain_logic.Allergen;

import domain_logic.VendingMachine;

import domain_logic.ManufacturerImpl;

import domain_logic.CakeImpl;

import observer_pattern.ObservableVendingMachine;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class JBP {
    public boolean JBPSave(VendingMachine automat) {
        VendingMachinePOJO vendingMachinePOJO = new VendingMachinePOJO();
        vendingMachinePOJO.setCapacity(automat.getCAPACITY());
        for (ManufacturerImpl h : automat.getHerstellerSet()) {
            if (h != null) {
                ManufacturerPojo herstellerPojo = new ManufacturerPojo();
                herstellerPojo.setHerstellerName(h.getName());
                vendingMachinePOJO.herstellerSet.add(herstellerPojo);
            }
        }
        for (CakeImpl k : automat.getVerkaufsobjekte()) {
            if (k != null) {
                CakePojo cakePojo = new CakePojo();
                if (k.getAllergene() != null) cakePojo.setAllergene(k.getAllergene());
                cakePojo.setFachnummer(k.getFachnummer());
                if (k.getKuchentyp() != null) cakePojo.setKuchentyp(k.getKuchentyp());
                if (k.getHaltbarkeit() != null) cakePojo.setHaltbarkeit(k.getHaltbarkeit().toString());
                if (k.getHersteller() != null) cakePojo.setHersteller(k.getHersteller().getName());
                if (k.getInspektionsdatum() != null)
                    cakePojo.setInspektionsdatum(k.getInspektionsdatum());
                if (k.getKremsorte() != null) cakePojo.setKremsorte(k.getKremsorte());
                if (k.getObstsorte() != null) cakePojo.setObstsorte(k.getObstsorte());
                if (k.getNaehrwert() != 0) cakePojo.setNaehrwert(k.getNaehrwert());
                if (k.getPreis() != null) cakePojo.setPreis(k.getPreis().toString());
                vendingMachinePOJO.verkaufsobjektListe.add(cakePojo);
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
            encoder.writeObject(vendingMachinePOJO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public VendingMachine JBPLoad() {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("beanItems.xml")));) {
            decoder.setExceptionListener(new ExceptionListener() {
                @Override
                public void exceptionThrown(Exception e) {
                    System.out.println("got exception. e=" + e);
                    e.printStackTrace();
                }
            });

            VendingMachinePOJO loadedVendingMachinePojo = (VendingMachinePOJO) decoder.readObject();

            //reconstruct original automat
            VendingMachine automatReloaded = new ObservableVendingMachine(loadedVendingMachinePojo.getCapacity());
            for (ManufacturerPojo hPojo : loadedVendingMachinePojo.getHerstellerSet()) {
                automatReloaded.addHersteller(new ManufacturerImpl(hPojo.getHerstellerName()));
            }
            List<CakePojo> kuchenpojoList = (List<CakePojo>) loadedVendingMachinePojo.getVerkaufsobjektListe();
            Collections.sort(kuchenpojoList, new ProductsIdComparator());
            ArrayList<CakeImpl> reconstructedVerkaufsobjektliste = new ArrayList<>();
            for (CakePojo k : kuchenpojoList) {
                if (k != null) {
                    String kremsorte = null;
                    ManufacturerImpl hersteller = null;
                    HashSet<Allergen> allergene = null;
                    int naehrwert = 0;
                    Duration haltbarkeit = null;
                    String obstsorte = null;
                    BigDecimal preis = null;
                    String kuchentyp = null;

                    kuchentyp = k.getKuchentyp();
                    hersteller = new ManufacturerImpl(k.getHerstellerName());
                    preis = new BigDecimal(k.getPreis());
                    if (k.getNaehrwert() != 0) naehrwert = k.getNaehrwert();
                    if (k.getKremsorte() != null) kremsorte = k.getKremsorte();
                    if (k.getObstsorte() != null) obstsorte = k.getObstsorte();
                    if (k.getAllergene() != null) allergene = k.getAllergene();
                    if (k.getHaltbarkeit() != null) haltbarkeit = Duration.parse(k.getHaltbarkeit());

                    switch (kuchentyp) {
                        case "Kremkuchen":
                            CakeImpl kremKuchen = new CakeImpl(hersteller, allergene, kremsorte, naehrwert, haltbarkeit, preis);
                            kremKuchen.setFachnummer(k.getFachnummer());
                            kremKuchen.setInspektionsdatum(k.getInspektionsdatum());
                            reconstructedVerkaufsobjektliste.add(kremKuchen);
                            break;
                        case "Obstkuchen":
                            CakeImpl obstKuchen = new CakeImpl(hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis);
                            obstKuchen.setFachnummer(k.getFachnummer());
                            obstKuchen.setInspektionsdatum(k.getInspektionsdatum());
                            reconstructedVerkaufsobjektliste.add(obstKuchen);
                            break;
                        case "Obsttorte":
                            CakeImpl obstTorte = new CakeImpl(kremsorte, hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis);
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
