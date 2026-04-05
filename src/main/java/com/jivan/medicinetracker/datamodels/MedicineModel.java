package com.jivan.medicinetracker.datamodels;

public class MedicineModel {
    public int id;
    public String name;
    public String dosage;
    public String form;
    public String notes;
    public int stock;

    public MedicineModel() {
        this.id = -1;
        this.name = "";
        this.dosage = "";
        this.form = "";
        this.notes = "";
        this.stock = 0;
    }

    public MedicineModel(int id, String name, String dosage, String form, String notes, int stock) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.form = form;
        this.notes = notes;
        this.stock = stock;
    }
}
