package com.example.myapplication;

public class app_item {
    String Name;
    String date;

    String id;
    String doctor, pname, gender;

    public app_item() {

    }

    public app_item(String name, String date, String id) {
        Name = name;
        this.date = date;
        this.id = id;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


