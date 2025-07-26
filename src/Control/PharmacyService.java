/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

/**
 *
 * @author jecsh
 */
import ADT.DynamicList;
import ADT.MyList;
import Entity.Medicine;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

public class PharmacyService {

    private final MyList<Medicine> medicines;
    private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public PharmacyService() {
        this.medicines = new DynamicList<>();
    }

    public boolean addMedicine(Medicine m) {
        if (findById(m.getMedicineID()) != null) {
            return false; // duplicate id
        }
        medicines.add(m);
        return true;
    }

    public boolean updateMedicine(String id, Medicine newData) {
        int idx = indexOfId(id);
        if (idx == -1) return false;
        medicines.remove(idx);
        medicines.add(idx, newData);
        return true;
    }

    public boolean deleteMedicine(String id) {
        int idx = indexOfId(id);
        if (idx == -1) return false;
        medicines.remove(idx);
        return true;
    }

    public Medicine findById(String id) {
        return medicines.findFirst(m -> m.getMedicineID().equalsIgnoreCase(id));
    }

    public MyList<Medicine> getAll() {
        return medicines;
    }

    public Date parseDate(String s) throws ParseException {
        return df.parse(s);
    }

    private int indexOfId(String id) {
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getMedicineID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    // Optional: generic search
    public Medicine findFirst(Predicate<Medicine> predicate) {
        return medicines.findFirst(predicate);
    }
}
