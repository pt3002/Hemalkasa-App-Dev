package com.hemalkasa.hemalkasa;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class Medicine_Table_ViewModel extends AndroidViewModel {
    private Medcine_Table_Repository medcineTableRepository;    //Repository from where we can actually call the database related functions
    private LiveData<List<Medicine_Table>> allMedicines;        // Stores all live data of medicines

    public Medicine_Table_ViewModel(@NonNull Application application) {
        super(application);
        medcineTableRepository=new Medcine_Table_Repository(application);
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

    public void deleteAllMedicines(){
        medcineTableRepository.deleteAllMedicines();
    }

    public LiveData<List<Medicine_Table>> getAllMedicines(){
        return allMedicines;
    }

}
