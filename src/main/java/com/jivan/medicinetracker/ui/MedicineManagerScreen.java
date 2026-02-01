package com.jivan.medicinetracker.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MedicineManagerScreen extends JPanel {
    private AppUI parent;

    private JLabel titleLabel;
    private JPanel medicinePanel;
    private JPanel buttonPanel;
    private JButton addMedicineButton;
    private JButton removeMedicineButton;
    private JButton editMedicineButton;
    private JButton homeScreenButton;

    public MedicineManagerScreen(AppUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

//        // setup
//        setTitle("Medicine Tracker App");
//        setSize(500, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null); // center on screen
//        setLayout(new BorderLayout(10, 10));

        // title
        titleLabel = new JLabel("Manage Medicines", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(titleLabel, BorderLayout.NORTH);

        // placeholder panel for medicines
        medicinePanel = new JPanel();
        medicinePanel.setLayout(new BoxLayout(medicinePanel, BoxLayout.Y_AXIS));
        medicinePanel.setBorder(BorderFactory.createTitledBorder("Medicines"));
        add(medicinePanel, BorderLayout.CENTER);

        // button panel
        buttonPanel = new JPanel();
        addMedicineButton = new JButton("Add Medicine");
        buttonPanel.add(addMedicineButton);
        editMedicineButton = new JButton("Edit Medicine");
        buttonPanel.add(editMedicineButton);
        removeMedicineButton = new JButton("Remove Medicine");
        buttonPanel.add(removeMedicineButton);
        homeScreenButton = new JButton("Home");
        homeScreenButton.addActionListener((ActionEvent e) -> parent.showScreen("HOME"));
        buttonPanel.add(homeScreenButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    public void showScreen() {
        setVisible(true);
    }
}
