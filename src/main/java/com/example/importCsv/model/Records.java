package com.example.importCsv.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "records")
public class Records {
    @Id
    @Column(name = "id")
    @CsvBindByName
    private String id;
    
    @Column(name = "name")
    @CsvBindByName
    private String name;

    @Column(name = "date")
    @CsvBindByName
    private SimpleDateFormat date;

    public Records() {
    }

    public Records(String id, String name, SimpleDateFormat date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "Records [id=" + id + ", name=" + name + ", date=" + date + "]";
    }
}
