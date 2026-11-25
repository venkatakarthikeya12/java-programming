import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class DamagedPassTest {
    private static String[] parse(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] f = line.split(",", -1);
        if (f.length != 5) return null;
        try {
            Integer.parseInt(f[3].trim());
            Double.parseDouble(f[4].trim());
            if (f[0].trim().isEmpty()) return null;
            return f;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        Path file = Files.createTempFile("damaged-passes", ".csv");
        Files.writeString(file,
                "P1,R1,Arun,12,4500\n"
                + "P2,R2,BAD,COUNT\n"
                + "\n"
                + "P3,R3,Divya,7,not-a-number\n"
                + "P4,R4,Kiran,9,3800\n");
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = r.readLine()) != null) {
                lineNumber++;
                String[] pass = parse(line);
                System.out.println(lineNumber + ": "
                        + (pass == null ? "DAMAGED/SKIPPED" : "VALID"));
            }
        }
        Files.delete(file);
    }
}
