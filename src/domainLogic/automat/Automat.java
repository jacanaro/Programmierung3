package domainLogic.automat;

import java.io.*;
import java.util.*;

public class Automat implements Serializable {
    private ArrayList<domainLogic.automat.KuchenImplementierung> verkaufsobjektListe = new ArrayList<>();
    private HashSet<domainLogic.automat.HerstellerImplementierung> herstellerSet = new HashSet<>();
    private final int CAPACITY;
    static final long serialVersionUID = 1L;

    public Automat(int CAPACITY) {

        this.CAPACITY = CAPACITY;
        if(CAPACITY<0){
            throw new IllegalArgumentException();
        }
    }

    public boolean addHersteller(domainLogic.automat.HerstellerImplementierung hersteller) {
        for (domainLogic.automat.HerstellerImplementierung h : herstellerSet) {
            if (h.getName().equals(hersteller.getName())) {
                return false;
            }
        }
        herstellerSet.add(hersteller);
        return true;
    }

    public boolean deleteHersteller(String herstellerName) {
        for (domainLogic.automat.HerstellerImplementierung h : herstellerSet) {
            if (h.getName().equals(herstellerName)) {
                herstellerSet.remove(h);
                return true;
            }
        }
        return false;
    }

    public domainLogic.automat.AutomatErrorCodes addVerkaufsobjekt(domainLogic.automat.KuchenImplementierung kuchen) {
        if (herstellerSet.size() == 0) return domainLogic.automat.AutomatErrorCodes.HERSTELLER_ERROR;
        if (verkaufsobjektListe.size() == CAPACITY) return domainLogic.automat.AutomatErrorCodes.CAPACITY_ERROR;

        for (domainLogic.automat.HerstellerImplementierung h : herstellerSet) {
            if (h.getName().equals(kuchen.getHersteller().getName())) {

                for (int i = 0; i < verkaufsobjektListe.size(); i++) {
                    if (verkaufsobjektListe.get(i).getFachnummer() != i) verkaufsobjektListe.get(i).setFachnummer(i);
                }
                kuchen.setFachnummer(verkaufsobjektListe.size());

                Date now = new Date();
                kuchen.setInspektionsdatum(now);
                verkaufsobjektListe.add(kuchen);
                return null;
            }
        }
        return domainLogic.automat.AutomatErrorCodes.HERSTELLER_ERROR;
    }

    public boolean deleteVerkaufsobjekt(int fachnummer) {
        for (int i = 0; i < verkaufsobjektListe.size(); i++) {
            if (fachnummer == verkaufsobjektListe.get(i).getFachnummer()) {
                verkaufsobjektListe.remove(i);
                return true;
            }
        }
        return false;
    }

    public domainLogic.automat.KuchenImplementierung doInspection(int fachnummer) {
        for (int i = 0; i < verkaufsobjektListe.size(); i++) {
            if (fachnummer == verkaufsobjektListe.get(i).getFachnummer()) {
                Date now = new Date();
                verkaufsobjektListe.get(i).setInspektionsdatum(now);
                return verkaufsobjektListe.get(i);
            }
        }
        return null;
    }

    public Map<String, Integer> listHerstellerWithCakeCount() {
        Map<String, Integer> herstellerWithCakeCount = new HashMap<>();
        for (domainLogic.automat.HerstellerImplementierung h : herstellerSet) {
            herstellerWithCakeCount.put(h.getName(), 0);
        }
        for (domainLogic.automat.KuchenImplementierung k : verkaufsobjektListe) {
            int oldValue = herstellerWithCakeCount.get(k.getHersteller().getName());
            herstellerWithCakeCount.replace(k.getHersteller().getName(), oldValue + 1);
        }
        return herstellerWithCakeCount;
    }

    public ArrayList<domainLogic.automat.KuchenImplementierung> getVerkaufsobjekte() {
        return new ArrayList<>(verkaufsobjektListe);
    }

    public ArrayList<domainLogic.automat.KuchenImplementierung> getVerkaufsobjekteOfType(String kuchentyp) {
        ArrayList<domainLogic.automat.KuchenImplementierung> verkaufsobjektlisteOfType = new ArrayList<>();
        for (domainLogic.automat.KuchenImplementierung k : verkaufsobjektListe) {
            if (k.getKuchentyp().equals(kuchentyp)) verkaufsobjektlisteOfType.add(k);
        }
        return verkaufsobjektlisteOfType;
    }

    public HashSet<domainLogic.automat.HerstellerImplementierung> getHerstellerSet() {
        return herstellerSet;
    }

    public int getCAPACITY() {
        return CAPACITY;
    }

    public HashSet<domainLogic.automat.Allergen> getAllergene(boolean existInAutomat) {
        HashSet<domainLogic.automat.Allergen> allergensInAutomat = new HashSet<>();
        HashSet<domainLogic.automat.Allergen> allergensNotInAutomat = new HashSet<>();
        domainLogic.automat.Allergen arr[] = domainLogic.automat.Allergen.values();

        for (domainLogic.automat.KuchenImplementierung k : verkaufsobjektListe) {
            if (k.getAllergene() != null) {
                for (domainLogic.automat.Allergen allergen : k.getAllergene()) {
                    if (!allergensInAutomat.contains(allergen)) allergensInAutomat.add(allergen);
                }
            }
        }

        for (int i = 0; i < arr.length; i++) {
            if (!allergensInAutomat.contains(arr[i])) allergensNotInAutomat.add(arr[i]);
        }

        if (existInAutomat) {
            return allergensInAutomat;
        } else {
            return allergensNotInAutomat;
        }
    }

    @Override
    public String toString() {
        return "Automat{" +
                "verkaufsobjektListe=" + verkaufsobjektListe +
                ", herstellerSet=" + herstellerSet +
                ", capacity=" + CAPACITY +
                '}';
    }





    public static void serialize(String filename, Automat serializableAutomat) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            serialize(oos, serializableAutomat);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serialize(ObjectOutput objectOutput, Automat serializableAutomat) throws IOException {
        objectOutput.writeObject(serializableAutomat);
    }

    public static Automat deserialize(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return deserialize(ois);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Automat deserialize(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return (Automat) objectInput.readObject();
    }
}
