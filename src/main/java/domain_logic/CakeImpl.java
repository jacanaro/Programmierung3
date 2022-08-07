package domain_logic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;

public class CakeImpl implements FruitFlan, Serializable {
    private String kremsorte;
    private ManufacturerImpl hersteller;
    private HashSet<Allergen> allergene;
    private int naehrwert;
    private Duration haltbarkeit;
    private String obstsorte;
    private BigDecimal preis;
    private Date inspektionsdatum;
    private int fachnummer;
    private String kuchentyp;

    public CakeImpl(String kremsorte, ManufacturerImpl hersteller, HashSet<Allergen> allergene, int naehrwert, Duration haltbarkeit,
                    String obstsorte, BigDecimal preis){
        this.kuchentyp="Obsttorte";
        this.kremsorte = kremsorte;
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.obstsorte = obstsorte;
        this.preis = preis;
    }

    public CakeImpl(ManufacturerImpl hersteller, HashSet<Allergen> allergene, int naehrwert, Duration haltbarkeit,
                    String obstsorte, BigDecimal preis){
        this.kuchentyp="Obstkuchen";
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.obstsorte = obstsorte;
        this.preis = preis;
    }

    public CakeImpl(ManufacturerImpl hersteller, HashSet<Allergen> allergene,
                    String kremsorte, int naehrwert, Duration haltbarkeit,
                    BigDecimal preis){
        this.kuchentyp="Kremkuchen";
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.kremsorte = kremsorte;
        this.preis = preis;
    }

    public String getKuchentyp() {
        return kuchentyp;
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
    }

    @Override
    public ManufacturerImpl getHersteller() {
        return hersteller;
    }

    @Override
    public HashSet<Allergen> getAllergene() {
        return allergene;
    }

    @Override
    public int getNaehrwert() {
        return naehrwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        return haltbarkeit;
    }

    @Override
    public String getObstsorte() {
        return obstsorte;
    }

    @Override
    public BigDecimal getPreis() {
        return preis;
    }

    @Override
    public Date getInspektionsdatum(){
        return inspektionsdatum;
    }

    @Override
    public int getFachnummer() {
        return fachnummer;
    }

    public void setInspektionsdatum(Date currentDate){
        this.inspektionsdatum = currentDate;
    }

    public void setFachnummer(int fachnummer){
        this.fachnummer=fachnummer;
    }

    @Override
    public String toString() {
        return "KuchenImplementierung{" +
                "kuchentyp="+kuchentyp+'\'' +
                "kremsorte=" + kremsorte + '\'' +
                ", hersteller=" + getHersteller().getName() +
                ", allergen=" + allergene +'\'' +
                ", naehrwert=" + naehrwert +'\'' +
                ", haltbarkeit=" + haltbarkeit +'\'' +
                ", obstsorte='" + obstsorte + '\'' +
                ", preis=" + preis +'\'' +
                ", inspektionsdatum=" + inspektionsdatum +'\'' +
                ", fachnummer=" + fachnummer +
                '}';
    }
}
