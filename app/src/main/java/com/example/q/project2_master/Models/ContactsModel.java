package com.example.q.project2_master.Models;

public class ContactsModel {

    private String userName, contactName, number;

    public ContactsModel(String userName, String contactName, String number) {
        this.userName = userName;
        this.contactName = contactName;
        this.number = number;
    }

    public String getUserName() {
        return userName;
    }
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String name) {
        this.contactName = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
