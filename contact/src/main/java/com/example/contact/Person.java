package com.example.contact;

public class Person {
    String Name;
    String PhoneNumber;

    public Person(String name, String phoneNumber) {
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
