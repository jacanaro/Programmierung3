package domain_logic;

import java.io.Serializable;

public class ManufacturerImpl implements Manufacturer, Serializable {
    private String name;

    public ManufacturerImpl(String name) {
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
