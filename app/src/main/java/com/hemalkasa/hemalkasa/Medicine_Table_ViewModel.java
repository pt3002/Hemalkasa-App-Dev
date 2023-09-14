package com.hemalkasa.hemalkasa;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class Medicine_Table_ViewModel extends AndroidViewModel {
    private Medicine_Table_Repository medcineTableRepository;    //Repository from where we can actually call the database related functions
    private LiveData<List<Medicine_Table>> allMedicines;        // Stores all live data of medicines

    public Medicine_Table_ViewModel(@NonNull Application application) {
        super(application);
        medcineTableRepository=new Medicine_Table_Repository(application);
        allMedicines=medcineTableRepository.getAllMedicines();  //We can now call the functions of repository just like we call the member function of class
    }

        //This outher function will provide data to the UI(View)
    public void insertMedicine(Medicine_Table medicineTable){
        medcineTableRepository.insertMedicine(medicineTable);   //This inner function will fetch data from the repository
    }
        //Function names can be different but for simplicity we keep their names to be same
    public void updateMedicine(Medicine_Table medicineTable){
        medcineTableRepository.updateMedicine(medicineTable);
    }

    public void deleteMedicine(Medicine_Table medicineTable){
        medcineTableRepository.deleteMedicine(medicineTable);
    }

    public int getMedicineById(String medicineName){
        return medcineTableRepository.getMedicineById(medicineName);
    }

    public List<Medicine_Table> getMedicineByVisitingDate(String visiting_date){
        return medcineTableRepository.getMedicineByVisitingDate(visiting_date);
    }

    public void deleteAllMedicines(){
        medcineTableRepository.deleteAllMedicines();
    }

    public LiveData<List<Medicine_Table>> getAllMedicines(){
        return allMedicines;
    }

}
