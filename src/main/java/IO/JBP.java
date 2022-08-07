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
        for (ManufacturerImpl h : automat.getManufacturers()) {
            if (h != null) {
                ManufacturerPojo herstellerPojo = new ManufacturerPojo();
                herstellerPojo.setHerstellerName(h.getName());
                vendingMachinePOJO.herstellerSet.add(herstellerPojo);
            }
        }
        for (CakeImpl k : automat.getProducts()) {
            if (k != null) {
                CakePojo cakePojo = new CakePojo();
                if (k.getAllergens() != null) cakePojo.setAllergene(k.getAllergens());
                cakePojo.setFachnummer(k.getVendingMachineSlot());
                if (k.getTypeOfProduct() != null) cakePojo.setKuchentyp(k.getTypeOfProduct());
                if (k.getShelfLive() != null) cakePojo.setHaltbarkeit(k.getShelfLive().toString());
                if (k.getManufacturer() != null) cakePojo.setHersteller(k.getManufacturer().getName());
                if (k.getDateOfInspection() != null)
                    cakePojo.setInspektionsdatum(k.getDateOfInspection());
                if (k.getCreamFlavor() != null) cakePojo.setKremsorte(k.getCreamFlavor());
                if (k.getTypeOfFruit() != null) cakePojo.setObstsorte(k.getTypeOfFruit());
                if (k.getNutritionalScore() != 0) cakePojo.setNaehrwert(k.getNutritionalScore());
                if (k.getPrice() != null) cakePojo.setPreis(k.getPrice().toString());
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
                automatReloaded.addManufacturer(new ManufacturerImpl(hPojo.getHerstellerName()));
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
                            kremKuchen.setVendingMachineSlot(k.getFachnummer());
                            kremKuchen.setDateOfInspection(k.getInspektionsdatum());
                            reconstructedVerkaufsobjektliste.add(kremKuchen);
                            break;
                        case "Obstkuchen":
                            CakeImpl obstKuchen = new CakeImpl(hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis);
                            obstKuchen.setVendingMachineSlot(k.getFachnummer());
                            obstKuchen.setDateOfInspection(k.getInspektionsdatum());
                            reconstructedVerkaufsobjektliste.add(obstKuchen);
                            break;
                        case "Obsttorte":
                            CakeImpl obstTorte = new CakeImpl(kremsorte, hersteller, allergene, naehrwert, haltbarkeit, obstsorte, preis);
                            obstTorte.setVendingMachineSlot(k.getFachnummer());
                            obstTorte.setDateOfInspection(k.getInspektionsdatum());
                            reconstructedVerkaufsobjektliste.add(obstTorte);
                            break;
                        default:
                            break;
                    }
                }
            }

            for (int i = 0; i < reconstructedVerkaufsobjektliste.size(); i++) {
                automatReloaded.addProduct(reconstructedVerkaufsobjektliste.get(i));
                automatReloaded.getProducts().get(i).setDateOfInspection(reconstructedVerkaufsobjektliste.get(i).getDateOfInspection());
            }

            return automatReloaded;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
