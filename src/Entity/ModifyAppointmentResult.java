/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author leekeezhan
 */
import ADT.DynamicList;
import ADT.MyList;

public class ModifyAppointmentResult {
    private MyList<String> errors;

    public ModifyAppointmentResult() {
        this.errors = new DynamicList<>();
    }

    public ModifyAppointmentResult(MyList<String> errors) {
        this.errors = errors;
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public MyList<String> getErrors() {
        return errors;
    }
}
