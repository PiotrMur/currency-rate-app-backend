package com.pmurawski.currencyrate.components;

import java.time.LocalDateTime;

public class ValueRequestDTO {
    private String currency;
    private String name;
    private LocalDateTime date;
    private Double value;

    public ValueRequestDTO() {
    }

    public ValueRequestDTO(String currency, String name, Double value) {
        this.currency = currency;
        this.name = name;
        this.date = LocalDateTime.now();
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
