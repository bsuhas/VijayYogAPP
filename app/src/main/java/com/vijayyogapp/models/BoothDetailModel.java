package com.vijayyogapp.models;

/**
 * Created by SUHAS on 09/03/2017.
 */

public class BoothDetailModel {
    private int boothId;
    private String boothAddress;

    public int getBoothId() {
        return boothId;
    }

    public void setBoothId(int boothId) {
        this.boothId = boothId;
    }

    public String getBoothAddress() {
        return boothAddress;
    }

    public void setBoothAddress(String boothAddress) {
        this.boothAddress = boothAddress;
    }

    @Override
    public String toString() {
        return "BoothDetailModel{" +
                "boothId=" + boothId +
                ", boothAddress='" + boothAddress + '\'' +
                '}';
    }
}
