package com.jivan.medicinetracker.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import com.jivan.medicinetracker.database.MedicineDatabase;
import com.jivan.medicinetracker.datamodels.InventoryModel;

public class InventoryScreen extends JPanel {

    private AppUI parent;

    private JLabel titleLabel;
    private JPanel inventoryPanel;
    private JButton homeButton;

    public InventoryScreen(AppUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        // Title
        titleLabel = new JLabel("Inventory", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(titleLabel, BorderLayout.NORTH);

        // Inventory list panel
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Inventory"));

        JScrollPane scrollPane = new JScrollPane(inventoryPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom button panel
        JPanel bottomPanel = new JPanel();
        homeButton = new JButton("Home");
        homeButton.addActionListener((ActionEvent e) -> parent.showScreen("HOME"));
        bottomPanel.add(homeButton);

        add(bottomPanel, BorderLayout.SOUTH);

        loadInventory();
    }

    public void showScreen() {
        loadInventory();
        setVisible(true);
    }

    public void loadInventory() {
        inventoryPanel.removeAll();

        List<InventoryModel> inventoryList = MedicineDatabase.getAllInventory();

        for (InventoryModel item : inventoryList) {

            JPanel row = new JPanel(new BorderLayout());
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            JLabel label = new JLabel(
                    "Medicine ID: " + item.medicine_id +
                            " | Units: " + item.units +
                            " | Packet: " + item.packet +
                            " | Box: " + item.box +
                            " | Total: " + item.total
            );

            row.add(label, BorderLayout.CENTER);
            inventoryPanel.add(row);
        }

        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }
}