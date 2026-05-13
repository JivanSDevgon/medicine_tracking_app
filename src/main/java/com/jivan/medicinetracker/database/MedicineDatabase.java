package com.jivan.medicinetracker.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jivan.medicinetracker.datamodels.MedicineModel;
import com.jivan.medicinetracker.datamodels.InventoryModel;

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
                    dosage INTEGER,
                    form TEXT,
                    units INTEGER,
                    packet INTEGER,
                    box INTEGER,
                    notes TEXT
                );
                """;

        String logTable = """
                CREATE TABLE IF NOT EXISTS medicine_log (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    medicine_id INTEGER NOT NULL,
                    timestamp INTEGER NOT NULL,
                    amount INTEGER NOT NULL,
                    FOREIGN KEY (medicine_id) REFERENCES medicines(id)
                );
                """;

        String inventoryTable = """
                CREATE TABLE IF NOT EXISTS inventory (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    medicine_id INTEGER UNIQUE NOT NULL,
                    units INTEGER NOT NULL,
                    packet INTEGER NOT NULL,
                    box INTEGER NOT NULL,
                    total INTEGER NOT NULL,
                    FOREIGN KEY (medicine_id) REFERENCES medicines(id)
                );
                """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(medicinesTable);
            stmt.execute(logTable);
            stmt.execute(inventoryTable);

            System.out.println("Database initialized.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------ MEDICINES ------------------

    public static int addMedicine(MedicineModel m) {
        String sql = "INSERT INTO medicines (name, dosage, form, units, packet, box, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, m.name);
            pstmt.setInt(2, m.dosage);
            pstmt.setString(3, m.form);
            pstmt.setInt(4, m.units);
            pstmt.setInt(5, m.packet);
            pstmt.setInt(6, m.box);
            pstmt.setString(7, m.notes);

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);

                InventoryModel inv = new InventoryModel(
                        0,
                        id,
                        m.name,
                        m.units,              // default units
                        m.packet,
                        m.box,
                        m.units * m.packet * m.box
                );

                updateInventory(id, inv);

                return id;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static void deleteMedicine(int medicine_id) {
        String sql = "DELETE FROM medicines WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, medicine_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editMedicine(MedicineModel m) {
        String sql = "UPDATE medicines SET name = ?, dosage = ?, form = ?, units = ?, packet = ?, box = ?, notes = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, m.name);
            pstmt.setInt(2, m.dosage);
            pstmt.setString(3, m.form);
            pstmt.setInt(4, m.units);
            pstmt.setInt(5, m.packet);
            pstmt.setInt(6, m.box);
            pstmt.setString(7, m.notes);
            pstmt.setInt(8, m.id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MedicineModel getMedicineById(int id) {

        String sql = "SELECT * FROM medicines WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new MedicineModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("dosage"),
                        rs.getString("form"),
                        rs.getInt("units"),
                        rs.getInt("packet"),
                        rs.getInt("box"),
                        rs.getString("notes")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
                        rs.getInt("dosage"),
                        rs.getString("form"),
                        rs.getInt("units"),
                        rs.getInt("packet"),
                        rs.getInt("box"),
                        rs.getString("notes")
                );

                medicines.add(med);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicines;
    }

    // ------------------ INVENTORY ------------------

    public static void updateInventory(int medicine_id, InventoryModel m) {

        int total = m.units * m.packet * m.box;

        String sql = """
                INSERT INTO inventory (medicine_id, units, packet, box, total)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT(medicine_id) DO UPDATE SET
                    units = excluded.units,
                    packet = excluded.packet,
                    box = excluded.box,
                    total = excluded.total;
                """;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, medicine_id);
            pstmt.setInt(2, m.units);
            pstmt.setInt(3, m.packet);
            pstmt.setInt(4, m.box);
            pstmt.setInt(5, total);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCurrentStock(int medicine_id) {
        String sql = "SELECT total FROM inventory WHERE medicine_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, medicine_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void editStock(int medicine_id, int amount) {}

    public static List<InventoryModel> getAllInventory() {
        List<InventoryModel> list = new ArrayList<>();

        String sql = """
                    SELECT i.id, i.medicine_id, m.name, i.units, i.packet, i.box, i.total
                    FROM inventory i
                    JOIN medicines m ON i.medicine_id = m.id""";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                InventoryModel item = new InventoryModel(
                        rs.getInt("id"),
                        rs.getInt("medicine_id"),
                        rs.getString("name"),
                        rs.getInt("units"),
                        rs.getInt("packet"),
                        rs.getInt("box"),
                        rs.getInt("total")
                );

                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ------------------ LOGGING ------------------

    public static void logMedicineUse(int medicineId, int amount) {

        String logSql = "INSERT INTO medicine_log(medicine_id, timestamp, amount) VALUES(?,?,?)";
        String updateStockSql = "UPDATE inventory SET total = total - ? WHERE medicine_id = ?";

        try (Connection conn = connect()) {

            conn.setAutoCommit(false);

            try (PreparedStatement logStmt = conn.prepareStatement(logSql);
                 PreparedStatement stockStmt = conn.prepareStatement(updateStockSql)) {

                // Log usage
                logStmt.setInt(1, medicineId);
                logStmt.setLong(2, System.currentTimeMillis());
                logStmt.setInt(3, amount);
                logStmt.executeUpdate();

                // Reduce stock
                stockStmt.setInt(1, amount);
                stockStmt.setInt(2, medicineId);
                stockStmt.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}