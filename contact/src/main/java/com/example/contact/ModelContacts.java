package com.example.contact;

import android.widget.ImageView;

public class ModelContacts {
    private String icon, name, phoneNumber;

    public ModelContacts(String icon, String name, String phoneNumber) {
        this.icon = icon;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
