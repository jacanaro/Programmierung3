package domainLogic.automat;

import java.io.Serializable;

public class HerstellerImplementierung implements Hersteller, Serializable {
    private String name;

    public HerstellerImplementierung(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "HerstellerImplementierung{" +
                "name='" + name + '\'' +
                '}';
    }
}
