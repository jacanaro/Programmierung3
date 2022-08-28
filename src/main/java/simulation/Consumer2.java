package simulation;

public class Consumer2 extends Thread {
    private VendingMachineSimulation2 vendingMachineSimulation2;

    public Consumer2(VendingMachineSimulation2 vendingMachineSimulation2) {
        this.vendingMachineSimulation2 = vendingMachineSimulation2;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this.getName() + " versucht Kuchen zu loeschen");
            this.vendingMachineSimulation2.deleteProductWithOldestInspectionDate();
        }
    }
}
