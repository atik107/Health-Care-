package com.example.myapplication;

public class medicine_item {
    String name;
    String element;
    String curing;

    public medicine_item(String name, String element, String curing) {
        this.name = name;
        this.element = element;
        this.curing = curing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getCuring() {
        return curing;
    }

    public void setCuring(String curing) {
        this.curing = curing;
    }
}
