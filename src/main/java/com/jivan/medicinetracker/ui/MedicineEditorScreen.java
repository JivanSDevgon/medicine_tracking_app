package com.jivan.medicinetracker.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import com.jivan.medicinetracker.datamodels.MedicineModel;
import com.jivan.medicinetracker.database.MedicineDatabase;

public class MedicineEditorScreen extends JPanel {
    private AppUI parent;

    private MedicineModel currentMedicine;
    private JTextField nameField;
    private JTextField dosageField;
    private JTextField formField;
    private JTextField unitsField;
    private JTextField packetField;
    private JTextField boxField;
    private JTextArea notesField;
    private boolean isEditMode = false;

    private JLabel titleLabel;
    private JPanel medicinePanel;
    private JPanel buttonPanel;
    private JButton saveMedicineButton;
    private JButton cancelMedicineButton;

    public MedicineEditorScreen(AppUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        // title
        titleLabel = new JLabel("Edit Medicine", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(titleLabel, BorderLayout.NORTH);

        // create input fields
        nameField = new JTextField(20);
        dosageField = new JTextField(20);
        formField = new JTextField(20);
        unitsField = new JTextField(20);
        packetField = new JTextField(20);
        boxField = new JTextField(20);
        notesField = new JTextArea(4, 20);

        // panel for input fields
        medicinePanel = new JPanel();
        medicinePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; medicinePanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; medicinePanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; medicinePanel.add(new JLabel("Dosage:"), gbc);
        gbc.gridx = 1; medicinePanel.add(dosageField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; medicinePanel.add(new JLabel("Form:"), gbc);
        gbc.gridx = 1; medicinePanel.add(formField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; medicinePanel.add(new JLabel("Units:"), gbc);
        gbc.gridx = 1; medicinePanel.add(unitsField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; medicinePanel.add(new JLabel("Packet:"), gbc);
        gbc.gridx = 1; medicinePanel.add(packetField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; medicinePanel.add(new JLabel("Box:"), gbc);
        gbc.gridx = 1; medicinePanel.add(boxField, gbc);

        gbc.gridx = 0; gbc.gridy = 6; medicinePanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1; medicinePanel.add(new JScrollPane(notesField), gbc);


        add(medicinePanel, BorderLayout.CENTER);

        // button panel
        buttonPanel = new JPanel();
        saveMedicineButton = new JButton("Save");
        saveMedicineButton.addActionListener(e -> handleSave());
        buttonPanel.add(saveMedicineButton);
        cancelMedicineButton = new JButton("Cancel");
        cancelMedicineButton.addActionListener(e -> handleCancel());
        buttonPanel.add(cancelMedicineButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    public void showScreen() {
        setVisible(true);
    }

    public void setMedicine(MedicineModel m) {
        this.currentMedicine = m;
        this.isEditMode = true;

        nameField.setText(m.name);
        dosageField.setText(String.valueOf(m.dosage));
        formField.setText(m.form);
        unitsField.setText(String.valueOf(m.units));
        packetField.setText(String.valueOf(m.packet));
        boxField.setText(String.valueOf(m.box));
        notesField.setText(m.notes);
    }

    public void setNewMedicine() {
        this.currentMedicine = new MedicineModel();
        this.isEditMode = false;

        nameField.setText("");
        dosageField.setText(String.valueOf(""));
        formField.setText("");
        unitsField.setText(String.valueOf(""));
        packetField.setText(String.valueOf(""));
        boxField.setText(String.valueOf(""));
        notesField.setText("");
    }

    public void handleCancel() {
        parent.showScreen("MANAGE");
    }

    public void handleSave() {
        currentMedicine.name = nameField.getText();
        currentMedicine.dosage = Integer.parseInt(dosageField.getText());
        currentMedicine.form = formField.getText();
        currentMedicine.units = Integer.parseInt(unitsField.getText());
        currentMedicine.packet = Integer.parseInt(packetField.getText());
        currentMedicine.box = Integer.parseInt(boxField.getText());
        currentMedicine.notes = notesField.getText();

        if (isEditMode) {
            MedicineDatabase.editMedicine(currentMedicine);
            JOptionPane.showMessageDialog(this, "Medicine saved!");
        } else {
            int newId = MedicineDatabase.addMedicine(currentMedicine);
            currentMedicine.id = newId;
            JOptionPane.showMessageDialog(this, "Medicine added!");
        }

        parent.showScreen("MANAGE");

    }
}
