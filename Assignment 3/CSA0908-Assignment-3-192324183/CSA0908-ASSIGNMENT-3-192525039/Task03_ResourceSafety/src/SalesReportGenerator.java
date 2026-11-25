
import java.io.*;
import java.util.*;

class TrackedReader extends BufferedReader {

    static int openCount = 0;

    TrackedReader(String file) throws IOException {
        super(new FileReader(file));
        openCount++;
    }

    @Override
    public void close() throws IOException {
        super.close();
        openCount--;
    }
}

public class SalesReportGenerator {

    static String inputPath = "Task03_ResourceSafety/input/";

    static void manualClose(String[] files) {

        System.out.println("\nMANUAL CLOSE VERSION");

        try {
            for (String file : files) {

                TrackedReader reader =
                        new TrackedReader(inputPath + file);

                System.out.println("Reading: " + file);

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                reader.close();
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Open readers after manual version: "
                + TrackedReader.openCount);
    }

    static void tryWithResources(String[] files) {

        System.out.println("\nTRY-WITH-RESOURCES VERSION");

        try {
            for (String file : files) {

                try (TrackedReader reader =
                        new TrackedReader(inputPath + file)) {

                    System.out.println("Reading: " + file);

                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Open readers after try-with-resources: "
                + TrackedReader.openCount);
    }

    public static void main(String[] args) {

        String[] files = {
            "store1.txt",
            "missing.txt",
            "store3.txt"
        };

        manualClose(files);

        TrackedReader.openCount = 0;

        tryWithResources(files);
    }
}
