import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class PassMigration {
    public static Connection connection() throws SQLException {
        String url = System.getenv().getOrDefault("DB_URL",
                "jdbc:mysql://localhost:3306/transport_office");
        String user = System.getenv().getOrDefault("DB_USER", "root");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "");
        return DriverManager.getConnection(url, user, password);
    }

    public static int migrate(Path source, Path failureReport,
                              Connection con) throws IOException, SQLException {
        List<String[]> valid = new ArrayList<>();
        List<String> failures = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(source, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = r.readLine()) != null) {
                lineNumber++;
                String[] f = line.split(",", -1);
                String reason = validate(f);
                if (reason == null) valid.add(f);
                else failures.add("Line " + lineNumber + ": " + reason + " [" + line + "]");
            }
        }
        Files.write(failureReport, failures, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        if (!valid.isEmpty()) {
            return PassMigrationLoader.loadAll(con, valid);
        }
        return 0;
    }

    private static String validate(String[] f) {
        if (f.length != 5) return "Expected five fields";
        if (f[0].trim().isEmpty() || f[1].trim().isEmpty() || f[2].trim().isEmpty()) {
            return "Required text field is empty";
        }
        try {
            int route = Integer.parseInt(f[3].trim());
            double amount = Double.parseDouble(f[4].trim());
            if (route <= 0) return "Route must be positive";
            if (amount <= 0) return "Amount must be positive";
        } catch (NumberFormatException ex) {
            return "Route or amount is not numeric";
        }
        return null;
    }

    public static void printMetadata(Connection con) throws SQLException {
        DatabaseMetaData md = con.getMetaData();
        System.out.println("Database product: " + md.getDatabaseProductName()
                + " " + md.getDatabaseProductVersion());
        System.out.println("Driver: " + md.getDriverName() + " " + md.getDriverVersion());
        try (ResultSet rs = md.getTables(null, null, "%", new String[]{"TABLE"})) {
            System.out.print("Tables:");
            while (rs.next()) System.out.print(" " + rs.getString("TABLE_NAME"));
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("Usage: PassMigration <source.csv>");
        }
        Path source = Path.of(args[0]);
        Path report = source.resolveSibling("failed_records.txt");
        try (Connection con = connection()) {
            printMetadata(con);
            int loaded = migrate(source, report, con);
            System.out.println("Valid records loaded: " + loaded);
            System.out.println("Failure report: " + report);
        }
    }
}
