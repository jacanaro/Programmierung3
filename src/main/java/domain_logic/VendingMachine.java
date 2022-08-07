package domain_logic;

import java.io.*;
import java.util.*;

public class VendingMachine implements Serializable {
    private ArrayList<CakeImpl> verkaufsobjektListe = new ArrayList<>();
    private HashSet<ManufacturerImpl> herstellerSet = new HashSet<>();
    private final int CAPACITY;
    static final long serialVersionUID = 1L;

    public VendingMachine(int CAPACITY) {

        this.CAPACITY = CAPACITY;
        if(CAPACITY<0){
            throw new IllegalArgumentException();
        }
    }

    public boolean addHersteller(ManufacturerImpl hersteller) {
        for (ManufacturerImpl h : herstellerSet) {
            if (h.getName().equals(hersteller.getName())) {
                return false;
            }
        }
        herstellerSet.add(hersteller);
        return true;
    }

    public boolean deleteHersteller(String herstellerName) {
        for (ManufacturerImpl h : herstellerSet) {
            if (h.getName().equals(herstellerName)) {
                herstellerSet.remove(h);
                return true;
            }
        }
        return false;
    }

    public VendingMachineErrorCodes addVerkaufsobjekt(CakeImpl kuchen) {
        if (herstellerSet.size() == 0) return VendingMachineErrorCodes.HERSTELLER_ERROR;
        if (verkaufsobjektListe.size() == CAPACITY) return VendingMachineErrorCodes.CAPACITY_ERROR;

        for (ManufacturerImpl h : herstellerSet) {
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
        return VendingMachineErrorCodes.HERSTELLER_ERROR;
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

    public CakeImpl doInspection(int fachnummer) {
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
        for (ManufacturerImpl h : herstellerSet) {
            herstellerWithCakeCount.put(h.getName(), 0);
        }
        for (CakeImpl k : verkaufsobjektListe) {
            int oldValue = herstellerWithCakeCount.get(k.getHersteller().getName());
            herstellerWithCakeCount.replace(k.getHersteller().getName(), oldValue + 1);
        }
        return herstellerWithCakeCount;
    }

    public static void serialize(String filename, VendingMachine serializableAutomat) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            serialize(oos, serializableAutomat);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serialize(ObjectOutput objectOutput, VendingMachine serializableAutomat) throws IOException {
        objectOutput.writeObject(serializableAutomat);
    }

    public static VendingMachine deserialize(String filename) {
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

    public static VendingMachine deserialize(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return (VendingMachine) objectInput.readObject();
    }

    public int getCAPACITY() {
        return CAPACITY;
    }

    public ArrayList<CakeImpl> getVerkaufsobjekte() {
        return new ArrayList<>(verkaufsobjektListe);
    }

    public ArrayList<CakeImpl> getVerkaufsobjekteOfType(String kuchentyp) {
        ArrayList<CakeImpl> verkaufsobjektlisteOfType = new ArrayList<>();
        for (CakeImpl k : verkaufsobjektListe) {
            if (k.getKuchentyp().equals(kuchentyp)) verkaufsobjektlisteOfType.add(k);
        }
        return verkaufsobjektlisteOfType;
    }

    public HashSet<ManufacturerImpl> getHerstellerSet() {
        return herstellerSet;
    }

    public HashSet<Allergen> getAllergene(boolean existInAutomat) {
        HashSet<Allergen> allergensInAutomat = new HashSet<>();
        HashSet<Allergen> allergensNotInAutomat = new HashSet<>();
        Allergen arr[] = Allergen.values();

        for (CakeImpl k : verkaufsobjektListe) {
            if (k.getAllergene() != null) {
                for (Allergen allergen : k.getAllergene()) {
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
}
