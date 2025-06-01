package org.example.courseworkp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourDB {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/tour_system";
    private static final String USER = "postgres";
    private static final String PASS = "Admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void saveTour(Tour tour) throws SQLException {
        String sql = "INSERT INTO tours (name, type, transport, food, days, price, start_date, destination) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tour.getName());
            stmt.setString(2, tour.getType());
            stmt.setString(3, tour.getTransport());
            stmt.setString(4, tour.getFood());
            stmt.setInt(5, tour.getDays());
            stmt.setDouble(6, tour.getPrice());
            stmt.setDate(7, Date.valueOf(tour.getStartDate()));
            stmt.setString(8, tour.getDestination());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating tour failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tour.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating tour failed, no ID obtained.");
                }
            }
        }
    }

    public List<Tour> getAllTours() throws SQLException {
        List<Tour> tours = new ArrayList<>();
        String sql = "SELECT * FROM tours";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tour tour = new Tour(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("transport"),
                        rs.getString("food"),
                        rs.getInt("days"),
                        rs.getDouble("price"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getString("destination")
                );
                tours.add(tour);
            }
        }
        return tours;
    }

    public void updateTour(Tour tour) throws SQLException {
        String sql = "UPDATE tours SET name = ?, type = ?, transport = ?, food = ?, " +
                "days = ?, price = ?, start_date = ?, destination = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tour.getName());
            stmt.setString(2, tour.getType());
            stmt.setString(3, tour.getTransport());
            stmt.setString(4, tour.getFood());
            stmt.setInt(5, tour.getDays());
            stmt.setDouble(6, tour.getPrice());
            stmt.setDate(7, Date.valueOf(tour.getStartDate()));
            stmt.setString(8, tour.getDestination());
            stmt.setInt(9, tour.getId());

            stmt.executeUpdate();
        }
    }

    public boolean deleteTour(int id) throws SQLException {
        String sql = "DELETE FROM tours WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}