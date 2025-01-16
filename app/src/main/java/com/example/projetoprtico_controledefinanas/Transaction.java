package com.example.projetoprtico_controledefinanas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "transactions")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private double value;
    private String category;
    private Date date;

    public Transaction(String title, double value, String category, Date date) {
        this.title = title;
        this.value = value;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public double getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

}