import java.sql.*;

public class LoginChecks {
    public static boolean unsafeLogin(Connection con, String id, String password)
            throws SQLException {
        String sql = "SELECT * FROM users WHERE id='" + id
                + "' AND pwd='" + password + "'";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next();
        }
    }

    public static boolean safeLogin(Connection con, String id, String password)
            throws SQLException {
        String sql = "SELECT * FROM users WHERE id=? AND pwd=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String url = System.getenv().getOrDefault("DB_URL",
                "jdbc:mysql://localhost:3306/campus_events");
        String user = System.getenv().getOrDefault("DB_USER", "root");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "");
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS users");
            st.executeUpdate("CREATE TABLE users(id VARCHAR(30) PRIMARY KEY, pwd VARCHAR(100) NOT NULL)");
            st.executeUpdate("INSERT INTO users VALUES ('validuser', 'validpass')");
            String payload = "' OR '1'='1";
            System.out.println("Unsafe accepts payload: "
                    + unsafeLogin(con, payload, "wrong"));
            System.out.println("Safe accepts payload: "
                    + safeLogin(con, payload, "wrong"));
        }
    }
}
