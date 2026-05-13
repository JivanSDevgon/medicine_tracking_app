package com.jivan.medicinetracker;

import com.jivan.medicinetracker.ui.AppUI;
import com.jivan.medicinetracker.database.MedicineDatabase;

public class Main {
    public static void main(String[] args) {
         AppUI app = new AppUI();
         app.showUI(); // show the window

        MedicineDatabase.initialize();
    }
}
