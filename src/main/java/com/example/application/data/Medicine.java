package com.example.application.data;

import jakarta.persistence.Entity;

@Entity
public class Medicine extends AbstractEntity {

    private String brandName;
    private String genericName;
    private String dosageSize;
    private String dosageForm;
    private String medicineDescription;
    private String medicineImage;
    private Integer medicinePrice;

    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public String getGenericName() {
        return genericName;
    }
    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }
    public String getDosageSize() {
        return dosageSize;
    }
    public void setDosageSize(String dosageSize) {
        this.dosageSize = dosageSize;
    }
    public String getDosageForm() {
        return dosageForm;
    }
    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }
    public String getMedicineDescription() {
        return medicineDescription;
    }
    public void setMedicineDescription(String medicineDescription) {
        this.medicineDescription = medicineDescription;
    }
    public String getMedicineImage() {
        return medicineImage;
    }
    public void setMedicineImage(String medicineImage) {
        this.medicineImage = medicineImage;
    }
    public Integer getMedicinePrice() {
        return medicinePrice;
    }
    public void setMedicinePrice(Integer medicinePrice) {
        this.medicinePrice = medicinePrice;
    }

}
