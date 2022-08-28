package domain_logic;

import java.time.Duration;
import java.util.Collection;

public interface Cake {
    Manufacturer getManufacturer();
    Collection<Allergen> getAllergens();
    int getNutritionalScore();
    Duration getShelfLife();
}
