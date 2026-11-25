
public class ThreadLifeCycleDemo {

    public static void main(String[] args)
            throws InterruptedException {

        System.out.println("EXAMINATION SCANNER SYSTEM");

        System.out.println("\n--- Extending Thread ---");

        ScannerThread t1 = new ScannerThread("Station 1", 5);
        ScannerThread t2 = new ScannerThread("Station 2", 8);

        t1.start();
        t2.start();

        System.out.println("Station 1 alive: " + t1.isAlive());

        t1.join();
        t2.join();

        System.out.println("\n--- Implementing Runnable ---");

        Thread t3 = new Thread(
                new ScannerRunnable("Station 3", 6));

        Thread t4 = new Thread(
                new ScannerRunnable("Station 4", 10));

        t3.start();
        t4.start();

        System.out.println("Station 3 alive: " + t3.isAlive());

        t3.join();
        t4.join();

        System.out.println("\nAll scanner stations completed.");
    }
}
