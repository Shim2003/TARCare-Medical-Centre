/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;
import java.util.Objects;
/**
 *
 * @author shim
 */
public class Medicine {
    private String medicineID;
    private String medicineName;
    private int quantity;
    private String category;
    private double price;
    private String manufacturer;
    private Date expiryDate;
    
    public Medicine() {}

    public Medicine(String medicineID, String medicineName, int quantity, String category, double price, String manufacturer, Date expiryDate) {
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicine)) return false;
        Medicine medicine = (Medicine) o;
        return Objects.equals(medicineID, medicine.medicineID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineID);
    }

    @Override
    public String toString() {
        String dateStr = expiryDate == null ? "-" : new java.text.SimpleDateFormat("dd/MM/yyyy").format(expiryDate);
        return String.format("%-8s | %-20s | %5d | %-12s | %8.2f | %-15s | %s",
                medicineID, medicineName, quantity, category, price, manufacturer, dateStr);
    }
}
