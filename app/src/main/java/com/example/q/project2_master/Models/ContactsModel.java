package com.example.q.project2_master.Models;

public class ContactsModel {

    private String name, number, number2;

    public ContactsModel(String name, String number, String number2) {
        this.name = name;
        this.number = number;
        this.number2 = number2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber2() { return number2; }

    public void setNumber2() { this.number2 = number2; }
}
