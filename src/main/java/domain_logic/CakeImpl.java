package domain_logic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;

public class CakeImpl implements FruitFlan, Serializable {
    private String creamFlavor;
    private ManufacturerImpl manufacturer;
    private HashSet<Allergen> allergens;
    private int nutritionalScore;
    private Duration shelfLife;
    private String typeOfFruit;
    private BigDecimal price;
    private Date dateOfInspection;
    private int vendingMachineSlot;
    private String typeOfProduct;

    public CakeImpl(String creamFlavor, ManufacturerImpl manufacturer, HashSet<Allergen> allergens, int nutritionalScore, Duration shelfLife,
                    String typeOfFruit, BigDecimal price){
        this.typeOfProduct ="Obsttorte";
        this.creamFlavor = creamFlavor;
        this.manufacturer = manufacturer;
        this.allergens = allergens;
        this.nutritionalScore = nutritionalScore;
        this.shelfLife = shelfLife;
        this.typeOfFruit = typeOfFruit;
        this.price = price;
    }

    public CakeImpl(ManufacturerImpl manufacturer, HashSet<Allergen> allergens, int nutritionalScore, Duration shelfLife,
                    String typeOfFruit, BigDecimal price){
        this.typeOfProduct ="Obstkuchen";
        this.manufacturer = manufacturer;
        this.allergens = allergens;
        this.nutritionalScore = nutritionalScore;
        this.shelfLife = shelfLife;
        this.typeOfFruit = typeOfFruit;
        this.price = price;
    }

    public CakeImpl(ManufacturerImpl manufacturer, HashSet<Allergen> allergens,
                    String creamFlavor, int nutritionalScore, Duration shelfLife,
                    BigDecimal price){
        this.typeOfProduct ="Kremkuchen";
        this.manufacturer = manufacturer;
        this.allergens = allergens;
        this.nutritionalScore = nutritionalScore;
        this.shelfLife = shelfLife;
        this.creamFlavor = creamFlavor;
        this.price = price;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    @Override
    public String getCreamFlavor() {
        return creamFlavor;
    }

    @Override
    public ManufacturerImpl getManufacturer() {
        return manufacturer;
    }

    @Override
    public HashSet<Allergen> getAllergens() {
        return allergens;
    }

    @Override
    public int getNutritionalScore() {
        return nutritionalScore;
    }

    @Override
    public Duration getShelfLife() {
        return shelfLife;
    }

    @Override
    public String getTypeOfFruit() {
        return typeOfFruit;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public Date getDateOfInspection(){
        return dateOfInspection;
    }

    @Override
    public int getVendingMachineSlot() {
        return vendingMachineSlot;
    }

    public void setDateOfInspection(Date currentDate){
        this.dateOfInspection = currentDate;
    }

    public void setVendingMachineSlot(int vendingMachineSlot){
        this.vendingMachineSlot = vendingMachineSlot;
    }

    @Override
    public String toString() {
        return "KuchenImplementierung{" +
                "kuchentyp="+ typeOfProduct +'\'' +
                "kremsorte=" + creamFlavor + '\'' +
                ", hersteller=" + getManufacturer().getName() +
                ", allergen=" + allergens +'\'' +
                ", naehrwert=" + nutritionalScore +'\'' +
                ", haltbarkeit=" + shelfLife +'\'' +
                ", obstsorte='" + typeOfFruit + '\'' +
                ", preis=" + price +'\'' +
                ", inspektionsdatum=" + dateOfInspection +'\'' +
                ", fachnummer=" + vendingMachineSlot +
                '}';
    }
}
