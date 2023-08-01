package com.pmurawski.currencyrate.components.dbpersistance;

import java.time.LocalDateTime;
import java.util.UUID;

public class ValueRequestDTO {
    private UUID id;
    private String currency;
    private String name;
    private LocalDateTime requestDate;
    private Double valueRate;

    public ValueRequestDTO() {
    }

    public ValueRequestDTO(String currency, String name, Double value) {
        this.id = UUID.randomUUID();
        this.currency = currency;
        this.name = name;
        this.requestDate = LocalDateTime.now();
        this.valueRate = value;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
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

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public Double getValueRate() {
        return valueRate;
    }

    public void setValueRate(Double valueRate) {
        this.valueRate = valueRate;
    }
}
