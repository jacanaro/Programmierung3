package domain_logic;

import java.time.Duration;
import java.util.Collection;

public interface Cake {
    Manufacturer getHersteller();
    Collection<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();
}
