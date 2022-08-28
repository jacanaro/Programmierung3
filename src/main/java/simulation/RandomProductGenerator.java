package simulation;
import domain_logic.Allergen;
import domain_logic.ManufacturerImpl;
import domain_logic.CakeImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RandomProductGenerator {
    private ArrayList<CakeImpl> randomProduct =new ArrayList<>();
    private HashSet<Allergen> allergens = new HashSet<>();
    ManufacturerImpl manufacturer1;
    ManufacturerImpl manufacturer2;

    public RandomProductGenerator(ManufacturerImpl manufacturer1, ManufacturerImpl manufacturer2) {
        this.manufacturer1 = manufacturer1;
        this.manufacturer2 = manufacturer2;

        allergens.add(Allergen.Sesamsamen);

        this.randomProduct.add(new CakeImpl("Nuss", manufacturer1, allergens,
                2000, Duration.ofHours(220), "Blaubeere", new BigDecimal("3.20")));

        allergens.add(Allergen.Erdnuss);

        this.randomProduct.add(new CakeImpl(manufacturer2, allergens,
                1000, Duration.ofHours(320), "Erdbeere", new BigDecimal("3.22")));

        allergens.add(Allergen.Haselnuss);

        this.randomProduct.add(new CakeImpl("Schoko", manufacturer2, allergens,
                1200, Duration.ofHours(230), "Banane", new BigDecimal("3.24")));

        allergens.add(Allergen.Gluten);

        this.randomProduct.add(new CakeImpl("Schoko", manufacturer1, allergens,
                1290, Duration.ofHours(230), "Ananas", new BigDecimal("3.25")));

        this.randomProduct.add(new CakeImpl("Kokos", manufacturer1, allergens,
                1280, Duration.ofHours(230), "Kirsche", new BigDecimal("3.26")));

        this.randomProduct.add(new CakeImpl(manufacturer2, allergens,
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27")));

        this.randomProduct.add(new CakeImpl("Walnuss", manufacturer1, allergens,
                1260, Duration.ofHours(230), "Stachelbeere", new BigDecimal("3.28")));

        this.randomProduct.add(new CakeImpl("Schoko", manufacturer2, allergens,
                1250, Duration.ofHours(230), "Stachelbeere", new BigDecimal("3.29")));

        this.randomProduct.add(new CakeImpl("Himbeere", manufacturer1, allergens,
                1240, Duration.ofHours(230), "Stachelbeere", new BigDecimal("3.21")));

        this.randomProduct.add(new CakeImpl(manufacturer2, allergens,
                1230, Duration.ofHours(230), "Stachelbeere", new BigDecimal("3.23")));
    }

    public ManufacturerImpl getManufacturer1() {
        return manufacturer1;
    }

    public ManufacturerImpl getManufacturer2() {
        return manufacturer2;
    }

    public CakeImpl getRandomProduct(){
        Random random=new Random();
        int randomIndex=random.nextInt(randomProduct.size());
        return randomProduct.get(randomIndex);
    }
}
