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
    private ArrayList<CakeImpl> randomKuchen=new ArrayList<>();
    private HashSet<Allergen> allergene = new HashSet<>();
    ManufacturerImpl hersteller1;
    ManufacturerImpl hersteller2;

    public RandomProductGenerator(ManufacturerImpl hersteller1, ManufacturerImpl hersteller2) {
        this.hersteller1=hersteller1;
        this.hersteller2=hersteller2;

        allergene.add(Allergen.Sesamsamen);

        this.randomKuchen.add(new CakeImpl("Nuss", hersteller1, allergene,
                2000, Duration.ofHours(220), "Blaubeere", new BigDecimal("3.20")));

        allergene.add(Allergen.Erdnuss);

        this.randomKuchen.add(new CakeImpl(hersteller2, allergene,
                1000, Duration.ofHours(320), "Erdbeere", new BigDecimal("3.22")));

        allergene.add(Allergen.Haselnuss);

        this.randomKuchen.add(new CakeImpl("Schoko", hersteller2, allergene,
                1200, Duration.ofHours(230), "Banane", new BigDecimal("3.24")));

        allergene.add(Allergen.Gluten);

        this.randomKuchen.add(new CakeImpl("Schoko", hersteller1, allergene,
                1290, Duration.ofHours(230), "Ananas", new BigDecimal("3.25")));

        this.randomKuchen.add(new CakeImpl("Kokos", hersteller1, allergene,
                1280, Duration.ofHours(230), "Kirsche", new BigDecimal("3.26")));

        this.randomKuchen.add(new CakeImpl(hersteller2, allergene,
                1270, Duration.ofHours(230), "Himihbeere", new BigDecimal("3.27")));

        this.randomKuchen.add(new CakeImpl("Walnuss", hersteller1, allergene,
                1260, Duration.ofHours(230), "Stachelbeere", new BigDecimal("3.28")));

        this.randomKuchen.add(new CakeImpl("Schoko", hersteller2, allergene,
                1250, Duration.ofHours(230), "Stachelbeere", new BigDecimal("3.29")));

        this.randomKuchen.add(new CakeImpl("Himbeere", hersteller1, allergene,
                1240, Duration.ofHours(230), "Stachelbeere", new BigDecimal("3.21")));

        this.randomKuchen.add(new CakeImpl(hersteller2, allergene,
                1230, Duration.ofHours(230), "Stachelbeere", new BigDecimal("3.23")));
    }

    public ManufacturerImpl getHersteller1() {
        return hersteller1;
    }

    public ManufacturerImpl getHersteller2() {
        return hersteller2;
    }

    public CakeImpl getRandomKuchen(){
        Random random=new Random();
        int randomIndex=random.nextInt(randomKuchen.size());
        return randomKuchen.get(randomIndex);
    }
}
