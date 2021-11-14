package domainLogic.automat;

import java.time.Duration;
import java.util.Collection;

public interface Kuchen {
    domainLogic.automat.Hersteller getHersteller();
    Collection<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();
}
