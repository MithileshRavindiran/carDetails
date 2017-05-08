package com.rdw.domain;

/**
 * Created by x073880 on 5/7/2017.
 */
public class CarData {

     private String licenseplate;

     private String brand;

     private String model;

     private String color;

     private String fuelType;

     private String apkDueDate;

     private boolean isAPKExpired;

    public boolean isAPKExpired() {
        return isAPKExpired;
    }

    public void setAPKExpired(boolean APKExpired) {
        isAPKExpired = APKExpired;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getApkDueDate() {
        return apkDueDate;
    }

    public void setApkDueDate(String apkDueDate) {
        this.apkDueDate = apkDueDate;
    }

    @Override
    public String toString() {
        return "CarData{" +
                "licenseplate='" + licenseplate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", apkDueDate='" + apkDueDate + '\'' +
                ", isAPKExpired=" + isAPKExpired +
                '}';
    }


}
