import java.sql.*;
import java.util.*;

public class JdbcInsertBenchmark {
    public static Connection connection() throws SQLException {
        String url = System.getenv().getOrDefault("DB_URL",
                "jdbc:mysql://localhost:3306/transport_office");
        String user = System.getenv().getOrDefault("DB_USER", "root");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "");
        return DriverManager.getConnection(url, user, password);
    }

    public static List<String[]> generate(int n) {
        List<String[]> rows = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            rows.add(new String[]{"P" + i, "R" + i, "Student " + i,
                    String.valueOf(i % 12 + 1), String.valueOf(3000.0 + i)});
        }
        return rows;
    }

    public static void statementInsert(Connection con, int n) throws SQLException {
        con.setAutoCommit(false);
        try (Statement st = con.createStatement()) {
            for (int i = 1; i <= n; i++) {
                st.executeUpdate("INSERT INTO bus_pass VALUES ('S" + i + "','RS" + i
                        + "','Student " + i + "'," + (i % 12 + 1) + "," + (3000.0 + i) + ")");
            }
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        }
    }

    public static void preparedInsert(Connection con, int n) throws SQLException {
        con.setAutoCommit(false);
        String sql = "INSERT INTO bus_pass VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 1; i <= n; i++) {
                ps.setString(1, "PS" + i);
                ps.setString(2, "RP" + i);
                ps.setString(3, "Student " + i);
                ps.setInt(4, i % 12 + 1);
                ps.setDouble(5, 3000.0 + i);
                ps.executeUpdate();
            }
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        }
    }

    public static void main(String[] args) throws Exception {
        try (Connection con = connection()) {
            for (int n : new int[]{1000, 10000, 100000}) {
                try (Statement st = con.createStatement()) {
                    st.executeUpdate("TRUNCATE TABLE bus_pass");
                }
                long t1 = System.nanoTime();
                statementInsert(con, n);
                long statement = (System.nanoTime() - t1) / 1_000_000;

                try (Statement st = con.createStatement()) {
                    st.executeUpdate("TRUNCATE TABLE bus_pass");
                }
                t1 = System.nanoTime();
                preparedInsert(con, n);
                long prepared = (System.nanoTime() - t1) / 1_000_000;

                try (Statement st = con.createStatement()) {
                    st.executeUpdate("TRUNCATE TABLE bus_pass");
                }
                t1 = System.nanoTime();
                PassLoader.loadAll(con, generate(n));
                long batch = (System.nanoTime() - t1) / 1_000_000;

                System.out.printf("%d: Statement=%d ms, Prepared=%d ms, Batch=%d ms%n",
                        n, statement, prepared, batch);
            }

            try (Statement st = con.createStatement()) {
                st.executeUpdate("TRUNCATE TABLE bus_pass");
                try (ResultSet rs = st.executeQuery("SELECT * FROM bus_pass")) {
                    ResultSetMetaData md = rs.getMetaData();
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.println(md.getColumnName(i) + " "
                                + md.getColumnTypeName(i) + " " + md.getColumnDisplaySize(i));
                    }
                }
            }
        }
    }
}
