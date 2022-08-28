package IO;

public class ManufacturerPojo {
    public String manufacturerName;

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    @Override
    public String toString() {
        return "HerstellerImplementierungPojo{" +
                "herstellerName='" + manufacturerName + '\'' +
                '}';
    }
}
