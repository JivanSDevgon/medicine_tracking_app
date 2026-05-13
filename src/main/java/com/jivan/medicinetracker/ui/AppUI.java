package com.jivan.medicinetracker.ui;

import javax.swing.*;
import java.awt.*;

public class AppUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private HomeScreen homeScreen;
    private MedicineEditorScreen medicineEditorScreen;
    private MedicineManagerScreen medicineManagerScreen;
    private InventoryScreen inventoryScreen;

    public AppUI() {
        setTitle("Medicine Tracker");
        setSize(500, 400);
        getContentPane().setBackground(Theme.BACKGROUND);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // CardLayout allows switching screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Theme.PANEL);

        // Screens
        homeScreen = new HomeScreen(this);
        medicineEditorScreen = new MedicineEditorScreen(this);
        medicineManagerScreen = new MedicineManagerScreen(this, medicineEditorScreen);
        inventoryScreen = new InventoryScreen(this);

        mainPanel.add(homeScreen, "HOME");
        mainPanel.add(medicineManagerScreen, "MANAGE");
        mainPanel.add(medicineEditorScreen, "EDITOR");
        mainPanel.add(inventoryScreen, "INVENTORY");

        add(mainPanel);
    }

    // Method to switch screens
    public void showScreen(String name) {

        switch (name) {
            case "MANAGE":
                medicineManagerScreen.loadMedicines();
                break;

            case "INVENTORY":
                inventoryScreen.loadInventory();
                break;
        }

        cardLayout.show(mainPanel, name);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showUI() {
        setVisible(true);
    }
}
