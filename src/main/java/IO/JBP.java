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
    public boolean JBPSave(VendingMachine vendingMachine) {
        VendingMachinePOJO vendingMachinePOJO = new VendingMachinePOJO();
        vendingMachinePOJO.setCapacity(vendingMachine.getCAPACITY());
        for (ManufacturerImpl manufacturer : vendingMachine.getManufacturers()) {
            if (manufacturer != null) {
                ManufacturerPojo manufacturerPojo = new ManufacturerPojo();
                manufacturerPojo.setManufacturerName(manufacturer.getName());
                vendingMachinePOJO.manufacturerPojos.add(manufacturerPojo);
            }
        }
        for (CakeImpl product : vendingMachine.getProducts()) {
            if (product != null) {
                CakePojo cakePojo = new CakePojo();
                if (product.getAllergens() != null) cakePojo.setAllergens(product.getAllergens());
                cakePojo.setVendingMachineSlot(product.getVendingMachineSlot());
                if (product.getTypeOfProduct() != null) cakePojo.setTypeOfProduct(product.getTypeOfProduct());
                if (product.getShelfLife() != null) cakePojo.setShelfLife(product.getShelfLife().toString());
                if (product.getManufacturer() != null) cakePojo.setHersteller(product.getManufacturer().getName());
                if (product.getDateOfInspection() != null)
                    cakePojo.setDateOfInspection(product.getDateOfInspection());
                if (product.getCreamFlavor() != null) cakePojo.setCreamFlavor(product.getCreamFlavor());
                if (product.getTypeOfFruit() != null) cakePojo.setTypeOfFruit(product.getTypeOfFruit());
                if (product.getNutritionalScore() != 0) cakePojo.setNutritionalScore(product.getNutritionalScore());
                if (product.getPrice() != null) cakePojo.setPrice(product.getPrice().toString());
                vendingMachinePOJO.productPojos.add(cakePojo);
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
            VendingMachine loadedVendingMachine = new ObservableVendingMachine(loadedVendingMachinePojo.getCapacity());
            for (ManufacturerPojo manufacturerPojo : loadedVendingMachinePojo.getManufacturerPojos()) {
                loadedVendingMachine.addManufacturer(new ManufacturerImpl(manufacturerPojo.getManufacturerName()));
            }
            List<CakePojo> productPojos = (List<CakePojo>) loadedVendingMachinePojo.getProductPojos();
            Collections.sort(productPojos, new ProductsIdComparator());
            ArrayList<CakeImpl> loadedProducts = new ArrayList<>();
            for (CakePojo product : productPojos) {
                if (product != null) {
                    String creamFlavor = null;
                    ManufacturerImpl manufacturer = null;
                    HashSet<Allergen> allergens = null;
                    int nutritionalScore = 0;
                    Duration shelfLife = null;
                    String typeOfFruit = null;
                    BigDecimal price = null;
                    String typeOfProduct = null;

                    typeOfProduct = product.getTypeOfProduct();
                    manufacturer = new ManufacturerImpl(product.getManufacturer());
                    price = new BigDecimal(product.getPrice());
                    if (product.getNutritionalScore() != 0) nutritionalScore = product.getNutritionalScore();
                    if (product.getCreamFlavor() != null) creamFlavor = product.getCreamFlavor();
                    if (product.getTypeOfFruit() != null) typeOfFruit = product.getTypeOfFruit();
                    if (product.getAllergens() != null) allergens = product.getAllergens();
                    if (product.getShelfLife() != null) shelfLife = Duration.parse(product.getShelfLife());

                    switch (typeOfProduct) {
                        case "Kremkuchen":
                            CakeImpl creamCake = new CakeImpl(manufacturer, allergens, creamFlavor, nutritionalScore, shelfLife, price);
                            creamCake.setVendingMachineSlot(product.getVendingMachineSlot());
                            creamCake.setDateOfInspection(product.getDateOfInspection());
                            loadedProducts.add(creamCake);
                            break;
                        case "Obstkuchen":
                            CakeImpl FruitCake = new CakeImpl(manufacturer, allergens, nutritionalScore, shelfLife, typeOfFruit, price);
                            FruitCake.setVendingMachineSlot(product.getVendingMachineSlot());
                            FruitCake.setDateOfInspection(product.getDateOfInspection());
                            loadedProducts.add(FruitCake);
                            break;
                        case "Obsttorte":
                            CakeImpl FruitFlan = new CakeImpl(creamFlavor, manufacturer, allergens, nutritionalScore, shelfLife, typeOfFruit, price);
                            FruitFlan.setVendingMachineSlot(product.getVendingMachineSlot());
                            FruitFlan.setDateOfInspection(product.getDateOfInspection());
                            loadedProducts.add(FruitFlan);
                            break;
                        default:
                            break;
                    }
                }
            }

            for (int i = 0; i < loadedProducts.size(); i++) {
                loadedVendingMachine.addProduct(loadedProducts.get(i));
                loadedVendingMachine.getProducts().get(i).setDateOfInspection(loadedProducts.get(i).getDateOfInspection());
            }

            return loadedVendingMachine;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
