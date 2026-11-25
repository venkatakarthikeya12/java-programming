package com.campus.event.dao;

import com.campus.event.model.Registration;
import java.sql.*;
import java.util.*;

public class JdbcRegistrationDao implements RegistrationDao {
    private final String url;
    private final String user;
    private final String password;

    public JdbcRegistrationDao() {
        this(System.getenv().getOrDefault("DB_URL",
                        "jdbc:mysql://localhost:3306/campus_events"),
                System.getenv().getOrDefault("DB_USER", "root"),
                System.getenv().getOrDefault("DB_PASSWORD", ""));
    }

    public JdbcRegistrationDao(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public boolean register(Registration registration) throws SQLException {
        String sql = "INSERT INTO registrations(student_id, student_name, event_name) VALUES (?, ?, ?)";
        try (Connection c = connection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, registration.studentId());
            ps.setString(2, registration.studentName());
            ps.setString(3, registration.eventName());
            return ps.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException duplicate) {
            return false;
        }
    }

    @Override
    public List<Registration> filter(String eventName, int offset, int limit) throws SQLException {
        String sql = "All".equals(eventName)
                ? "SELECT id, student_id, student_name, event_name, registered_at FROM registrations LIMIT ? OFFSET ?"
                : "SELECT id, student_id, student_name, event_name, registered_at FROM registrations WHERE event_name=? LIMIT ? OFFSET ?";
        List<Registration> rows = new ArrayList<>();
        try (Connection c = connection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if ("All".equals(eventName)) {
                ps.setInt(1, limit);
                ps.setInt(2, offset);
            } else {
                ps.setString(1, eventName);
                ps.setInt(2, limit);
                ps.setInt(3, offset);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("registered_at");
                    rows.add(new Registration(rs.getLong("id"), rs.getString("student_id"),
                            rs.getString("student_name"), rs.getString("event_name"),
                            timestamp == null ? "" : timestamp.toString()));
                }
            }
        }
        return rows;
    }

    @Override
    public List<Registration> all() throws SQLException {
        return filter("All", 0, Integer.MAX_VALUE);
    }
}
