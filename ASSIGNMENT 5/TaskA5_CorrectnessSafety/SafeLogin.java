import java.sql.*;

public class SafeLogin {
    public static boolean login(Connection con, String id, String password) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE id=? AND pwd=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static void main(String[] args) {
        String url = System.getenv().getOrDefault("DB_URL",
                "jdbc:mysql://localhost:3306/transport_office");
        String user = System.getenv().getOrDefault("DB_USER", "root");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "");
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users("
                    + "id VARCHAR(30) PRIMARY KEY, pwd VARCHAR(100) NOT NULL)");
            st.executeUpdate("DELETE FROM users");
            st.executeUpdate("INSERT INTO users VALUES ('validuser', 'validpass')");
            System.out.println("Valid login: "
                    + login(con, "validuser", "validpass"));
            System.out.println("Invalid login: "
                    + login(con, "validuser", "wrong"));
        } catch (SQLException ex) {
            System.out.println("Database connection failed: " + ex.getMessage());
            System.out.println("Install/start MySQL and set DB_URL, DB_USER and DB_PASSWORD.");
        }
    }
}
