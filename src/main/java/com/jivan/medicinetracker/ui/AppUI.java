package com.jivan.medicinetracker.ui;

import com.jivan.medicinetracker.database.MedicineDatabase;

import javax.swing.*;
import java.awt.*;

public class AppUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private HomeScreen homeScreen;
    private MedicineEditorScreen medicineEditorScreen;
    private MedicineManagerScreen medicineManagerScreen;

    public AppUI() {
        setTitle("Medicine Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // CardLayout allows switching screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Screens
        homeScreen = new HomeScreen(this);
        medicineEditorScreen = new MedicineEditorScreen(this);
        medicineManagerScreen = new MedicineManagerScreen(this, medicineEditorScreen);

        mainPanel.add(homeScreen, "HOME");
        mainPanel.add(medicineManagerScreen, "MANAGE");
        mainPanel.add(medicineEditorScreen, "EDITOR");

        add(mainPanel);
    }

    // Method to switch screens
    public void showScreen(String name) {
        if (name.equals("MANAGE")) {
            medicineManagerScreen.loadMedicines();
        }
        cardLayout.show(mainPanel, name);
    }

    public void showUI() {
        setVisible(true);
    }
}
