package IO;



import domain_logic.Allergen;

import java.util.Date;
import java.util.HashSet;

public class CakePojo {
    public String creamFlavor;
    public String manufacturer;
    public HashSet<Allergen> allergens = new HashSet<>();
    public int nutritionalScore;
    public String shelfLife;
    public String typeOfFruit;
    public String price;
    public Date dateOfInspection;
    public int vendingMachineSlot;
    public String typeOfProduct;

    public String getCreamFlavor() {
        return creamFlavor;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public HashSet<Allergen> getAllergens() {
        return allergens;
    }

    public int getNutritionalScore() {
        return nutritionalScore;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public String getTypeOfFruit() {
        return typeOfFruit;
    }

    public String getPrice() {
        return price;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    public int getVendingMachineSlot() {
        return vendingMachineSlot;
    }

    public Date getDateOfInspection() {
        return dateOfInspection;
    }

    public void setCreamFlavor(String creamFlavor) {
        this.creamFlavor = creamFlavor;
    }

    public void setHersteller(String herstellerName) {
        this.manufacturer = herstellerName;
    }

    public void setAllergens(HashSet<Allergen> allergens) {
        this.allergens = allergens;
    }

    public void setNutritionalScore(int nutritionalScore) {
        this.nutritionalScore = nutritionalScore;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public void setTypeOfFruit(String typeOfFruit) {
        this.typeOfFruit = typeOfFruit;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDateOfInspection(Date dateOfInspection) {
        this.dateOfInspection = dateOfInspection;
    }

    public void setVendingMachineSlot(int vendingMachineSlot) {
        this.vendingMachineSlot = vendingMachineSlot;
    }

    public void setTypeOfProduct(String typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    @Override
    public String toString() {
        return "KuchenImplementierungPojo{" +
                "kremsorte='" + creamFlavor + '\'' +
                ", hersteller=" + manufacturer +
                ", allergene=" + allergens +
                ", naehrwert=" + nutritionalScore +
                ", haltbarkeit=" + shelfLife +
                ", obstsorte='" + typeOfFruit + '\'' +
                ", preis=" + price +
                ", inspektionsdatum=" + dateOfInspection +
                ", fachnummer=" + vendingMachineSlot +
                ", kuchentyp='" + typeOfProduct + '\'' +
                '}';
    }
}
