package com.example.myapplication;

public class app_item2 {
    String pname, gender;

    public app_item2() {
    }

    public app_item2(String pname, String gender) {
        this.pname = pname;
        this.gender = gender;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
