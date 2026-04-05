package com.jivan.medicinetracker.datamodels;

public class LogModel {
    public int id;
    public int medicineId;
    public long timestamp;
    public String amount;

    public LogModel(int medicineId, long timestamp, String amount) {
        this.medicineId = medicineId;
        this.timestamp = timestamp;
        this.amount = amount;
    }
}