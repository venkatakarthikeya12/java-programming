import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class PassStore {
    private final Path file;

    PassStore(Path file) { this.file = file; }

    public void add(String passId, String regNo, String name,
                    int route, double amount) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(file,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            w.write(String.join(",", passId, regNo, name,
                    String.valueOf(route), String.valueOf(amount)));
            w.newLine();
            w.flush();
        }
    }

    public String[] find(String passId) throws IOException {
        if (!Files.exists(file)) return null;
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = parse(line);
                if (fields != null && fields[0].equals(passId)) return fields;
            }
        }
        return null;
    }

    public List<String[]> listRoute(int route) throws IOException {
        List<String[]> result = new ArrayList<>();
        if (!Files.exists(file)) return result;
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = parse(line);
                if (fields != null && Integer.parseInt(fields[3]) == route) result.add(fields);
            }
        }
        return result;
    }

    private String[] parse(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] fields = line.split(",", -1);
        if (fields.length != 5) return null;
        if (fields[0].trim().isEmpty() || fields[1].trim().isEmpty()
                || fields[2].trim().isEmpty()) return null;
        try {
            Integer.parseInt(fields[3].trim());
            Double.parseDouble(fields[4].trim());
            return fields;
        } catch (NumberFormatException | NullPointerException ex) {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        Path f = Files.createTempFile("passes", ".csv");
        PassStore store = new PassStore(f);
        store.add("P1001", "192311001", "Arun", 12, 4500);
        Files.writeString(f, "P1002,DAMAGED LINE\n", StandardOpenOption.APPEND);
        store.add("P1003", "192311003", "Divya", 7, 3800);
        System.out.println(Arrays.toString(store.find("P1003")));
        System.out.println(Arrays.toString(store.find("P1002")));
        Files.delete(f);
    }
}
