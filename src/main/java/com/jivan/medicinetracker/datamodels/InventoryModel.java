package com.jivan.medicinetracker.datamodels;

public class InventoryModel {
    public int id;
    public int medicine_id;
    public String name;
    public int units;
    public int packet;
    public int box;
    public int total;

    public InventoryModel(int id, int medicine_id, String name, int units, int packet, int box, int total) {
        this.id = id;
        this.medicine_id = medicine_id;
        this.name = name;
        this.units = units;
        this.packet = packet;
        this.box = box;
        this.total = total;
    }
}