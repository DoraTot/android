package com.example.klk22;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String title;
    private String description;

    public Product(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}