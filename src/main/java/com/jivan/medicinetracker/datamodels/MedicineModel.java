package com.jivan.medicinetracker.datamodels;

public class MedicineModel {
    public int id;
    public String name; // Medicine name, e.g. Lamotrigine
    public int dosage; // Dosage in units of medicine, e.g. 2
    public String form; // Tablets, pills, liquid, e.g. tablet
    public int units;
    public int packet;
    public int box;
    public String notes; // Usage, dose size (e.g. 2x 200mg twice a day), e.g. Epilespy, 2x 200mg twice a day


    public MedicineModel() {
        this.id = -1;
        this.name = "";
        this.dosage = 0;
        this.form = "";
        this.units = 0;
        this.packet = 0;
        this.box = 0;
        this.notes = "";
    }

    public MedicineModel(int id, String name, int dosage, String form, int units, int packet, int box, String notes) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.form = form;
        this.units = units;
        this.packet = packet;
        this.box = box;
        this.notes = notes;
    }
}
