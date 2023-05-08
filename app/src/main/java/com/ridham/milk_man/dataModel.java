package com.ridham.milk_man;

public class dataModel {
    String name;
    int id;

    public dataModel(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public dataModel(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
