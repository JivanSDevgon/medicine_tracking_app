package com.jivan.medicinetracker.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomeScreen extends JPanel {
    private AppUI parent;

    private JLabel titleLabel;
    private JPanel upcomingMedicinePanel;
    private JPanel buttonPanel;
    private JButton manageMedicineButton;
    private JButton inventoryButton;

    public HomeScreen(AppUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        // title
        titleLabel = new JLabel("Medicine Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(titleLabel, BorderLayout.NORTH);

        // placeholder panel for medicines
        upcomingMedicinePanel = new JPanel();
        upcomingMedicinePanel.setLayout(new BoxLayout(upcomingMedicinePanel, BoxLayout.Y_AXIS));
        upcomingMedicinePanel.setBorder(BorderFactory.createTitledBorder("Upcoming Medicines"));
        add(upcomingMedicinePanel, BorderLayout.CENTER);

        // button panel
        buttonPanel = new JPanel();
        manageMedicineButton = new JButton("Manage Medicines");
        manageMedicineButton.addActionListener((ActionEvent e) -> parent.showScreen("MANAGE"));
        buttonPanel.add(manageMedicineButton);

        inventoryButton = new JButton("Inventory");
        inventoryButton.addActionListener((ActionEvent e) -> parent.showScreen("INVENTORY"));
        buttonPanel.add(inventoryButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
