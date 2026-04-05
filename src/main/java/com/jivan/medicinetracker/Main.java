package com.jivan.medicinetracker;

import com.jivan.medicinetracker.ui.AppUI;
import com.jivan.medicinetracker.database.MedicineDatabase;

public class Main {
    public static void main(String[] args) {
         AppUI app = new AppUI();
         app.showUI(); // show the window

        MedicineDatabase.initialize();

//        com.jivan.medicinetracker.datamodels.MedicineModel med = new com.jivan.medicinetracker.datamodels.MedicineModel(
//                -1,                 // id (or -1 if it's new)
//                "Paracetamol",     // name
//                "500mg",           // dosage
//                "Tablet",          // form
//                "After food"       // notes
//                "100"             // stock
//        );
//        MedicineDatabase.addMedicine(med);
//
//        MedicineDatabase.logMedicineUse(1, "2 tablets");
//
//        MedicineDatabase.getAllMedicines()
//                .forEach(System.out::println);

    }
}
