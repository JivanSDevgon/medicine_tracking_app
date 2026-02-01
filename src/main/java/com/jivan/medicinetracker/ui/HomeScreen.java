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

    public HomeScreen(AppUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

//        // setup
//        setTitle("Medicine Tracker App");
//        setSize(500, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null); // center on screen
//        setLayout(new BorderLayout(10, 10));

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
        manageMedicineButton = new JButton("Manage Medicine");
        manageMedicineButton.addActionListener((ActionEvent e) -> parent.showScreen("MANAGE"));
        buttonPanel.add(manageMedicineButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    public void showScreen() {
        setVisible(true);
    }
}
