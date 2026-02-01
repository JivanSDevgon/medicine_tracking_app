// define package
package com.jivan.medicinetracker.ui;

// basic ui libraries
import javax.swing.*;
import java.awt.*;

public class DemoUI extends JFrame {

    private JLabel titleLabel;
    private JPanel medicinePanel;
    private JButton addButton;
    private JButton removeButton;

    public DemoUI() {
        // setup
        setTitle("Medicine Tracker App");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout(10, 10));

        // title
        titleLabel = new JLabel("Medicine Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(titleLabel, BorderLayout.NORTH);

        // placeholder panel for medicines
        medicinePanel = new JPanel();
        medicinePanel.setLayout(new BoxLayout(medicinePanel, BoxLayout.Y_AXIS));
        medicinePanel.setBorder(BorderFactory.createTitledBorder("Medicines"));
        add(medicinePanel, BorderLayout.CENTER);

        // button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Medicine");
        removeButton = new JButton("Remove Medicine");
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // show UI method
    public void showUI() {
        setVisible(true);
    }

}
