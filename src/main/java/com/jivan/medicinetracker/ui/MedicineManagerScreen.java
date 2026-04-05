package com.jivan.medicinetracker.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import com.jivan.medicinetracker.datamodels.MedicineModel;
import com.jivan.medicinetracker.database.MedicineDatabase;

public class MedicineManagerScreen extends JPanel {
    private AppUI parent;
    private MedicineEditorScreen medicineEditor;

    private JLabel titleLabel;
    private JPanel medicinePanel;
    private JPanel buttonPanel;

    private JButton addMedicineButton;
    private JButton removeMedicineButton;
    private JButton editMedicineButton;
    private JButton homeScreenButton;

    private MedicineModel selectedMedicine = null;
    private JPanel selectedRow = null;

    public MedicineManagerScreen(AppUI parent, MedicineEditorScreen medicineEditor) {
        this.parent = parent;
        this.medicineEditor = medicineEditor;
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

        JScrollPane scrollPane = new JScrollPane(medicinePanel);
        add(scrollPane, BorderLayout.CENTER);

        loadMedicines();

        // button panel
        buttonPanel = new JPanel();

        addMedicineButton = new JButton("Add Medicine");
        addMedicineButton.addActionListener(e -> handleAddMedicine());
        buttonPanel.add(addMedicineButton);

        editMedicineButton = new JButton("Edit Medicine");
        editMedicineButton.addActionListener(e -> handleEditMedicine());
        buttonPanel.add(editMedicineButton);

        removeMedicineButton = new JButton("Remove Medicine");
        removeMedicineButton.addActionListener(e -> handleRemoveMedicine());
        buttonPanel.add(removeMedicineButton);

        homeScreenButton = new JButton("Home");
        homeScreenButton.addActionListener((ActionEvent e) -> parent.showScreen("HOME"));
        buttonPanel.add(homeScreenButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    public void showScreen() {
        loadMedicines();
        setVisible(true);
    }

    public void loadMedicines(){
        medicinePanel.removeAll();
        selectedMedicine = null;
        selectedRow = null;

        List<MedicineModel> medicines = MedicineDatabase.getAllMedicines();

        for (MedicineModel m : medicines) {
            JPanel row = new JPanel(new BorderLayout());
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            JLabel label = new JLabel(
                    m.id + " | " + m.name + " | " + m.dosage + " | " + m.form + " | " + m.notes
            );

            row.add(label, BorderLayout.CENTER);

            row.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    // Reset previous selection
                    if (selectedRow != null) {
                        selectedRow.setBackground(null);
                    }

                    // Set new selection
                    selectedMedicine = m;
                    selectedRow = row;

                    row.setBackground(Color.LIGHT_GRAY);

                    System.out.println("Selected: " + m.name);
                }
            });

            medicinePanel.add(row);
        }

        medicinePanel.revalidate();
        medicinePanel.repaint();
    }

    private void handleRemoveMedicine() {
        if (selectedMedicine == null) {
            JOptionPane.showMessageDialog(this, "Select a medicine first.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete " + selectedMedicine.name + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            com.jivan.medicinetracker.database.MedicineDatabase
                    .deleteMedicine(selectedMedicine.id);

            loadMedicines();
        }
    }

    private void handleEditMedicine() {
        if (selectedMedicine == null) {
            JOptionPane.showMessageDialog(this, "Select a medicine first.");
            return;
        }
        medicineEditor.setMedicine(selectedMedicine);
        parent.showScreen("EDITOR");
    }

    private void handleAddMedicine() {
        medicineEditor.setNewMedicine();
        parent.showScreen("EDITOR");
    }

}
