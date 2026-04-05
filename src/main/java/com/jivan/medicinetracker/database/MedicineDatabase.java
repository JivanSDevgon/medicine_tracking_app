package com.jivan.medicinetracker.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jivan.medicinetracker.datamodels.MedicineModel;

public class MedicineDatabase {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DB_URL = "jdbc:sqlite:medicine_tracker.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialize() {
        String medicinesTable = """
                CREATE TABLE IF NOT EXISTS medicines (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    dosage TEXT,
                    form TEXT,
                    notes TEXT,
                    stock INTEGER
                );
                """;

        String logTable = """
                CREATE TABLE IF NOT EXISTS medicine_log (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    medicine_id INTEGER NOT NULL,
                    timestamp INTEGER NOT NULL,
                    amount TEXT,
                    FOREIGN KEY (medicine_id) REFERENCES medicines(id)
                );
                """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(medicinesTable);
            stmt.execute(logTable);

            System.out.println("Database initialized.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addMedicine(MedicineModel m) {
        String sql = "INSERT INTO medicines (name, dosage, form, notes, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, m.name);
            pstmt.setString(2, m.dosage);
            pstmt.setString(3, m.form);
            pstmt.setString(4, m.notes);
            pstmt.setInt(5, m.stock);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void deleteMedicine(int medicine_id) {
        String sql = "DELETE FROM medicines WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, medicine_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editMedicine(MedicineModel m) {
        String sql = "UPDATE medicines SET name = ?, dosage = ?, form = ?, notes = ?, stock = ?, WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, m.name);
            pstmt.setString(2, m.dosage);
            pstmt.setString(3, m.form);
            pstmt.setString(4, m.notes);
            pstmt.setInt(5, m.stock);
            pstmt.setInt(6, m.id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logMedicineUse(int medicineId, String amount) {
        String sql = "INSERT INTO medicine_log(medicine_id, timestamp, amount) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, medicineId);
            pstmt.setLong(2, System.currentTimeMillis());
            pstmt.setString(3, amount);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<MedicineModel> getAllMedicines() {
        List<MedicineModel> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MedicineModel med = new MedicineModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("dosage"),
                        rs.getString("form"),
                        rs.getString("notes"),
                        rs.getInt("stock")
                );

                medicines.add(med);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicines;
    }
}