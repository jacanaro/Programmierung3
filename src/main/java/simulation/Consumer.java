package simulation;

public class Consumer extends Thread {
    private VendingMachineSimulation1 vendingMachineSimulation1;

    public Consumer(VendingMachineSimulation1 vendingMachineSimulation1) {
        this.vendingMachineSimulation1 = vendingMachineSimulation1;
    }

    public void run() {
        while (true) {
            synchronized (this.vendingMachineSimulation1) {
                if (this.vendingMachineSimulation1.getProducts().size() != 0) {
                    System.out.println(this.getName() + " versucht Kuchen zu loeschen");
                    this.vendingMachineSimulation1.deleteRandomProduct();
                }
            }
        }
    }
}
