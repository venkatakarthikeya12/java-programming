
public class ScannerThread extends Thread {

    private final String stationName;
    private final int scripts;

    public ScannerThread(String stationName, int scripts) {
        this.stationName = stationName;
        this.scripts = scripts;
    }

    @Override
    public void run() {
        try {
            System.out.println(stationName + " started scanning "
                    + scripts + " scripts.");

            Thread.sleep(1000);

            System.out.println(stationName
                    + " completed scanning " + scripts + " scripts.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(stationName + " was interrupted.");
        }
    }
}
