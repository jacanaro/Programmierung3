package domain_logic;

import java.io.*;
import java.util.*;

public class VendingMachine implements Serializable {
    private ArrayList<CakeImpl> products = new ArrayList<>();
    private HashSet<ManufacturerImpl> manufacturers = new HashSet<>();
    private final int CAPACITY;
    static final long serialVersionUID = 1L;

    public VendingMachine(int CAPACITY) {

        this.CAPACITY = CAPACITY;
        if(CAPACITY<0){
            throw new IllegalArgumentException();
        }
    }

    public boolean addManufacturer(ManufacturerImpl manufacturer) {
        for (ManufacturerImpl h : manufacturers) {
            if (h.getName().equals(manufacturer.getName())) {
                return false;
            }
        }
        manufacturers.add(manufacturer);
        return true;
    }

    public boolean deleteManufacturer(String manufacturerName) {
        for (ManufacturerImpl h : manufacturers) {
            if (h.getName().equals(manufacturerName)) {
                manufacturers.remove(h);
                return true;
            }
        }
        return false;
    }

    public VendingMachineErrorCodes addProduct(CakeImpl cake) {
        if (manufacturers.size() == 0) return VendingMachineErrorCodes.MANUFACTURER_ERROR;
        if (products.size() == CAPACITY) return VendingMachineErrorCodes.CAPACITY_ERROR;

        for (ManufacturerImpl h : manufacturers) {
            if (h.getName().equals(cake.getManufacturer().getName())) {

                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getVendingMachineSlot() != i) products.get(i).setVendingMachineSlot(i);
                }
                cake.setVendingMachineSlot(products.size());

                Date now = new Date();
                cake.setDateOfInspection(now);
                products.add(cake);
                return null;
            }
        }
        return VendingMachineErrorCodes.MANUFACTURER_ERROR;
    }

    public boolean deleteProduct(int vendingMachineSlot) {
        for (int i = 0; i < products.size(); i++) {
            if (vendingMachineSlot == products.get(i).getVendingMachineSlot()) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public CakeImpl doInspection(int vendingMachineSlot) {
        for (int i = 0; i < products.size(); i++) {
            if (vendingMachineSlot == products.get(i).getVendingMachineSlot()) {
                Date now = new Date();
                products.get(i).setDateOfInspection(now);
                return products.get(i);
            }
        }
        return null;
    }

    public Map<String, Integer> listManufacturersWithProductsCounted() {
        Map<String, Integer> manufacturersWithProductsCounted = new HashMap<>();
        for (ManufacturerImpl h : manufacturers) {
            manufacturersWithProductsCounted.put(h.getName(), 0);
        }
        for (CakeImpl k : products) {
            int oldValue = manufacturersWithProductsCounted.get(k.getManufacturer().getName());
            manufacturersWithProductsCounted.replace(k.getManufacturer().getName(), oldValue + 1);
        }
        return manufacturersWithProductsCounted;
    }

    public static void serializeVendingMachine(String filename, VendingMachine serializableVendingMachine) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            serializeVendingMachine(oos, serializableVendingMachine);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serializeVendingMachine(ObjectOutput objectOutput, VendingMachine serializableVendingMachine) throws IOException {
        objectOutput.writeObject(serializableVendingMachine);
    }

    public static VendingMachine deserializeVendingMachine(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return deserializeVendingMachine(ois);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static VendingMachine deserializeVendingMachine(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return (VendingMachine) objectInput.readObject();
    }

    public int getCAPACITY() {
        return CAPACITY;
    }

    public ArrayList<CakeImpl> getProducts() {
        return new ArrayList<>(products);
    }

    public ArrayList<CakeImpl> getProductsByType(String typeOfProduct) {
        ArrayList<CakeImpl> productsOfSpecifiedType = new ArrayList<>();
        for (CakeImpl k : products) {
            if (k.getTypeOfProduct().equals(typeOfProduct)) productsOfSpecifiedType.add(k);
        }
        return productsOfSpecifiedType;
    }

    public HashSet<ManufacturerImpl> getManufacturers() {
        return manufacturers;
    }

    public HashSet<Allergen> getAllergens(boolean existInVendingMachine) {
        HashSet<Allergen> allergensInVendingMachine = new HashSet<>();
        HashSet<Allergen> allergensNotInVendingMachine = new HashSet<>();
        Allergen listOfAllPossibleAllergens[] = Allergen.values();

        for (CakeImpl cake : products) {
            if (cake.getAllergens() != null) {
                for (Allergen allergenOfCake : cake.getAllergens()) {
                    if (!allergensInVendingMachine.contains(allergenOfCake)) allergensInVendingMachine.add(allergenOfCake);
                }
            }
        }

        for (int i = 0; i < listOfAllPossibleAllergens.length; i++) {
            if (!allergensInVendingMachine.contains(listOfAllPossibleAllergens[i])) allergensNotInVendingMachine.add(listOfAllPossibleAllergens[i]);
        }

        if (existInVendingMachine) {
            return allergensInVendingMachine;
        } else {
            return allergensNotInVendingMachine;
        }
    }

    @Override
    public String toString() {
        return "Automat{" +
                "verkaufsobjektListe=" + products +
                ", herstellerSet=" + manufacturers +
                ", capacity=" + CAPACITY +
                '}';
    }
}
