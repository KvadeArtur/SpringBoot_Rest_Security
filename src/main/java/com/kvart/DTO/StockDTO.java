package com.kvart.DTO;

import java.util.Date;

public class StockDTO {

    private int id;

    private Integer edrpou;
    private Integer quantity;

    private Double nominalValue;
    private Double totalValue;

    private Date date;

    public StockDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getEdrpou() {
        return edrpou;
    }

    public void setEdrpou(Integer edrpou) {
        this.edrpou = edrpou;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getNominalValue() {
        return nominalValue;
    }

    public void setNominalValue(Double nominalValue) {
        this.nominalValue = nominalValue;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
