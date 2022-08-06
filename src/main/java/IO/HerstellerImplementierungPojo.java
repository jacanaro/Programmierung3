package IO;

public class HerstellerImplementierungPojo {
    public String herstellerName;

    public void setHerstellerName(String herstellerName) {
        this.herstellerName = herstellerName;
    }

    public String getHerstellerName() {
        return herstellerName;
    }

    @Override
    public String toString() {
        return "HerstellerImplementierungPojo{" +
                "herstellerName='" + herstellerName + '\'' +
                '}';
    }
}
