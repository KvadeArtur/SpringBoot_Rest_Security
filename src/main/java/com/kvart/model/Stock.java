package com.kvart.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;

    @Column(length = 200)
    private String comment;

    private int sizeOfTheCapital;
    private int edrpou;
    private int quantity;

    private double nominalValue;
    private double totalValue;
    private double duty;

    private Date date;

    private List<String> changes = new ArrayList<>();

    public Stock() {
    }

    public Stock(String comment, int sizeOfTheCapital, int edrpou,
                 int quantity, double nominalValue, double duty, Date date) {
        this.comment = comment;
        this.sizeOfTheCapital = sizeOfTheCapital;
        this.edrpou = edrpou;
        this.quantity = quantity;
        this.nominalValue = nominalValue;
        this.totalValue = quantity * nominalValue;
        this.duty = duty;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public int getSizeOfTheCapital() {
        return sizeOfTheCapital;
    }

    public int getEdrpou() {
        return edrpou;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getNominalValue() {
        return nominalValue;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public double getDuty() {
        return duty;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getChanges() {
        return changes;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSizeOfTheCapital(int sizeOfTheCapital) {
        this.sizeOfTheCapital = sizeOfTheCapital;
    }

    public void setEdrpou(int edrpou) {
        this.edrpou = edrpou;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalValue = quantity * this.nominalValue;
    }

    public void setNominalValue(double nominalValue) {
        this.nominalValue = nominalValue;
        this.totalValue = this.quantity * nominalValue;
    }

    public void setDuty(double duty) {
        this.duty = duty;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", sizeOfTheCapital=" + sizeOfTheCapital +
                ", edrpou=" + edrpou +
                ", quantity=" + quantity +
                ", nominalValue=" + nominalValue +
                ", totalValue=" + totalValue +
                ", duty=" + duty +
                ", date=" + date +
                ", changes=" + changes +
                '}';
    }
}
