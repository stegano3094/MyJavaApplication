package com.stegano.myjavaapplication.datas;

import java.io.Serializable;

public class Store implements Serializable {
    private String name;
    private String phoneNumber;
    private String logoUrl;

    public Store(String name, String phoneNumber, String logoUrl) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}
