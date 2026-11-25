
public class ScannerRunnable implements Runnable {

    private final String stationName;
    private final int scripts;

    public ScannerRunnable(String stationName, int scripts) {
        this.stationName = stationName;
        this.scripts = scripts;
    }

    @Override
    public void run() {
        try {
            System.out.println(stationName + " started scanning "
                    + scripts + " scripts.");

            Thread.sleep(1500);

            System.out.println(stationName
                    + " completed scanning " + scripts + " scripts.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(stationName + " was interrupted.");
        }
    }
}
