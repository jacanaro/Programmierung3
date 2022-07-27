package IO;



import domainLogic.automat.Allergen;

import java.util.Date;
import java.util.HashSet;

public class KuchenImplementierungPojo {
    public String kremsorte;
    public String herstellerName;
    public HashSet<Allergen> allergene = new HashSet<>();
    public int naehrwert;
    public String haltbarkeit;
    public String obstsorte;
    public String preis;
    public Date inspektionsdatum;
    public int fachnummer;
    public String kuchentyp;

    public String getKremsorte() {
        return kremsorte;
    }

    public String getHerstellerName() {
        return herstellerName;
    }

    public HashSet<Allergen> getAllergene() {
        return allergene;
    }

    public int getNaehrwert() {
        return naehrwert;
    }

    public String getHaltbarkeit() {
        return haltbarkeit;
    }

    public String getObstsorte() {
        return obstsorte;
    }

    public String getPreis() {
        return preis;
    }

    public String getKuchentyp() {
        return kuchentyp;
    }

    public int getFachnummer() {
        return fachnummer;
    }

    public Date getInspektionsdatum() {
        return inspektionsdatum;
    }

    public void setKremsorte(String kremsorte) {
        this.kremsorte = kremsorte;
    }

    public void setHersteller(String herstellerName) {
        this.herstellerName = herstellerName;
    }

    public void setAllergene(HashSet<Allergen> allergene) {
        this.allergene = allergene;
    }

    public void setNaehrwert(int naehrwert) {
        this.naehrwert = naehrwert;
    }

    public void setHaltbarkeit(String haltbarkeit) {
        this.haltbarkeit = haltbarkeit;
    }

    public void setObstsorte(String obstsorte) {
        this.obstsorte = obstsorte;
    }

    public void setPreis(String preis) {
        this.preis = preis;
    }

    public void setInspektionsdatum(Date inspektionsdatum) {
        this.inspektionsdatum = inspektionsdatum;
    }

    public void setFachnummer(int fachnummer) {
        this.fachnummer = fachnummer;
    }

    public void setKuchentyp(String kuchentyp) {
        this.kuchentyp = kuchentyp;
    }

    @Override
    public String toString() {
        return "KuchenImplementierungPojo{" +
                "kremsorte='" + kremsorte + '\'' +
                ", hersteller=" + herstellerName +
                ", allergene=" + allergene +
                ", naehrwert=" + naehrwert +
                ", haltbarkeit=" + haltbarkeit +
                ", obstsorte='" + obstsorte + '\'' +
                ", preis=" + preis +
                ", inspektionsdatum=" + inspektionsdatum +
                ", fachnummer=" + fachnummer +
                ", kuchentyp='" + kuchentyp + '\'' +
                '}';
    }
}
