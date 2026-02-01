package com.jivan.medicinetracker.ui;

import javax.swing.*;
import java.awt.*;

public class AppUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public AppUI() {
        setTitle("Medicine Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // CardLayout allows switching screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Screens
        HomeScreen homeScreen = new HomeScreen(this);
        MedicineManagerScreen medicineManagerScreen = new MedicineManagerScreen(this);

        mainPanel.add(homeScreen, "HOME");
        mainPanel.add(medicineManagerScreen, "MANAGE");

        add(mainPanel);
    }

    // Method to switch screens
    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void showUI() {
        setVisible(true);
    }
}
