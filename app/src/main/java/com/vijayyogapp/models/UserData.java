package com.vijayyogapp.models;

/**
 * Created by SUHAS on 07/03/2017.
 */

public class UserData {
    private String name;
    private String mobileNumber;
    private String loaksabha;
    private String vidhansabha;
    private String wardNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLoaksabha() {
        return loaksabha;
    }

    public void setLoaksabha(String loaksabha) {
        this.loaksabha = loaksabha;
    }

    public String getVidhansabha() {
        return vidhansabha;
    }

    public void setVidhansabha(String vidhansabha) {
        this.vidhansabha = vidhansabha;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", loaksabha='" + loaksabha + '\'' +
                ", vidhansabha='" + vidhansabha + '\'' +
                ", wardNumber='" + wardNumber + '\'' +
                '}';
    }
}
