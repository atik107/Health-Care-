package com.example.myapplication;

public class patient {
    String id,name,email,bt,gender,type,phone,address,imgurl;
    int age;
    String pres;

    public patient() {
    }

    public patient(String id, String name, String email, String bt, String gender, String type, String phone, String address, String imgurl, int age, String pres) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bt = bt;
        this.gender = gender;
        this.type = type;
        this.phone = phone;
        this.address = address;
        this.imgurl = imgurl;
        this.age = age;
        this.pres = pres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getBt() {
        return bt;
    }

    public String getGender() {
        return gender;
    }

    public String getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }
}
