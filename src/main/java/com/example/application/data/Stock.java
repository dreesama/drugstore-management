package com.example.application.data;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Stock extends AbstractEntity {

    private String brandName;
    private String genericName;
    private Integer medicineQuantity;
    private LocalDate expiration;

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
    public Integer getMedicineQuantity() {
        return medicineQuantity;
    }
    public void setMedicineQuantity(Integer medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }
    public LocalDate getExpiration() {
        return expiration;
    }
    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

}
