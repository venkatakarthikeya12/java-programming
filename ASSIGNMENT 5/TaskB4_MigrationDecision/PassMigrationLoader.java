import java.sql.*;
import java.util.List;

public class PassMigrationLoader {
    public static int loadAll(Connection con, List<String[]> passes) throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id, reg_no, name, route_no, amount) "
                + "VALUES (?, ?, ?, ?, ?)";
        boolean oldAutoCommit = con.getAutoCommit();
        con.setAutoCommit(false);
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int n = 0;
            for (String[] p : passes) {
                ps.setString(1, p[0].trim());
                ps.setString(2, p[1].trim());
                ps.setString(3, p[2].trim());
                ps.setInt(4, Integer.parseInt(p[3].trim()));
                ps.setDouble(5, Double.parseDouble(p[4].trim()));
                ps.addBatch();
                n++;
                if (n % 1000 == 0) ps.executeBatch();
            }
            ps.executeBatch();
            con.commit();
            return n;
        } catch (SQLException | RuntimeException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(oldAutoCommit);
        }
    }
}
