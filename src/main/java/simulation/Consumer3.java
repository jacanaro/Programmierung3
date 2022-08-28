package simulation;

public class Consumer3 extends Thread{
    private VendingMachineSimulation3 vendingMachineSimulation3;
    public Consumer3(VendingMachineSimulation3 vendingMachineSimulation3) {
        this.vendingMachineSimulation3 = vendingMachineSimulation3;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this.getName()+ " versucht Kuchen zu loeschen");
            this.vendingMachineSimulation3.deleteRandomAmountOfOldestProducts();
        }
    }
}
